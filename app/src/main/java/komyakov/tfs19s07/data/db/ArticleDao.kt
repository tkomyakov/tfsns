package komyakov.tfs19s07.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import komyakov.tfs19s07.dto.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Query("select a.id, a.text from article a where id=:id")
    fun select(id: String): Single<Article>

}