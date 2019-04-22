package komyakov.tfs19s07.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable


abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int
    protected val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return prepareView(inflater.inflate(layoutId, container, false))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onAttachContext(context)
    }

    open fun onAttachContext(context: Context) {
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    abstract fun prepareView(view: View): View

}