package komyakov.tfs19s07.tabs

import komyakov.tfs19s07.base.baselist.IBaseListItemModel
import komyakov.tfs19s07.dto.ConsolidatedNewsItem
import komyakov.tfs19s07.utils.formatReadable

data class CommonListItemModel(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val favorite: Boolean
) : IBaseListItemModel {
    override val type: Int = IBaseListItemModel.TYPE_COMMON

    constructor(newsItem: ConsolidatedNewsItem) : this(
        newsItem.id,
        newsItem.title,
        newsItem.description,
        formatReadable(newsItem.timestamp),
        newsItem.favorite
    )
}
