package komyakov.tfs19s07.tabs

import android.view.View
import komyakov.tfs19s07.base.baselist.BaseListItemHolder
import komyakov.tfs19s07.base.baselist.IBaseFragmentListItemCallback
import komyakov.tfs19s07.base.baselist.IBaseListItemModel
import kotlinx.android.synthetic.main.item_common.view.*

class CommonListItemHolder(
    viewItem: View,
    private val callback: IBaseFragmentListItemCallback
) : BaseListItemHolder<CommonListItemModel>(viewItem) {
    fun itemClicked(item: IBaseListItemModel) {
        callback.listItemCLicked(item)
    }

    override fun bindItem(model: CommonListItemModel) {
        itemView.itemTitle.text = model.title
    }
}