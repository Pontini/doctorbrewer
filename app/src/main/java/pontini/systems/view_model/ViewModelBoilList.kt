package pontini.systems.view_model

import android.app.Application
import androidx.lifecycle.*
import androidx.room.EmptyResultSetException
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pontini.systems.repository.RepositoryBoil
import pontini.systems.room.Boil
import pontini.systems.state.StateGenericListScreen
import java.util.concurrent.TimeUnit

open class ViewModelBoilList(application: Application) : AndroidViewModel(application), LifecycleObserver {

    constructor(application: Application, RepositoryBoil: RepositoryBoil) : this(application) {
        _repository = RepositoryBoil
    }

    lateinit var _repository: RepositoryBoil


    var repository: RepositoryBoil? = null
        get() {
            if (!::_repository.isInitialized) {
                _repository = RepositoryBoil()
            }
            return _repository
        }


    val showingSearchView = MutableLiveData<Boolean>()
    var searchView = MutableLiveData<String>()
    var disposableLoad: Disposable? = null
    var listBoil: List<Boil> = arrayListOf()
    var screenState = MutableLiveData<StateGenericListScreen>()
    val throwableError = MutableLiveData<Throwable>()
  //  var filter: FilterIngredientList


    init {
        //filter = FilterIngredientList()
        screenState.value = StateGenericListScreen.Init

    }

    fun onQueryTextChange(searchText: String) {
        val DELAY_SEARCHVIEW: Long = 1

        disposableLoad?.let {
            if (!it.isDisposed) it.dispose()
        }

        disposableLoad = Single.just(searchText)
            .observeOn(AndroidSchedulers.mainThread())
            .delay(DELAY_SEARCHVIEW, TimeUnit.SECONDS, Schedulers.computation())
            .subscribe({

                if (!isSearchEquals(searchText)) {
                    searchView?.postValue(searchText)
                    //filter.searchText = searchText
                }

            }, { t: Throwable? -> t!!.printStackTrace() })


    }

    fun isSearchEquals(compareTexte: String): Boolean {

        if (searchView?.value.isNullOrEmpty() && compareTexte.isNullOrEmpty()) {
            return true
        }

        return searchView?.value.equals(compareTexte)
    }

    fun load() {
        screenState.value = StateGenericListScreen.Load
        disposeLoader()
        disposableLoad = repository!!.getAllBoil().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it.isNotEmpty()) {
                listBoil = it
                screenState.value = StateGenericListScreen.WithResult
            } else {
                screenState.value = StateGenericListScreen.ResultEmpty

            }

        }, {

            if (it is EmptyResultSetException) {
                screenState.value = StateGenericListScreen.ResultEmpty
                throwableError.value = it
            } else {
                screenState.value = StateGenericListScreen.Error
                throwableError.value = it
            }
        })
    }


    private fun disposeLoader() {
        disposableLoad?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unSubscribeViewModel() {
        disposeLoader()

    }



}

