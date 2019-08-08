package pontini.systems.view.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pontini.systems.room.Mash


class AdapterMashList(val listener: ViewHolderMash.ViewHolderListener)  : RecyclerView.Adapter<ViewHolderMash>() {


    var dataSource: List<Mash> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMash {
        return ViewHolderMash.getInstance(parent,listener)
    }

    override fun onBindViewHolder(holder: ViewHolderMash, position: Int) {
        holder.onBindViewHolder(getItem(position))


    }

    override fun getItemCount() = dataSource.size


    fun getItem(position: Int) = dataSource[position]


}
