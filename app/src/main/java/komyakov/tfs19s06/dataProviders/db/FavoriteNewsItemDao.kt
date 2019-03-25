package komyakov.tfs19s06.dataProviders.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import komyakov.tfs19s06.dto.FavoriteNewsItem
import komyakov.tfs19s06.dto.ConsolidatedNewsItem

@Dao
interface FavoriteNewsItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: FavoriteNewsItem)

    @Query("select *, 1 as favorite from newsitem where id in (select id from favoritenewsitem)")
    fun loadAll(): Flowable<List<ConsolidatedNewsItem>>

    @Query("delete from favoritenewsitem where id=:itemId")
    fun delete(itemId: String)
}