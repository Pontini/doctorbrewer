package pontini.systems.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.appbar_scroll_details_mash.appbar_scroll
import kotlinx.android.synthetic.main.ept_view.*
import kotlinx.android.synthetic.main.recycler.*
import kotlinx.android.synthetic.main.toolbar_scroll_details_mash.toolbar
import org.jetbrains.anko.toast
import pontini.systems.R
import pontini.systems.disableScroll
import pontini.systems.enableScroll
import pontini.systems.room.MashStep
import pontini.systems.state.StateDetailsMash
import pontini.systems.view.adapters.AdapterInsertOrUpdateMashStepList
import pontini.systems.view.adapters.ViewHolderMashStep
import pontini.systems.view_model.BundleAwareViewModelFactory
import pontini.systems.view_model.ViewModelDetailsMash
import pontinisistemas.doctorbrewer.base.ext.addIdMash


class ActivityDetailsMash : AppCompatActivity(), LifecycleOwner, ViewHolderMashStep.ViewHolderListener {


    private lateinit var viewModel: ViewModelDetailsMash
    private lateinit var tvValueName: TextView
    private lateinit var tvValueNotes: TextView
    private var adapter: AdapterInsertOrUpdateMashStepList? = null
    private lateinit var tvLabelName: TextView
    private lateinit var tvLabelNotes: TextView

    private val REQUEST_CODE_UPDATE_MASH = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_details_mash)
        initViewModel()
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_frag_details_mash, menu)
        return true
    }

    override fun onCLickMashStep(mashStep: MashStep) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_alter -> {
                onClickAlter()
                true
            }
            R.id.menu_delete -> {
                onCLickMenuDelete()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun onClickAlter() {
        //val intent = Intent(this, ActivitityInsertOrUpdateMash::class.java)
        //addIdMash(intent, viewModel.mashId)
        //this.startActivityForResult(intent, REQUEST_CODE_UPDATE_MASH)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_UPDATE_MASH) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.loadMashDetails()
            }
        }
    }

    private fun onCLickMenuDelete() {

    }

    private fun onDeleteSucess() {
        toggleProgressBar(false)
        toast(resources.getString(R.string.deleted))
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun onClickInsertOrUpdate() {
        toggleProgressBar(true)
    }

    private fun onShowError() {
        toast("${viewModel.throwableError.value!!.message}")
        this.finish()
    }


    private fun onWithResult() {

        updateValues()
        toggleProgressBar(false)
    }

    private fun updateValues() {
        /*  viewModel.mash.let {
              tvValueName.setText(it.name)
              tvValueNotes.setText(it.note)
          }*/
        viewModel.mashSteps.let {
            adapter?.dataSource = it
        }

    }

    private fun onLoadMashDetails() {
        toggleProgressBar(true)
    }

    private fun toggleProgressBar(exibir: Boolean) {
        //progressbar.visibility = if (exibir) View.VISIBLE else View.GONE
    }

    private fun onInitScreen() {

        viewModel.name?.value.let {
            if (!it.isNullOrEmpty()) tvValueName.text = it
        }
        viewModel.notes?.value.let {
            if (!it.isNullOrEmpty()) tvValueNotes.text = it
        }
        viewModel.mashSteps.let {
            adapter?.dataSource = it
        }
        toggleProgressBar(false)
        toggleMainView(false)

    }

    private fun toggleMainView(exibir: Boolean) {
        //TODO é outro..... uma tela de vazio e outro de inicializando.
//        ept_view.visibility = if (exibir) View.VISIBLE else View.GONE
        //progressbar.setProgress(50)
    }

    private fun initView() {
        initRecycleView()
        toggleProgressBar(false)
        toolbar!!.title = resources.getString(R.string.mash_details)
        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun initRecycleView() {
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = AdapterInsertOrUpdateMashStepList(this)
        recycler.adapter = adapter

    }

    private fun initViewModel() {
        this?.let {
            var bundleFactory = BundleAwareViewModelFactory<ViewModelDetailsMash>(
                it.intent.extras,
                ViewModelProvider.AndroidViewModelFactory.getInstance(it.application)
            )
            viewModel = ViewModelProviders.of(it, bundleFactory).get(ViewModelDetailsMash::class.java)
        }
        lifecycle.addObserver(viewModel)
        viewModel.screenState.observe(this, Observer {
            changeScreenState(it)
        })


    }

    private fun changeScreenState(newState: StateDetailsMash?) {
        when (newState) {
            StateDetailsMash.InitScreen -> onInitScreen()
            StateDetailsMash.LoadMash -> onLoadMashDetails()
            StateDetailsMash.WithResult -> onWithResult()
            StateDetailsMash.ResultEmptyMashStep -> onWithResultEmptyMashStep()
            StateDetailsMash.DeleteSucess -> onDeleteSucess()
            StateDetailsMash.Error -> onShowError()
            else -> {
                toast(resources.getString(R.string.no_records_found))
            }
        }
    }


    private fun onWithResultEmptyMashStep() {
        toggleProgressBar(false)
        toggleViewLista(false)
        toggleViewSemResultado(true)
        toggleScroolFab(false)
        toggleMainView(true)
    }


    private fun toggleViewLista(exibirLista: Boolean) {
        recycler.visibility = if (exibirLista) View.VISIBLE else View.GONE

    }

    private fun toggleViewSemResultado(exibir: Boolean) {
        ept_view.visibility = if (exibir) View.VISIBLE else View.GONE

    }

    private fun toggleScroolFab(enable: Boolean) {
        if (enable) {
            enableScroll(toolbar)

        } else {
            appbar_scroll.setExpanded(true, true)
            disableScroll(toolbar)
        }
    }

    companion object {
        fun start(ctx: Context, idMash: Long) {
        }

    }
}