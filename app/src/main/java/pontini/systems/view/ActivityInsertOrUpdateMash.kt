package pontini.systems.view


import android.app.ActionBar
import kotlinx.android.synthetic.main.act_insert_or_update_mash.*
import kotlinx.android.synthetic.main.dlg_progress.*
import kotlinx.android.synthetic.main.fab.*
import kotlinx.android.synthetic.main.recycler.*
import org.jetbrains.anko.toast

import pontini.systems.view_model.ViewModelInsertOrUpdateMash
import pontinisistemas.doctorbrewer.view.dialog.DialogInsertOrUpdateItemMashStep
import pontini.systems.state.StateInsertOrUpdateMashScreen
import java.util.*

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.toolbar_scroll.*
import pontini.systems.R
import pontini.systems.room.MashStep
import pontini.systems.view.adapters.AdapterInsertOrUpdateMashStepList
import pontini.systems.view.adapters.ViewHolderMashStep
import pontini.systems.view_model.BundleAwareViewModelFactory


class ActivitityInsertOrUpdateMash : AppCompatActivity(), LifecycleOwner, ViewHolderMashStep.ViewHolderListener {


    override fun onCLickMashStep(mashStep: MashStep) {
        DialogInsertOrUpdateItemMashStep(
            viewModel.idMash,
            mashStep,
            true,
            object : DialogInsertOrUpdateItemMashStep.Callback {

                override fun onClickPositiveButtonUpdate(mashStep: MashStep) {
                    viewModel.onClickUpdateMashStep(mashStep)
                }

                override fun onClickPositiveButtonInsert(
                    name: String,
                    description: String,
                    type: Int,
                    temperature: Double,
                    minutes: GregorianCalendar
                ) {
                }

            }).show(supportFragmentManager, ActivitityInsertOrUpdateMash::class.java.name)

    }

    private lateinit var viewModel: ViewModelInsertOrUpdateMash
    private var adapter: AdapterInsertOrUpdateMashStepList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_insert_or_update_mash)
        initViewModel()
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_frag_insert_or_update_malt, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_save -> {
                viewModel.onClickInsertOrUpdate(etName.text.toString(), etNotes.text.toString())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun changeScreenState(newState: StateInsertOrUpdateMashScreen?) {
        when (newState) {
            StateInsertOrUpdateMashScreen.InitScreen -> onInitScreen()
            StateInsertOrUpdateMashScreen.LoadMash -> onLoadMashUpdate()
            StateInsertOrUpdateMashScreen.WithResult -> onWithResult()
            StateInsertOrUpdateMashScreen.ResultEmpty -> onResultEmpty()
            StateInsertOrUpdateMashScreen.Insert -> onClickInsertOrUpdate()
            StateInsertOrUpdateMashScreen.InsertSucess -> onInsertOrUpdateSucess()
            StateInsertOrUpdateMashScreen.Update -> onClickInsertOrUpdate()
            StateInsertOrUpdateMashScreen.UpdateSucess -> onInsertOrUpdateSucess()
            StateInsertOrUpdateMashScreen.MashStepInsertOrUpdateSucess -> onMashStepInsertOrUpdateSucess()
            StateInsertOrUpdateMashScreen.FieldEmpty -> onShowFieldRequired()
            StateInsertOrUpdateMashScreen.Error -> onShowError()
            else -> {
                toast(resources.getString(R.string.no_records_found))
            }
        }
    }

    private fun onMashStepInsertOrUpdateSucess() {
        toggleProgressBar(false)
        viewModel.mashSteps.let {
            adapter?.dataSource = it
        }
        //TODO Pode colocar

        /*  toggleViewLista(true)
          toggleViewEmptyResult(false)
          toggleMainView(false)
          toggleScroolFab(true)*/
    }

    private fun onShowFieldRequired() {
        viewModel.name.let {
            if (it.value.isNullOrEmpty()) etName.error = resources.getString(R.string.required)
        }
        viewModel.notes.let {
            if (it.value.isNullOrEmpty()) etNotes.error = resources.getString(R.string.required)
        }

    }


    private fun onInsertOrUpdateSucess() {
        toggleProgressBar(false)
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun onClickInsertOrUpdate() {
        toggleProgressBar(true)
    }

    private fun onShowError() {
        toast("${viewModel.throwableError.value!!.message}")
    }

    private fun onResultEmpty() {
//        actionBar!!.title = resources.getString(R.string.insert_mash)
        toggleProgressBar(false)
        toggleMainView(false)
    }

    private fun onWithResult() {
        actionBar!!.title = resources.getString(R.string.alter_mash)
        updateValues()
        toggleProgressBar(false)
        toggleMainView(false)
    }

    private fun updateValues() {
        viewModel.mash.let {
            etName.setText(it.name)
            etNotes.setText(it.note)
        }
        viewModel.mashSteps.let {
            adapter?.dataSource = it
        }


    }

    private fun onLoadMashUpdate() {
        toggleProgressBar(true)
    }

    private fun toggleProgressBar(exibir: Boolean) {
        progressbar.visibility = if (exibir) View.VISIBLE else View.GONE

    }


    private fun onInitScreen() {
        viewModel.name?.value.let {
            if (!it.isNullOrEmpty()) etName.setText(it)
        }
        viewModel.notes?.value.let {
            if (!it.isNullOrEmpty()) etNotes.setText(it)
        }
        viewModel.mashSteps.let {
            adapter?.dataSource = it
        }
        toggleProgressBar(false)
        toggleMainView(false)


    }

    private fun toggleMainView(exibir: Boolean) {
        //TODO Ã© outro..... uma tela de vazio e outro de inicializando.
//        ept_view.visibility = if (exibir) View.VISIBLE else View.GONE
        progressbar.progress = 50
    }

    private fun initView() {
        initFab()
        initRecycleView()
        toggleProgressBar(false)
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.insert_mash)
    }

    private fun initFab() {
        fab.setOnClickListener {
            DialogInsertOrUpdateItemMashStep(object : DialogInsertOrUpdateItemMashStep.Callback {
                override fun onClickPositiveButtonUpdate(mashStep: MashStep) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onClickPositiveButtonInsert(
                    name: String,
                    description: String,
                    type: Int,
                    temperature: Double,
                    minutes: GregorianCalendar
                ) {
                    viewModel.onClickInsertMashStep(name, description, type, temperature, minutes)
                }

            }).show(supportFragmentManager, ActivitityInsertOrUpdateMash::class.java.name)
        }
    }

    private fun initRecycleView() {
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = AdapterInsertOrUpdateMashStepList(this)
        recycler.adapter = adapter

    }

    private fun initViewModel() {
        this?.let {
            var bundleFactory = BundleAwareViewModelFactory<ViewModelInsertOrUpdateMash>(
                it.intent.extras,
                ViewModelProvider.AndroidViewModelFactory.getInstance(it.application)
            )
            viewModel = ViewModelProviders.of(it, bundleFactory).get(ViewModelInsertOrUpdateMash::class.java)
        }
        lifecycle.addObserver(viewModel)

        viewModel.screenState.observe(this, Observer {
            changeScreenState(it)
        })


    }

}