package komyakov.tfs19s05.tabs

import komyakov.tfs19s05.base.baselist.IBaseListItemModel

data class HeaderListItemModel(val date: String) : IBaseListItemModel {
    override val type: Int = IBaseListItemModel.TYPE_GROUP_DATE
}
