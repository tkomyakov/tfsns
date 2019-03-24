package komyakov.tfs19s05

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import komyakov.tfs19s05.base.baselist.IBaseFragmentListItemCallback
import komyakov.tfs19s05.base.baselist.IBaseListItemModel
import komyakov.tfs19s05.di.DataManager
import komyakov.tfs19s05.news.NewsFragment
import komyakov.tfs19s05.tabs.CommonListItemModel


class MainActivity : AppCompatActivity(), NewsFragment.Callback, IBaseFragmentListItemCallback {

    private val component: DataManager by lazy {
        (application as App).component
    }

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

    override fun favoriteClicked(id: String, value: Boolean) {

        if (value) {
            component.markFavorite(id)
            Toast.makeText(this, R.string.news_like, Toast.LENGTH_SHORT).show()
            return
        }

        component.unmarkFavorite(id)
        Toast.makeText(this,    R.string.news_dislike, Toast.LENGTH_SHORT).show()
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
