package komyakov.tfs19s06.dataProviders.db

import android.content.Context
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import komyakov.tfs19s06.R
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
    }

    override fun unmarkFavorite(id: String): Completable {
        return Completable.fromAction { favoriteNewsItemDao().delete(id) }
    }

    override fun loadAllNews(): Flowable<List<ConsolidatedNewsItem>> {
        return newsItemDao().loadAll()
    }

    override fun insertNews(newsList: List<NewsItem>): Completable {
        return Completable.fromAction { newsItemDao().insert(newsList) }
    }

    companion object {
        private var instance: NewsDatabase? = null

        fun getInstance(context: Context, fallbackProvider: IDataProvider?): NewsDatabase {

            instance?.let { return it }

            instance = Room.databaseBuilder(context, NewsDatabase::class.java, "database")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        fallbackProvider?.loadAllNewsRaw()
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.flatMapCompletable { list -> instance!!.insertNews(list) }
                            ?.subscribe(object : DisposableCompletableObserver() {
                                override fun onComplete() {}
                                override fun onError(e: Throwable) {
                                    Toast.makeText(context, R.string.some_error_toast, Toast.LENGTH_SHORT).show()
                                }

                            })
                    }
                })
                .build()

            return instance!!
        }
    }
}