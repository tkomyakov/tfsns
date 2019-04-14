package komyakov.tfs19s07.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return prepareView(inflater.inflate(layoutId, container, false))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onAttachContext(context)
    }

    open fun onAttachContext(context: Context) {
    }

    abstract fun prepareView(view: View): View

}