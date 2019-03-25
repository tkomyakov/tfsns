package komyakov.tfs19s06.base.baselist

import java.io.Serializable

interface IBaseListItemModel: Serializable {

    companion object {
        const val TYPE_COMMON = 1
        const val TYPE_GROUP_DATE= 1 shl 1
    }

    val type: Int
}
