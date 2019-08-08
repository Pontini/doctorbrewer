package pontini.systems.view_model

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.room.EmptyResultSetException
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pontini.systems.repository.RepositoryMash
import pontini.systems.room.Mash
import pontini.systems.room.MashStep
import pontini.systems.state.StateDetailsMash
import pontinisistemas.doctorbrewer.base.ext.getIdMash

open class ViewModelDetailsMash(application: Application) : ParcelableViewModel(application), LifecycleObserver {


    constructor(application: Application, repositoryMash: RepositoryMash) : this(application) {
        _repositoryMash = repositoryMash
    }

    lateinit var _repositoryMash: RepositoryMash

    var repositoryMash: RepositoryMash? = null
        get() {
            if (!::_repositoryMash.isInitialized) {
                _repositoryMash = RepositoryMash()
            }
            return _repositoryMash
        }


    val name = MutableLiveData<String>()
    val notes = MutableLiveData<String>()
    var screenState = MutableLiveData<StateDetailsMash>()
    val throwableError = MutableLiveData<Throwable>()

    var disposable: Disposable? = null
    var mashId: Long = 0
    var mashSteps: MutableList<MashStep> = mutableListOf()
    lateinit var mash: Mash


    init {
        name.value = ""
        notes.value = ""
        screenState.value = StateDetailsMash.InitScreen
    }

    override fun onReceiveBundle(bundle: Bundle) {
        mashId = getIdMash(bundle)

    }

    override fun postReceiveBundle() {
        if (mashId > 0) {
            loadMashDetails()
        } else {
            throwableError.value = EmptyResultSetException("Not found mash")
            screenState.value = StateDetailsMash.Error
        }
    }


    fun loadMashDetails() {
        screenState.value = StateDetailsMash.LoadMash
        disposeLoader()
        disposable = repositoryMash!!.getMashById(mashId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                mash = it
                repositoryMash!!.getAllMashStepByMashId(mashId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            }.subscribe({
                if (it.isEmpty()) {
                    screenState.value = StateDetailsMash.ResultEmptyMashStep
                } else {
                    mashSteps.clear()
                    mashSteps.addAll(it)
                    this.name.value = mash.name
                    this.notes.value = mash.note
                    screenState.value = StateDetailsMash.WithResult
                }
            }, {
                    throwableError.value = it
                    screenState.value = StateDetailsMash.Error

            })

    }

    private fun disposeLoader() {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unSubscribeViewModel() {
        disposeLoader()

    }

    fun onClickAlter() {

    }



    fun onClickDelete() {
        Single.fromCallable {
            repositoryMash!!.deleteMash(mash)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            screenState.value = StateDetailsMash.DeleteSucess

        }, { t: Throwable? ->

            throwableError.value = t
            screenState.value = StateDetailsMash.Error

        })
    }


}


