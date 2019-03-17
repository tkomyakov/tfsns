package komyakov.tfs19s04.tabs

import komyakov.tfs19s04.base.baselist.IBaseListItemModel
import komyakov.tfs19s04.dto.NewsItem
import komyakov.tfs19s04.utils.formatReadable

interface IListTransformer {
    fun transformList(list: ArrayList<NewsItem>): ArrayList<IBaseListItemModel> {
        val sorted = list.sortedByDescending { it.timestamp }.toList() as ArrayList<NewsItem>
        sorted.add(sorted.size, sorted.last())

        val result = sorted
            .asSequence()
            .zipWithNext { a, b ->
                if (a.timestamp == b.timestamp) {
                    sequenceOf(CommonListItemModel(a))
                } else {
                    sequenceOf(
                        CommonListItemModel(a),
                        HeaderListItemModel(formatReadable(b.timestamp))
                    )
                }
            }
            .flatMap { it }
            .toList() as ArrayList
        result.add(0, HeaderListItemModel((result[0] as CommonListItemModel).date))

        return result
    }
}