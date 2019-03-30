package komyakov.tfs19s06

import android.view.View
import androidx.fragment.app.Fragment
import komyakov.tfs19s06.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_main

    override fun prepareView(view: View): View {

        view.viewPager.adapter = MainFragmentAdapter(childFragmentManager, context!!)
        view.tabLayout.setupWithViewPager(view.viewPager)

        return view
    }

    companion object {
        fun newInstance(): Fragment {
            return MainFragment()
        }
    }
}