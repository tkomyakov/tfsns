package komyakov.tfs19s07.data.db

import androidx.room.*
import io.reactivex.Flowable
import komyakov.tfs19s07.dto.NewsHeader


@Dao
interface NewsItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(newsList: List<NewsHeader>): List<Long>

    @Update
    fun update(newsHeader: List<NewsHeader>)

    @Transaction
    fun insertOrUpdate(newsList: List<NewsHeader>) {

        val insertRes = insert(newsList)
        val updateList = mutableListOf<NewsHeader>()

        for (i in insertRes.indices) {
            if (insertRes[i] == -1L) {
                updateList.add(newsList[i])
            }
        }

        if (!updateList.isEmpty()) {
            update(updateList)
        }
    }

    @Query("select n.id, n.title, n.mills from newsheader n order by mills desc ")
    fun loadAll(): Flowable<List<NewsHeader>>

    @Query("delete from newsheader where newsheader.id not in (:ids)")
    fun trim(ids: List<String>)
}