package komyakov.tfs19s04.tabs

import komyakov.tfs19s04.base.baselist.IBaseListItemModel
import komyakov.tfs19s04.dto.NewsItem
import komyakov.tfs19s04.utils.formatReadable

data class CommonListItemModel(
    val title: String,
    val description: String,
    val date: String,
    val favorite: Boolean
) : IBaseListItemModel {
    override val type: Int = IBaseListItemModel.TYPE_COMMON

    constructor(newsItem: NewsItem) : this(
        newsItem.title,
        newsItem.description,
        formatReadable(newsItem.timestamp),
        newsItem.favorite
    )
}
