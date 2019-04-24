package komyakov.tfs19s07.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import komyakov.tfs19s07.dto.FavoriteNewsItem
import komyakov.tfs19s07.dto.NewsHeader

@Dao
interface FavoriteNewsItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: FavoriteNewsItem)

    @Query("select * from newsheader where id in (select id from favoritenewsitem)")
    fun loadAll(): Flowable<List<NewsHeader>>

    @Query("delete from favoritenewsitem where id=:itemId")
    fun delete(itemId: String)

    @Query("select exists(select 1 from favoritenewsitem WHERE id=:itemId LIMIT 1)")
    fun favoriteStaus(itemId: String): Single<Boolean>
}