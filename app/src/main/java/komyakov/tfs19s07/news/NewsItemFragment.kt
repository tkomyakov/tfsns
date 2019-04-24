package komyakov.tfs19s07.news

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import komyakov.tfs19s07.App
import komyakov.tfs19s07.R
import komyakov.tfs19s07.base.BaseFragment
import komyakov.tfs19s07.tabs.CommonListItemModel
import komyakov.tfs19s07.utils.fromHtml
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsItemFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_news

    private lateinit var newsItemId: String
    private var articleText: String? = null
    private var callback: Callback? = null
    private var favorite: Boolean = false

    override fun prepareView(view: View): View {
        view.fav.setOnClickListener {
            favorite = !favorite
            callback?.favoriteClicked(newsItemId, favorite)
            setFavorite()
        }

        view.itemDescription.movementMethod = LinkMovementMethod.getInstance()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val item = if (savedInstanceState?.containsKey(KEY_CONTENT) != true) {
            arguments!!.getSerializable(KEY_CONTENT) as CommonListItemModel
        } else {
            savedInstanceState.getSerializable(KEY_CONTENT) as CommonListItemModel
        }

        newsItemId = item.id

        view.itemDate.text = item.date
        view.itemTitle.text = item.title

        compositeDisposable.add(
            App.repo
                .getFavoriteStatus(newsItemId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isFav ->
                    favorite = isFav
                    setFavorite()
                }
        )

        if (savedInstanceState?.containsKey(KEY_TEXT) == true) {
            view.itemDescription.text = savedInstanceState.getString(KEY_TEXT)
        } else {
            compositeDisposable.add(
                App.repo
                    .loadArticle(newsItemId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ article ->
                        view.itemDescription.text = fromHtml(article.text)
                    },
                        { Toast.makeText(context, getString(R.string.load_error), Toast.LENGTH_LONG).show() })
            )
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val oldItem = arguments!!.getSerializable(KEY_CONTENT) as CommonListItemModel
        val item = CommonListItemModel(oldItem.id, oldItem.title, oldItem.date)
        outState.putSerializable(KEY_CONTENT, item)

        if (!articleText.isNullOrEmpty()) {
            outState.putSerializable(KEY_TEXT, articleText)
        }

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
        private const val KEY_TEXT = "text"

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