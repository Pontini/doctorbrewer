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
import pontini.systems.state.StateInsertOrUpdateMashScreen
import pontinisistemas.doctorbrewer.base.ext.getIdMash
import java.util.*


open class ViewModelInsertOrUpdateMash(application: Application) : ParcelableViewModel(application), LifecycleObserver {


    constructor(application: Application, reposMash: RepositoryMash) : this(application) {
        _repositoryMash = reposMash
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
    var screenState = MutableLiveData<StateInsertOrUpdateMashScreen>()
    val throwableError = MutableLiveData<Throwable>()
    lateinit var mash: Mash
    var mashSteps: MutableList<MashStep>
    var disposable: Disposable? = null
    var idMash: Long

    init {
        idMash=0
        name.value = ""
        notes.value = ""
        mashSteps = mutableListOf()
        screenState.value = StateInsertOrUpdateMashScreen.InitScreen
    }

    override fun onReceiveBundle(bundle: Bundle) {
        idMash = getIdMash(bundle)

    }

    override fun postReceiveBundle() {
        if (idMash > 0) {
            screenState.value = StateInsertOrUpdateMashScreen.LoadMash

            disposable = repositoryMash!!.getMashById(idMash).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    mash = it
                    repositoryMash!!.getAllMashStepByMashId(idMash).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                }.subscribe({
                    mashSteps.clear()
                    mashSteps.addAll(it)
                    updateSequence()
                    screenState.value = StateInsertOrUpdateMashScreen.WithResult

                }, {

                    if (it is EmptyResultSetException) {
                        throwableError.value = it
                        screenState.value = StateInsertOrUpdateMashScreen.Error

                    } else {
                        throwableError.value = it
                        screenState.value = StateInsertOrUpdateMashScreen.Error
                    }
                })

        } else {
            screenState.value = StateInsertOrUpdateMashScreen.ResultEmpty
        }
    }

    private fun updateSequence() {
        for ((sequence, i) in mashSteps.indices.withIndex()) {
            mashSteps[i].sequence = sequence.toLong()
        }
    }

    fun onClickInsertOrUpdate(name: String, notes: String) {
        var isFieldEmpty = isFieldEmpty(name, notes)

        if (isFieldEmpty.not()) {
            if (idMash <= 0) {
                screenState.value = StateInsertOrUpdateMashScreen.Insert
                insertMash(name, notes,mashSteps)
            } else {
                screenState.value = StateInsertOrUpdateMashScreen.Update
                updateMash(name, notes, mashSteps)
            }
        } else {
            screenState.value = StateInsertOrUpdateMashScreen.FieldEmpty
        }
    }

    private fun isFieldEmpty(name: String, notes: String): Boolean {
        var isFieldEmpty = false

        if (name.isEmpty()) {
            this.name.value = ""
            isFieldEmpty = true
        } else {
            this.name.value = name
        }
        if (notes.isEmpty()) {
            this.notes.value = ""
            isFieldEmpty = true
        } else {
            this.notes.value = notes
        }
        return isFieldEmpty
    }

    private fun updateMash(name: String, notes: String, listMashStep: List<MashStep>) {
        var updateMash= Mash(idMash,name,notes)

        Single.fromCallable {
            repositoryMash!!.updateMash(updateMash, mashSteps)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                screenState.value = StateInsertOrUpdateMashScreen.UpdateSucess

            }, { t: Throwable? ->
                throwableError.value = t
                screenState.value = StateInsertOrUpdateMashScreen.Error

            })
    }

    private fun insertMash(name: String, notes: String, listMashStep: List<MashStep>) {
        Single.fromCallable { repositoryMash!!.insertMash(Mash(0, name, notes), listMashStep) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe({

                screenState.value = StateInsertOrUpdateMashScreen.InsertSucess

            }, { t: Throwable? ->

                throwableError.value = t
                screenState.value = StateInsertOrUpdateMashScreen.Error
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

    fun onClickInsertMashStep(name: String, description: String, type: Int, temperature: Double, time: GregorianCalendar) {

        var sequence=mashSteps.size+1.toLong();

        var newMashStep = MashStep(0, 0, name, description, type, temperature, time)
        newMashStep.sequence=sequence

        mashSteps.add(newMashStep)

        screenState.value = StateInsertOrUpdateMashScreen.MashStepInsertOrUpdateSucess
    }

    fun onClickUpdateMashStep(item: MashStep) {
        for (i in mashSteps.indices) {
            if (item.sequence == mashSteps[i].sequence) {
                mashSteps[i] = item
            }
        }
        screenState.value = StateInsertOrUpdateMashScreen.MashStepInsertOrUpdateSucess
    }

}


