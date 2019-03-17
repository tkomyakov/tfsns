package komyakov.tfs19s04

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import komyakov.tfs19s04.base.IFragmentTabNecessary
import komyakov.tfs19s04.tabs.CommonFragment
import komyakov.tfs19s04.tabs.FavoriteFragment

class MainFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val dataSet: ArrayList<Fragment> = ArrayList()

    init {
        dataSet.add(CommonFragment.newInstance())
        dataSet.add(FavoriteFragment.newInstance())
    }

    override fun getItem(position: Int): Fragment {

        return dataSet[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return (getItem(position) as IFragmentTabNecessary).getName()
    }

    override fun getCount(): Int {
        return dataSet.size
    }
}