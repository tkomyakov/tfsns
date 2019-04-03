package komyakov.tfs19s06.base.baselist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import komyakov.tfs19s06.App
import komyakov.tfs19s06.R
import komyakov.tfs19s06.base.BaseFragment
import komyakov.tfs19s06.di.DataManager
import komyakov.tfs19s06.tabs.CommonListItemModel
import komyakov.tfs19s06.tabs.ListItemsAdapter

abstract class BaseListFragment : BaseFragment() {

    protected val component: DataManager by lazy {
        (activity!!.application as App).component
    }

    lateinit var adapter: ListItemsAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (savedInstanceState?.containsKey(KEY_CONTENT) != true) {
            arguments?.let {
                (adapter as ListItemsAdapter)
                    .setData(arguments!!.getSerializable(KEY_CONTENT) as List<CommonListItemModel>)
            }
        } else {
            (adapter as ListItemsAdapter).setData(
                savedInstanceState.getSerializable(KEY_CONTENT) as List<CommonListItemModel>
            )
        }

        compositeDisposable.add(
            getListDataFlow()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data ->
                    (adapter as ListItemsAdapter).setData(data)

                    if (data.isEmpty()) {
                        arguments = null

                    } else {
                        val bundle = Bundle()
                        bundle.putSerializable(KEY_CONTENT, data as ArrayList<IBaseListItemModel>)
                        arguments = bundle
                    }
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