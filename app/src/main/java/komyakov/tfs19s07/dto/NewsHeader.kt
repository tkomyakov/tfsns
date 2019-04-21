package komyakov.tfs19s07.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class NewsHeader(
    @SerializedName("id")
    @PrimaryKey
    var id: String,
    @SerializedName("text")
    var title: String,
    @SerializedName("publicationDate")
    @Embedded
    var publicationDate: PublicationDate
)

@Entity
data class PublicationDate(
    @SerializedName("milliseconds")
    var mills: Long
)