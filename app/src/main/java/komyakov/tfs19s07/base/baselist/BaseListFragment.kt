package komyakov.tfs19s07.base.baselist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import komyakov.tfs19s07.App
import komyakov.tfs19s07.R
import komyakov.tfs19s07.base.BaseFragment
import komyakov.tfs19s07.di.DataManager
import komyakov.tfs19s07.tabs.CommonListItemModel
import komyakov.tfs19s07.tabs.ListItemsAdapter
import kotlinx.android.synthetic.main.fragment_tab.*

abstract class BaseListFragment : BaseFragment() {

    protected val component: DataManager by lazy {
        (activity!!.application as App).component
    }

    private lateinit var adapter: ListItemsAdapter

    private var callback: IBaseFragmentListItemCallback? = null
    private val compositeDisposable = CompositeDisposable()

    abstract fun getListDataFlow(): Flowable<List<IBaseListItemModel>>

    private fun prepareList(view: View) {
        adapter = ListItemsAdapter(callback!!)
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

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val list = when {
            savedInstanceState?.containsKey(KEY_CONTENT) == true ->
                savedInstanceState.getSerializable(KEY_CONTENT) as List<CommonListItemModel>
            arguments?.containsKey(KEY_CONTENT) == true ->
                arguments!!.getSerializable(KEY_CONTENT) as List<CommonListItemModel>
            else -> emptyList()
        }
        adapter.setData(list)

        progress_indicator.show()
        compositeDisposable.add(
            getListDataFlow()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.setData(it)

                    when {
                        it.isEmpty() -> arguments = null
                        else -> {
                            val bundle = Bundle()
                            bundle.putSerializable(KEY_CONTENT, it as ArrayList<IBaseListItemModel>)
                            arguments = bundle
                        }
                    }

                    progress_indicator.hide()
                }
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(KEY_CONTENT, ArrayList(adapter.getData()))
        super.onSaveInstanceState(outState)
    }

    override fun onAttachContext(context: Context) {

        if (context !is IBaseFragmentListItemCallback) {
            throw IllegalArgumentException("Context must implement list callbacks!")
        }

        callback = context
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    companion object {
        private const val KEY_CONTENT = "content"
    }
}