package pontini.systems.view.adapters


import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.vh_mash.view.*
import pontini.systems.R
import pontini.systems.room.Boil

class ViewHolderBoil(itemView: View, val listener: ViewHolderListener) : BaseViewHolder(itemView) {


    fun onBindViewHolder(boil: Boil) {
        with(itemView) {
            tvName.text = boil.name
            tvName.text = boil.name
            tvSeparetor.visibility = View.VISIBLE


            setOnClickListener {
                listener.onCLickBoil(boil.id)
            }
        }
    }


    companion object {

        fun getInstance(parent: ViewGroup, listener: ViewHolderListener): ViewHolderBoil {
            val view = inflarLayout(R.layout.vh_boil, parent)
            return ViewHolderBoil(view, listener)
        }
    }

    interface ViewHolderListener {
        fun onCLickBoil(idBoil: Long)

    }


}
