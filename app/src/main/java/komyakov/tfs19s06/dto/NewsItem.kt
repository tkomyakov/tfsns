package komyakov.tfs19s06.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsItem(
    @PrimaryKey
    var id: String,
    var title: String,
    var description: String,
    var timestamp: String
)