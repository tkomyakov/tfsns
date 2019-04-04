package komyakov.tfs19s06.dataProviders.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import komyakov.tfs19s06.dto.ConsolidatedNewsItem
import komyakov.tfs19s06.dto.NewsItem

@Dao
interface NewsItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsList: List<NewsItem>)

    @Query("select n.id, n.title, n.description, n.timestamp, IFNULL(fav.id, 0) as favorite from newsitem n left outer join favoritenewsitem fav on n.id=fav.id order by timestamp desc ")
    fun loadAll(): Flowable<List<ConsolidatedNewsItem>>
}