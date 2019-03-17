package komyakov.tfs19s04.news

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import komyakov.tfs19s04.R
import komyakov.tfs19s04.base.BaseFragment
import komyakov.tfs19s04.tabs.CommonListItemModel
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_news

    private var callback: Callback? = null
    private var favorite: Boolean = false

    override fun prepareView(view: View): View {
        view.fav.setOnClickListener {
            favorite = !favorite
            callback?.favoriteClicked(favorite)
            setFavorite()
        }

        return super.prepareView(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val item = if (savedInstanceState?.getBoolean(KEY_CONTENT) == null) {
            arguments!!.getSerializable(KEY_CONTENT) as CommonListItemModel
        } else {
            savedInstanceState.getSerializable(KEY_CONTENT) as CommonListItemModel
        }

        favorite = item.favorite
        setFavorite()

        view.itemDate.text = item.date
        view.itemTitle.text = item.title
        view.itemDescription.text = item.description

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val oldItem = arguments!!.getSerializable(KEY_CONTENT) as CommonListItemModel
        val item = CommonListItemModel(oldItem.title, oldItem.description, oldItem.date, favorite)
        outState.putSerializable(KEY_CONTENT, item)
        super.onSaveInstanceState(outState)
    }

    override fun onAttachContext(context: Context?) {
        if (context !is Callback) {
            throw IllegalArgumentException("Context must implement news callbacks!")
        }

        callback = context
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

    private fun setFavorite() {
        fav.setImageDrawable(
            fav.context.getDrawable(
                if (favorite) {
                    android.R.drawable.btn_star_big_on
                } else {
                    android.R.drawable.btn_star_big_off
                }
            )
        )
    }

    companion object {
        private const val KEY_CONTENT = "content"

        fun newInstance(item: CommonListItemModel): Fragment {
            val fragment = NewsFragment()
            fragment.arguments = Bundle()
            fragment.arguments!!.putSerializable(KEY_CONTENT, item)
            return fragment
        }
    }

    interface Callback {
        fun favoriteClicked(value: Boolean)
    }
}