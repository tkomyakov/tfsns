package komyakov.tfs19s04.tabs

import android.view.View
import komyakov.tfs19s04.base.baselist.BaseListItemHolder
import kotlinx.android.synthetic.main.item_header.view.*

class HeaderListItemHolder(viewItem: View) : BaseListItemHolder<HeaderListItemModel>(viewItem) {

    override fun bindItem(model: HeaderListItemModel) {
        itemView.itemDate.text = model.date
    }
}