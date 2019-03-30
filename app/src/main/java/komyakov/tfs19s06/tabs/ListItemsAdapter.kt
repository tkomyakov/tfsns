package komyakov.tfs19s06.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import komyakov.tfs19s06.R
import komyakov.tfs19s06.base.baselist.BaseListItemHolder
import komyakov.tfs19s06.base.baselist.IBaseFragmentListItemCallback
import komyakov.tfs19s06.base.baselist.IBaseListItemModel
import komyakov.tfs19s06.base.baselist.IBaseListItemModel.Companion.TYPE_COMMON
import komyakov.tfs19s06.base.baselist.IBaseListItemModel.Companion.TYPE_GROUP_DATE

class ListItemsAdapter(private val callback: IBaseFragmentListItemCallback) :
    RecyclerView.Adapter<BaseListItemHolder<*>>() {

    private var dataSet = mutableListOf<IBaseListItemModel>()
        set(values) {
            field = values
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListItemHolder<*> {
        when (viewType) {
            IBaseListItemModel.TYPE_COMMON -> {
                val view = LayoutInflater
                    .from(parent.context).inflate(R.layout.item_common, parent, false)
                val holder = CommonListItemHolder(view, callback)
                //!!помним что не в onBind ставим кликлистенер
                view.setOnClickListener {
                    if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                        holder.itemClicked(dataSet[holder.adapterPosition])
                    }
                }

                return holder
            }
            IBaseListItemModel.TYPE_GROUP_DATE -> {
                val view = LayoutInflater
                    .from(parent.context).inflate(R.layout.item_header, parent, false)

                return HeaderListItemHolder(view)
            }
        }

        throw IllegalArgumentException("Unsupported type")
    }

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].type
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setData(listItems: List<IBaseListItemModel>) {
        dataSet.clear()
        dataSet.addAll(listItems)
        notifyDataSetChanged()
    }

    fun getData(): List<IBaseListItemModel> {
        return dataSet
    }

    override fun onBindViewHolder(holder: BaseListItemHolder<*>, position: Int) {
        val item = dataSet[position]

        when (item.type) {
            TYPE_COMMON -> {
                (holder as CommonListItemHolder).bindItem(item as CommonListItemModel)
            }
            TYPE_GROUP_DATE -> {
                (holder as HeaderListItemHolder).bindItem(item as HeaderListItemModel)
            }
        }
    }
}