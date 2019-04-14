package komyakov.tfs19s07.news

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import komyakov.tfs19s07.R
import komyakov.tfs19s07.base.BaseFragment
import komyakov.tfs19s07.tabs.CommonListItemModel
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsItemFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_news

    private lateinit var newsItemId: String
    private var callback: Callback? = null
    private var favorite: Boolean = false

    override fun prepareView(view: View): View {
        view.fav.setOnClickListener {
            favorite = !favorite
            callback?.favoriteClicked(newsItemId, favorite)
            setFavorite()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val item = if (savedInstanceState?.containsKey(KEY_CONTENT) != true) {
            arguments!!.getSerializable(KEY_CONTENT) as CommonListItemModel
        } else {
            savedInstanceState.getSerializable(KEY_CONTENT) as CommonListItemModel
        }

        favorite = item.favorite
        newsItemId = item.id
        setFavorite()

        view.itemDate.text = item.date
        view.itemTitle.text = item.title
        view.itemDescription.text = item.description

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val oldItem = arguments!!.getSerializable(KEY_CONTENT) as CommonListItemModel
        val item = CommonListItemModel(oldItem.id, oldItem.title, oldItem.description, oldItem.date, favorite)
        outState.putSerializable(KEY_CONTENT, item)
        super.onSaveInstanceState(outState)
    }

    override fun onAttachContext(context: Context) {
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
        //flutter style :)
        fav.setImageDrawable(
            fav.context.getDrawable(
                when {
                    favorite -> android.R.drawable.btn_star_big_on
                    else -> android.R.drawable.btn_star_big_off
                }
            )
        )
    }

    companion object {
        private const val KEY_CONTENT = "content"

        fun newInstance(item: CommonListItemModel): Fragment {
            val fragment = NewsItemFragment()
            fragment.arguments = Bundle()
            fragment.arguments!!.putSerializable(KEY_CONTENT, item)
            return fragment
        }
    }

    interface Callback {
        fun favoriteClicked(id: String, value: Boolean)
    }
}