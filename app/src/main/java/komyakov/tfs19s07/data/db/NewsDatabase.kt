package komyakov.tfs19s07.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import komyakov.tfs19s07.dto.FavoriteNewsItem
import komyakov.tfs19s07.dto.NewsHeader


@Database(entities = [NewsHeader::class, FavoriteNewsItem::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsItemDao(): NewsItemDao

    abstract fun favoriteNewsItemDao(): FavoriteNewsItemDao

    fun loadFavoriteHeaders(): Flowable<List<NewsHeader>> {
        return favoriteNewsItemDao().loadAll()
    }

    fun markFavorite(id: String): Completable {
        return Completable.fromAction { favoriteNewsItemDao().insert(FavoriteNewsItem(id)) }
    }

    fun unmarkFavorite(id: String): Completable {
        return Completable.fromAction { favoriteNewsItemDao().delete(id) }
    }

    fun loadNewsHeaders(): Flowable<List<NewsHeader>> {
        return newsItemDao().loadAll()
    }

    fun insertHeaders(newsList: List<NewsHeader>): Completable {
        return Completable.fromAction { newsItemDao().insert(newsList) }
    }

    companion object {
        private var instance: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {

            instance?.let { return it }

            instance = Room.databaseBuilder(context, NewsDatabase::class.java, "database")
                .build()

            return instance!!
        }
    }
}