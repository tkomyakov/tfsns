package komyakov.tfs19s07

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import komyakov.tfs19s07.base.IFragmentTabNecessary
import komyakov.tfs19s07.tabs.CommonListFragment
import komyakov.tfs19s07.tabs.FavoriteListFragment

class MainFragmentAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    private val dataSet = mutableListOf<Fragment>()

    init {
        dataSet.add(CommonListFragment.newInstance())
        dataSet.add(FavoriteListFragment.newInstance())
    }

    override fun getItem(position: Int): Fragment {

        return dataSet[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString((getItem(position) as IFragmentTabNecessary).getName())
    }

    override fun getCount(): Int {
        return dataSet.size
    }
}