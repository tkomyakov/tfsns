package komyakov.tfs19s05.dto

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