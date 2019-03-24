package komyakov.tfs19s05

import android.view.View
import androidx.fragment.app.Fragment
import komyakov.tfs19s05.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_main

    override fun prepareView(view: View): View {

        view.viewPager.adapter = MainFragmentAdapter(childFragmentManager)
        view.tabLayout.setupWithViewPager(view.viewPager)

        return view
    }

    companion object {
        fun newInstance(): Fragment {
            return MainFragment()
        }
    }
}