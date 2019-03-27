package komyakov.tfs19s06.dataProviders.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import komyakov.tfs19s06.dataProviders.IDataProvider
import komyakov.tfs19s06.dto.ConsolidatedNewsItem
import komyakov.tfs19s06.dto.FavoriteNewsItem
import komyakov.tfs19s06.dto.NewsItem


@Database(entities = [NewsItem::class, FavoriteNewsItem::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase(), IDataProvider {
    abstract fun newsItemDao(): NewsItemDao

    abstract fun favoriteNewsItemDao(): FavoriteNewsItemDao

    override fun loadFavoriteNews(): Flowable<List<ConsolidatedNewsItem>> {
        return favoriteNewsItemDao().loadAll()
    }

    override fun markFavorite(id: String): Completable {
        return Completable.fromAction { favoriteNewsItemDao().insert(FavoriteNewsItem(id)) }
            .subscribeOn(Schedulers.computation())
    }

    override fun unmarkFavorite(id: String): Completable {
        return Completable.fromAction { favoriteNewsItemDao().delete(id) }
            .subscribeOn(Schedulers.computation())
    }

    override fun loadAllNews(): Flowable<List<ConsolidatedNewsItem>> {
        return newsItemDao().loadAll()
    }

    override fun insertNews(newsList: List<NewsItem>): Completable {
        return Completable.fromAction { newsItemDao().insert(newsList) }
            .subscribeOn(Schedulers.computation())
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
                                .flatMapCompletable { list -> instance!!.insertNews(list) }
                                .subscribe { }
                        }
                    }
                })
                .build()

            return instance!!
        }
    }
}