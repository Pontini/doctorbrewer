package pontini.systems.view.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pontini.systems.room.MashStep


class AdapterInsertOrUpdateMashStepList(val listener: ViewHolderMashStep.ViewHolderListener)  : RecyclerView.Adapter<ViewHolderMashStep>() {

    var dataSource: List<MashStep> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMashStep {
        return ViewHolderMashStep.getInstance(parent,listener)
    }

    override fun onBindViewHolder(holder: ViewHolderMashStep, position: Int) {
        holder.onBindViewHolder(getItem(position))


    }

    override fun getItemCount() = dataSource.size


    fun getItem(position: Int) = dataSource[position]



}
