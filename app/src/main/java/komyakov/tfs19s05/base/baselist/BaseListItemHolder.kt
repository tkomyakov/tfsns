package komyakov.tfs19s05.base.baselist

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListItemHolder<in T>(viewItem: View) : RecyclerView.ViewHolder(viewItem) {

    abstract fun bindItem(model: T)
}