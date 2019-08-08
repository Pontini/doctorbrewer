package pontini.systems.view.adapters


import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.vh_mash.view.*
import pontini.systems.R
import pontini.systems.room.Mash

class ViewHolderMash(itemView: View, val listener: ViewHolderListener) : BaseViewHolder(itemView) {


    fun onBindViewHolder(mash: Mash) {
        with(itemView) {
            tvName.text = mash.name
            tvSeparetor.visibility = View.VISIBLE
            tvCountSteps.text="Refatorar"

            setOnClickListener {
                listener.onCLickMash(mash.id)
            }
        }
    }


    companion object {

        fun getInstance(parent: ViewGroup, listener: ViewHolderListener): ViewHolderMash {
            val view = inflarLayout(R.layout.vh_mash, parent)
            return ViewHolderMash(view, listener)
        }
    }

    interface ViewHolderListener {
        fun onCLickMash(idMash: Long)

    }


}
