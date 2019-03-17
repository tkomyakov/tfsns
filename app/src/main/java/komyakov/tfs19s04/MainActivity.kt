package komyakov.tfs19s04

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import komyakov.tfs19s04.base.baselist.IBaseListItemModel
import komyakov.tfs19s04.base.baselist.IBaseFragmentListItemCallback
import komyakov.tfs19s04.tabs.CommonListItemModel
import komyakov.tfs19s04.news.NewsFragment


class MainActivity : AppCompatActivity(), NewsFragment.Callback,
    IBaseFragmentListItemCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance(), false)
            return
        }
    }

    override fun listItemCLicked(listItem: IBaseListItemModel) {
        replaceFragment(NewsFragment.newInstance(listItem as CommonListItemModel), true)
    }

    override fun favoriteClicked(value: Boolean) {
        Toast.makeText(this, if (value) R.string.news_like else R.string.news_dislike, Toast.LENGTH_SHORT).show()
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                fragment
            )

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}
