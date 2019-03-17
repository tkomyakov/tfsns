package komyakov.tfs19s04.base.baselist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import komyakov.tfs19s04.App
import komyakov.tfs19s04.R
import komyakov.tfs19s04.base.BaseFragment
import komyakov.tfs19s04.tabs.CommonListItemModel
import komyakov.tfs19s04.di.DataManager
import komyakov.tfs19s04.tabs.ListItemsAdapter

abstract class BaseListFragment : BaseFragment() {

    protected val component: DataManager by lazy {
        (activity!!.application as App).component
    }

    lateinit var adapter: RecyclerView.Adapter<BaseListItemHolder<*>>

    private var callback: IBaseFragmentListItemCallback? = null

    abstract fun getListData(): List<IBaseListItemModel>

    fun prepareList(view: View) {
        adapter = ListItemsAdapter(callback)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val lm = LinearLayoutManager(view.context)

        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, lm.orientation))

        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter
    }

    override fun prepareView(view: View): View {

        prepareList(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (savedInstanceState?.get(KEY_CONTENT) == null && arguments?.get(
                KEY_CONTENT
            ) == null
        ) {
            val data = getListData()
            (adapter as ListItemsAdapter).setData(data)
            val bundle = Bundle()
            bundle.putSerializable(KEY_CONTENT, data as ArrayList<IBaseListItemModel>)
            arguments = bundle
            return
        }

        if (savedInstanceState?.get(KEY_CONTENT) == null) {
            (adapter as ListItemsAdapter).setData(arguments!!.getSerializable(KEY_CONTENT) as List<CommonListItemModel>)
            return
        }

        (adapter as ListItemsAdapter).setData(
            savedInstanceState.getSerializable(KEY_CONTENT) as List<CommonListItemModel>
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(KEY_CONTENT, (adapter as ListItemsAdapter).getData())
        super.onSaveInstanceState(outState)
    }

    override fun onAttachContext(context: Context?) {
        if (context !is IBaseFragmentListItemCallback) {
            throw IllegalArgumentException("Context must implement list callbacks!")
        }
        callback = context
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    companion object {
        private const val KEY_CONTENT = "content"
    }
}