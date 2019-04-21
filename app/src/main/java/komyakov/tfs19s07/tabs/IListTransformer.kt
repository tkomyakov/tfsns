package komyakov.tfs19s07.tabs

import komyakov.tfs19s07.base.baselist.IBaseListItemModel
import komyakov.tfs19s07.dto.NewsHeader
import komyakov.tfs19s07.utils.formatReadable

interface IListTransformer {
    fun transformList(list: List<NewsHeader>): List<IBaseListItemModel> {

        if (list.isEmpty()) {
            return emptyList()
        }

        //TODO: подумать - не ломать цепочку
        val sorted = list.sortedByDescending { it.publicationDate.mills }.toMutableList()
        sorted.add(sorted.size, sorted.last())

        val result = sorted
            .asSequence()
            .zipWithNext { a, b ->
                if (a.publicationDate.mills/86400000 == b.publicationDate.mills/86400000) {
                    sequenceOf(CommonListItemModel(a))
                } else {
                    sequenceOf(
                        CommonListItemModel(a),
                        HeaderListItemModel(formatReadable(b.publicationDate.mills))
                    )
                }
            }
            .flatMap { it }
            .toMutableList()
        result.add(0, HeaderListItemModel((result[0] as CommonListItemModel).date))

        return result
    }
}