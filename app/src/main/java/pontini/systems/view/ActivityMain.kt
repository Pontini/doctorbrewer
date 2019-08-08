package pontini.systems.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.toast
import pontini.systems.room.DatabaseRoom
import pontini.systems.state.StateMain
import pontini.systems.view_model.ViewModelMain
import pontinisistemas.doctorbrewer.room.DatabaseCreator


class ActivityMain : AppCompatActivity() {


    private lateinit var viewModel: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ViewModelMain::class.java)
        lifecycle.addObserver(viewModel)
        viewModel.stateScreen.observe(this, Observer {
            when(it){
                StateMain.init-> onStateInit()
                else -> {
                    toast("State not found [$it]")
                }
            }
        })
    }

    private fun onStateInit() {
        DatabaseCreator.createDb(this, DatabaseRoom::class.java, DatabaseRoom.ROOM_DATABASE_NAME)
        DatabaseCreator.isDatabaseCreated.observe(this, Observer {
           if(it==true){
               val intent = Intent(this, ActivityNavigationBottom::class.java)
               startActivity(intent)
               finish()
           }
        })
    }
}