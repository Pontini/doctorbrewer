package pontini.systems.view.adapters


import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.vh_mash_step.view.*
import pontini.systems.R
import pontini.systems.room.MashStep
import pontinisistemas.doctorbrewer.base.ext.convertGregorioCalendarForHHMMSS
import pontinisistemas.doctorbrewer.base.ext.fomatTemperatureForDisplay

class ViewHolderMashStep(itemView: View, val listener: ViewHolderListener) : BaseViewHolder(itemView) {


    fun onBindViewHolder(mashStep: MashStep) {
        with(itemView) {
            mashStep.let {
                setname(it)
                setTemperature(it)
                setTime(it)
            }
            setOnClickListener {
                listener.onCLickMashStep(mashStep)
            }
        }
    }

    private fun View.setTime(it: MashStep) {
        vh_mash_step__tv__time.text = convertGregorioCalendarForHHMMSS(it.time)
    }

    private fun View.setTemperature(it: MashStep) {
        vh_mash_step__tv__temperature.text = fomatTemperatureForDisplay(it.temperature)
    }

    private fun View.setname(it: MashStep) {
        if (it.name.isNullOrEmpty()) {
           // vh_mash_step__tv__name.text = resources.getString(EnumMashStepType.fromInt(it.type)!!.idName)
        } else {
            vh_mash_step__tv__name.text = it.name
        }
    }


    companion object {

        fun getInstance(parent: ViewGroup, listener: ViewHolderListener): ViewHolderMashStep {
            val view = inflarLayout(R.layout.vh_mash_step, parent)
            return ViewHolderMashStep(view, listener)
        }
    }

    interface ViewHolderListener {
        fun onCLickMashStep(id: MashStep)
    }
}
