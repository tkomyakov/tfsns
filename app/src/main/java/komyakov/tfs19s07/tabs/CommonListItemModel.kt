package komyakov.tfs19s07.tabs

import komyakov.tfs19s07.base.baselist.IBaseListItemModel
import komyakov.tfs19s07.dto.NewsHeader
import komyakov.tfs19s07.utils.formatReadable

data class CommonListItemModel(
    val id: String,
    val title: String,
    val date: String
) : IBaseListItemModel {
    override val type: Int = IBaseListItemModel.TYPE_COMMON

    constructor(newsHeader: NewsHeader) : this(
        newsHeader.id,
        newsHeader.title,
        formatReadable(newsHeader.publicationDate.mills)
    )
}
