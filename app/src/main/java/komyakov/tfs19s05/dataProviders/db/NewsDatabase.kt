package komyakov.tfs19s05.dataProviders.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import komyakov.tfs19s05.dataProviders.IDataProvider
import komyakov.tfs19s05.dto.ConsolidatedNewsItem
import komyakov.tfs19s05.dto.FavoriteNewsItem
import komyakov.tfs19s05.dto.NewsItem


@Database(entities = [NewsItem::class, FavoriteNewsItem::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase(), IDataProvider {
    abstract fun newsItemDao(): NewsItemDao

    abstract fun favoriteNewsItemDao(): FavoriteNewsItemDao

    override fun loadFavoriteNews(): Flowable<List<ConsolidatedNewsItem>> {
        return favoriteNewsItemDao().loadAll()
    }

    override fun markFavorite(id: String) {
        return favoriteNewsItemDao().insert(FavoriteNewsItem(id))
    }

    override fun unmarkFavorite(id: String) {
        return favoriteNewsItemDao().delete(id)
    }

    override fun loadAllNews(): Flowable<List<ConsolidatedNewsItem>> {
        return newsItemDao().loadAll()
    }

    override fun insertNews(newsList: List<NewsItem>) {
        return newsItemDao().insert(newsList)
    }

    companion object {
        private var instance: NewsDatabase? = null

        fun getInstance(context: Context, fallbackProvider: IDataProvider?): NewsDatabase {

            instance?.let { return it }

            instance = Room.databaseBuilder(context, NewsDatabase::class.java, "database")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        fallbackProvider?.let {
                            it.loadAllNewsRaw()
                                .observeOn(AndroidSchedulers.mainThread())
                                .firstOrError()
                                .subscribe(object : DisposableSingleObserver<List<NewsItem>>() {
                                    override fun onSuccess(fake: List<NewsItem>) {
                                        instance!!.insertNews(fake)
                                    }

                                    override fun onError(e: Throwable) {
                                        println("Error on population")
                                    }
                                })
                        }

                    }
                })
                .allowMainThreadQueries()
                .build()

            return instance!!
        }
    }
}