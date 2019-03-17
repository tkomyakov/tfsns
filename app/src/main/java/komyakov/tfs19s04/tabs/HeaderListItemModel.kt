package komyakov.tfs19s04.tabs

import komyakov.tfs19s04.base.baselist.IBaseListItemModel

open class HeaderListItemModel(val date: String) : IBaseListItemModel {
    override val type: Int = IBaseListItemModel.TYPE_GROUP_DATE
}
