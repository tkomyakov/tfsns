package komyakov.tfs19s07.tabs

import io.reactivex.Flowable
import komyakov.tfs19s07.base.baselist.IBaseListItemModel
import komyakov.tfs19s07.dto.NewsHeader

interface IListTransformer {
    val millsInDay: Int
        get() = 86400000

    fun transformList(list: List<NewsHeader>): Flowable<List<IBaseListItemModel>> {
        return Flowable.fromIterable(list)
            .groupBy { it.publicationDate.mills / millsInDay }
            .sorted { a, b -> b.key!!.compareTo(a.key!!) }
            .flatMapSingle { grouped -> grouped.toList() }
            .flatMap { it ->
                val elm = it.map { CommonListItemModel(it) as IBaseListItemModel }.toMutableList()
                elm.add(0, HeaderListItemModel((elm[0] as CommonListItemModel).date))
                Flowable.just(elm)
            }
            .flatMapIterable { it }
            .toList()
            .toFlowable()
    }
}