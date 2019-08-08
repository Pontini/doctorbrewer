package pontini.systems.view.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pontini.systems.room.Boil


class AdapterBoilList(val listener: ViewHolderBoil.ViewHolderListener)  : RecyclerView.Adapter<ViewHolderBoil>() {


    var dataSource: List<Boil> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBoil {
        return ViewHolderBoil.getInstance(parent,listener)
    }

    override fun onBindViewHolder(holder: ViewHolderBoil, position: Int) {
        holder.onBindViewHolder(getItem(position))


    }

    override fun getItemCount() = dataSource.size


    fun getItem(position: Int) = dataSource[position]


}
