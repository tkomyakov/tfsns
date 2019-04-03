package komyakov.tfs19s06.tabs

import komyakov.tfs19s06.base.baselist.IBaseListItemModel
import komyakov.tfs19s06.dto.ConsolidatedNewsItem
import komyakov.tfs19s06.utils.formatReadable

interface IListTransformer {
    fun transformList(list: List<ConsolidatedNewsItem>): List<IBaseListItemModel> {

        if (list.isEmpty()) {
            return emptyList()
        }

        //TODO: подумать - не ломать цепочку
        val sorted = list.sortedByDescending { it.timestamp }.toMutableList()
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
            .toMutableList()
        result.add(0, HeaderListItemModel((result[0] as CommonListItemModel).date))

        return result
    }
}