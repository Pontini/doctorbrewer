package pontini.systems.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.jetbrains.anko.support.v4.toast
import pontini.systems.state.StateGenericListScreen

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.appbar_scroll.*
import kotlinx.android.synthetic.main.dlg_progress.*
import kotlinx.android.synthetic.main.ept_view.*
import kotlinx.android.synthetic.main.fab.*
import kotlinx.android.synthetic.main.recycler.*
import kotlinx.android.synthetic.main.toolbar_scroll.*
import pontini.systems.R
import pontini.systems.addOnScrollListenerWithFab
import pontini.systems.disableScroll
import pontini.systems.enableScroll
import pontini.systems.view.adapters.AdapterBoilList
import pontini.systems.view.adapters.ViewHolderBoil
import pontini.systems.view_model.ViewModelBoilList

class FragmentListBoil : Fragment(), ViewHolderBoil.ViewHolderListener  {

    override fun onCLickBoil(idBoil: Long) {
    }

    private lateinit var viewModel: ViewModelBoilList
    private var adapter: AdapterBoilList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        this?.let {
            viewModel = ViewModelProviders.of(it).get(ViewModelBoilList::class.java)
        }
        lifecycle.addObserver(viewModel)

        viewModel.screenState.observe(this, Observer
        {
            changeScreenState(it!!)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.frag_generic_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        changeScreenState(viewModel.screenState.value!!)
    }

    private fun changeScreenState(newState: StateGenericListScreen?) {
        when (newState) {
            StateGenericListScreen.Init -> onInitScreen()
            StateGenericListScreen.Load -> onLoad()
            StateGenericListScreen.WithResult -> onShowResult()
            StateGenericListScreen.ResultEmpty -> onShowEmptyResult()
            StateGenericListScreen.Error -> onShowError()
            else -> {
                toast(context!!.resources.getString(R.string.no_records_found))
            }
        }
    }

    private fun onShowError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun onShowEmptyResult() {
        toggleProgressBar(false)
        toggleViewLista(false)
        toggleViewSemResultado(true)
        toggleScroolFab(false)
        toggleMainView(true)
    }

    private fun toggleScroolFab(enable: Boolean) {
        if (enable) {
            enableScroll(toolbar)

        } else {
            appbar_scroll.setExpanded(true, true)
            disableScroll(toolbar)
        }
    }

    private fun onShowResult() {
        toggleProgressBar(false)
        viewModel.listBoil.let {
            adapter?.dataSource = it
        }
        toggleViewLista(true)
        toggleViewSemResultado(false)
        toggleMainView(false)
        toggleScroolFab(true)
    }

    private fun onInitScreen() {
        initViews()
        toggleProgressBar(false)
        toggleViewLista(false)
        toggleViewSemResultado(false)
        toggleMainView(true)
        viewModel.load()
    }
    private fun initViews() {
        initRecycleView()
        initToolbar()
        initFab()
        addOnScrollListenerWithFab(recycler, fab)
    }
    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        val toolbar = toolbar
        toolbar.title = resources.getString(R.string.boil)

    }

    private fun initRecycleView() {
        recycler.layoutManager = LinearLayoutManager(activity)
        adapter = AdapterBoilList(this)
        recycler.adapter = adapter

    }
    private fun initFab() {
        fab.setOnClickListener {

        }
    }
    private fun onLoad() {
        toggleProgressBar(true)
    }


    private fun toggleProgressBar(exibir: Boolean) {
        progressbar.visibility = if (exibir) View.VISIBLE else View.GONE

    }

    private fun toggleViewLista(exibirLista: Boolean) {
        recycler.visibility = if (exibirLista) View.VISIBLE else View.GONE

    }

    private fun toggleViewSemResultado(exibir: Boolean) {
        ept_view.visibility = if (exibir) View.VISIBLE else View.GONE

    }

    private fun toggleMainView(exibir: Boolean) {
        //TODO é outro..... uma tela de vazio e outro de inicializando.
        ept_view.visibility = if (exibir) View.VISIBLE else View.GONE
        progressbar.setProgress(50)
    }

}