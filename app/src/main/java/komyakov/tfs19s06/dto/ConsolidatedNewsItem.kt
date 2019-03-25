package komyakov.tfs19s06.dto

//этот странный рум, без транзиентных полей, либо я не нашел- как джойнить без создания колонки??
class ConsolidatedNewsItem(
    var id: String,
    var title: String,
    var description: String,
    var timestamp: String,
    var favorite: Boolean
)