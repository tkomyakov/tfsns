package komyakov.tfs19s06.tabs

import androidx.fragment.app.Fragment
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import komyakov.tfs19s06.R
import komyakov.tfs19s06.base.IFragmentTabNecessary
import komyakov.tfs19s06.base.baselist.BaseListFragment
import komyakov.tfs19s06.base.baselist.IBaseListItemModel

class FavoriteFragment : BaseListFragment(), IFragmentTabNecessary, IListTransformer {

    override val layoutId = R.layout.fragment_tab

    override fun getName(): Int {
        return R.string.favorite
    }

    override fun getListDataFlow(): Flowable<List<IBaseListItemModel>> {
        return component.loadFavorite()
            .subscribeOn(Schedulers.computation())
            .flatMap { newsItems ->
                Flowable.just(transformList(newsItems))
            }
    }

    companion object {
        fun newInstance(): Fragment {

            return FavoriteFragment()
        }
    }
}