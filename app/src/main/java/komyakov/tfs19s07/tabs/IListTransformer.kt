package komyakov.tfs19s07.tabs

import io.reactivex.Flowable
import komyakov.tfs19s07.base.baselist.IBaseListItemModel
import komyakov.tfs19s07.dto.NewsHeader
import komyakov.tfs19s07.utils.MILLS_IN_DAY
import komyakov.tfs19s07.utils.formatReadable

interface IListTransformer {

    fun transformList(list: List<NewsHeader>): Flowable<List<IBaseListItemModel>> {
        return Flowable.fromIterable(list)
            .groupBy { it.publicationDate.mills / MILLS_IN_DAY }
            .sorted { a, b -> b.key!!.compareTo(a.key!!) }
            .flatMap {
                it.map { model ->
                        CommonListItemModel(model)
                    }
                    .cast(IBaseListItemModel::class.java)
                    .startWith(HeaderListItemModel(formatReadable(it.key!!, MILLS_IN_DAY)))
            }
            .toList()
            .toFlowable()
    }
}