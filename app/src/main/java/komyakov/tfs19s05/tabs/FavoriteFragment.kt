package komyakov.tfs19s05.tabs

import androidx.fragment.app.Fragment
import io.reactivex.Flowable
import komyakov.tfs19s05.R
import komyakov.tfs19s05.base.IFragmentTabNecessary
import komyakov.tfs19s05.base.baselist.BaseListFragment
import komyakov.tfs19s05.base.baselist.IBaseListItemModel

class FavoriteFragment : BaseListFragment(), IFragmentTabNecessary, IListTransformer {

    override val layoutId = R.layout.fragment_tab

    override fun getName(): String {
        return "Избранное"
    }

    override fun getListDataFlow(): Flowable<List<IBaseListItemModel>> {
        return component.loadFavorite()
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