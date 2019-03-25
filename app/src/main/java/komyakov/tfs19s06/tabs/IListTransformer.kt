package komyakov.tfs19s06.tabs

import komyakov.tfs19s06.base.baselist.IBaseListItemModel
import komyakov.tfs19s06.dto.ConsolidatedNewsItem
import komyakov.tfs19s06.utils.formatReadable

interface IListTransformer {
    fun transformList(list: List<ConsolidatedNewsItem>): List<IBaseListItemModel> {

        if (list.isEmpty()) {
            return emptyList()
        }

        //Наверное это лишнее- апи дает как надо (но это не точно)
        val sorted = ArrayList(list.sortedByDescending { it.timestamp }.toList())
        sorted.add(sorted.size, sorted.last())

        val result = ArrayList(sorted
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
            .toList()
        )
        result.add(0, HeaderListItemModel((result[0] as CommonListItemModel).date))

        return result
    }
}