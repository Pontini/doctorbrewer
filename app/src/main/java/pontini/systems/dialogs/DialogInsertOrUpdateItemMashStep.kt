package pontinisistemas.doctorbrewer.view.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import pontini.systems.R
import pontini.systems.dialogs.BaseDialogFragment
import pontini.systems.room.MashStep
import pontinisistemas.doctorbrewer.base.ext.convertGregorioCalendarForMinute
import pontinisistemas.doctorbrewer.enuns.EnumMashStepType
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("ValidFragment")
@Deprecated(message = "Refatorar")
class DialogInsertOrUpdateItemMashStep : BaseDialogFragment, LifecycleOwner {

    private var isEdit:Boolean=false

    private var tvTitle: TextView? = null
    private var etName: EditText? = null
    private var etDescription: EditText? = null
    private var etTemperature: EditText? = null
    private var etTime: EditText? = null
    private var spType: Spinner? = null
    private var etType: EditText? = null

    private var name: String = ""
    private var description: String = ""
    private var temperature: Double = 0.0
    private var time: GregorianCalendar = GregorianCalendar(0, 0, 0, 0, 0)
    private var enumMashStepType: EnumMashStepType = EnumMashStepType.TEMPERATURE
    private var idMash:Long = 0
    private var idMashStep:Long = 0
    private var sequence:Long=0
    private lateinit var mashStep: MashStep
    private var callback: Callback




    @SuppressLint("ValidFragment")
    constructor(callback: Callback) {
        this.callback = callback


    }
    @SuppressLint("ValidFragment")
    constructor(idMash:Long,mashStep: MashStep, isEdit:Boolean, callback: Callback) {
        this.mashStep = mashStep
        this.callback = callback
        this.name = mashStep.name
        this.description = mashStep.description
        this.temperature = mashStep.temperature
        this.time = mashStep.time
        this.enumMashStepType = EnumMashStepType.fromInt(mashStep.type)!!
        this.idMash=idMash
        this.idMashStep=mashStep.id
        this.sequence=mashStep.sequence
        this.isEdit=isEdit
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(activity, R.layout.dlg_insert_or_update_item_mash_step, null)
        initViews(view)
        updateViews()
        return buildDialog(view)
    }

    private fun buildDialog(view: View): Dialog {
        var builder = AlertDialog.Builder(activity);
        builder.setView(view)
        bindListeners(builder)
        return builder.create()
    }

    private fun bindListeners(dialog: AlertDialog.Builder) {
        dialog.setPositiveButton(android.R.string.ok) { _, i ->

            name= etName!!.text.toString()
            description=etDescription!!.text.toString()
            temperature=etTemperature!!.text.toString().toDouble()
            val minutes =etTime!!.text.toString().toInt()
            time=GregorianCalendar(0,0,0,0,minutes)


            if(isEdit){
                mashStep=MashStep(idMashStep,idMash,name,description,enumMashStepType.code,temperature, time)
                mashStep.sequence=sequence
                callback.onClickPositiveButtonUpdate(mashStep)

            }else{
                callback.onClickPositiveButtonInsert(name,description,enumMashStepType.code,temperature,time)
            }
        }

        dialog.setNegativeButton(R.string.cancel) { dialogInterface, i ->

        }
    }

    private fun updateViews() {
        updateTextView()
        updateEiteTex()
        updateSpiner()

    }

    private fun updateSpiner() {
        setTypeMashStep(enumMashStepType)
    }

    private fun initSpinnerUseIn(view: View) {
        spType = view.findViewById(R.id.dlg_input_item_ingredient__sp__use__in)

        val dataAdapter = ArrayAdapter(activity, android.R.layout.simple_dropdown_item_1line,getMashType())
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spType!!.setAdapter(dataAdapter)

        spType!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when {
                    getMashType()[position].contains(getString(EnumMashStepType.TEMPERATURE.idName)) -> setTypeMashStep(EnumMashStepType.TEMPERATURE)
                    getMashType()[position].contains(getString(EnumMashStepType.DECOCTION.idName)) -> setTypeMashStep(EnumMashStepType.DECOCTION)
                    getMashType()[position].contains(getString(EnumMashStepType.INFUSION.idName)) -> setTypeMashStep(EnumMashStepType.INFUSION)
                }
            }
        }
    }
    private fun setTypeMashStep(useIn: EnumMashStepType) {
        enumMashStepType = useIn
        etType!!.setText(getString(enumMashStepType.idName))
    }
    private fun getMashType(): ArrayList<String> {
        val list = arrayListOf<String>()

        list.add(getString(enumMashStepType.idName))

        if(EnumMashStepType.DECOCTION.code!=enumMashStepType.code){
            list.add(getString(EnumMashStepType.DECOCTION.idName))
        }
        if(EnumMashStepType.INFUSION.code!=enumMashStepType.code){
            list.add(getString(EnumMashStepType.INFUSION.idName))
        }
        if(EnumMashStepType.TEMPERATURE.code!=enumMashStepType.code){
            list.add(getString(EnumMashStepType.TEMPERATURE.idName))
        }
        return list
    }

    private fun updateEiteTex() {
        etName!!.setText(name)
        etDescription!!.setText(description)
        etTemperature!!.setText(temperature.toString())
        updateTime()
    }

    private fun updateTime() {
        etTime!!.setText(convertGregorioCalendarForMinute(time).toString());
    }

    private fun updateTextView() {
        if (title.isNullOrEmpty()) {
            tvTitle!!.text = getString(R.string.insert_mash_step)
        } else {
            tvTitle!!.text = getString(R.string.update_mash_step)
        }
    }

    private fun initViews(view: View?) {
        tvTitle = view!!.findViewById(R.id.tv_title)
        etName = view!!.findViewById(R.id.et_name)
        etDescription = view!!.findViewById(R.id.et_description)
        etTime = view!!.findViewById(R.id.et_time)
        etTemperature = view!!.findViewById(R.id.et_temperature)
        etType = view!!.findViewById(R.id.et_type)
        initSpinnerUseIn(view)
    }

    interface Callback {
        fun onClickPositiveButtonInsert(name: String, description: String, type:Int, temperature: Double, minutes: GregorianCalendar)
        fun onClickPositiveButtonUpdate(mashStep: MashStep)
    }


}