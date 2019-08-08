package pontini.systems.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import pontini.systems.state.StateMain



open class ViewModelMain(application: Application) : AndroidViewModel(application), LifecycleObserver {

    var stateScreen = MutableLiveData<StateMain>()

    init {
        stateScreen.value = StateMain.init
    }




}

