package komyakov.tfs19s07.tabs

import androidx.fragment.app.Fragment
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import komyakov.tfs19s07.App
import komyakov.tfs19s07.R
import komyakov.tfs19s07.base.IFragmentTabNecessary
import komyakov.tfs19s07.base.baselist.BaseListFragment
import komyakov.tfs19s07.base.baselist.IBaseListItemModel

class CommonListFragment : BaseListFragment(), IFragmentTabNecessary, IListTransformer {

    override val layoutId = R.layout.fragment_tab

    override fun getName(): Int {
        return R.string.latest
    }

    override fun getListDataFlow(): Flowable<List<IBaseListItemModel>> {
        return App.repo.loadAll()
            .subscribeOn(Schedulers.io())
            .flatMap { newsItems ->
                transformList(newsItems)
            }
    }

    companion object {
        fun newInstance(): Fragment {

            return CommonListFragment()
        }
    }
}