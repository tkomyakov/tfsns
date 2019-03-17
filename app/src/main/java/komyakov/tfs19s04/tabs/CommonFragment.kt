package komyakov.tfs19s04.tabs

import androidx.fragment.app.Fragment
import komyakov.tfs19s04.R
import komyakov.tfs19s04.base.IFragmentTabNecessary
import komyakov.tfs19s04.base.baselist.BaseListFragment
import komyakov.tfs19s04.base.baselist.IBaseListItemModel
import komyakov.tfs19s04.dto.NewsItem

class CommonFragment : BaseListFragment(), IFragmentTabNecessary, IListTransformer {

    override val layoutId = R.layout.fragment_tab

    override fun getName(): String {
        return "Последние"
    }

    override fun getListData(): List<IBaseListItemModel> {
        return transformList(component.loadAll() as ArrayList<NewsItem>)
    }

    companion object {
        fun newInstance(): Fragment {

            return CommonFragment()
        }
    }
}