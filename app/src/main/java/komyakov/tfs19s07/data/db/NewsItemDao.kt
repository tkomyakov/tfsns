package komyakov.tfs19s07.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import komyakov.tfs19s07.dto.NewsHeader

@Dao
interface NewsItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsList: List<NewsHeader>)

    @Query("select n.id, n.title, n.mills from newsheader n order by mills desc ")
    fun loadAll(): Flowable<List<NewsHeader>>
}