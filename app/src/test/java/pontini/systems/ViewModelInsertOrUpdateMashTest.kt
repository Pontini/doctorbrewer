import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.MockitoAnnotations
import pontini.systems.RxTrampolineSchedulerRule
import pontini.systems.repository.RepositoryMash
import pontini.systems.room.DatabaseRoom
import pontini.systems.room.Mash
import pontini.systems.room.MashStep
import pontini.systems.state.StateDetailsMash
import pontini.systems.state.StateInsertOrUpdateMashScreen
import pontini.systems.view_model.ViewModelDetailsMash
import pontini.systems.view_model.ViewModelInsertOrUpdateMash
import pontinisistemas.doctorbrewer.base.ext.addIdMash
import java.util.*


@RunWith(AndroidJUnit4::class)
open class ViewModelInsertOrUpdateMashTest {


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    @Rule
    @JvmField
    var schedulerRule = RxTrampolineSchedulerRule()

    lateinit var applicationMock: Application
    lateinit var sharedPrefs: SharedPreferences
    private var idMash: Long = 10
    private lateinit var database: DatabaseRoom
    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), DatabaseRoom::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

       // insertMash()

        MockitoAnnotations.initMocks(this)

        val sharedEditor = mock<SharedPreferences.Editor> {
            on {

                putInt(any(), ArgumentMatchers.anyInt())
            } doReturn it

        }

        sharedPrefs = mock<SharedPreferences> {
            on {
                getInt(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
            } doReturn 0

            on {
                edit()
            } doReturn sharedEditor

        }

        applicationMock = mock<Application> {

            on {
                getSharedPreferences(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
            } doReturn sharedPrefs
        }

    }
    private fun insertMash() {
        var mash= Mash(idMash, "Cascade", "Hop American")
        var idMash = database.mashDao().insert(mash)
    }


    @Test
    fun onReceiveBundle() {
        val mockReposMash = mock<RepositoryMash> {}
        val viewModel = ViewModelInsertOrUpdateMash(applicationMock, mockReposMash)
        var bundle = Bundle()
        addIdMash(bundle, idMash)
        viewModel.onReceiveBundle(bundle)
        assertThat(viewModel.idMash).isEqualTo(idMash)
        assertThat(viewModel.screenState.value).isEqualTo(StateInsertOrUpdateMashScreen.InitScreen)
    }

    @Test
    fun onPostReceiveBundleWithResult() {
        var mash = Mash(idMash, "Mash 65", "Corpo Leve")
        var mashStep = MashStep(10,mash.id,"","",1,65.0, GregorianCalendar())
        val mockReposMash = mock<RepositoryMash> {
            on {
                getAllMash()
            } doReturn Single.just(listOf(mash))

            on {
                getMashById(idMash)
            } doReturn Single.just(mash)

            on {
                getAllMashStepByMashId(idMash)
            } doReturn Single.just(listOf(mashStep))
        }

        val viewModel = ViewModelInsertOrUpdateMash(applicationMock, mockReposMash)
        var bundle = Bundle()
        addIdMash(bundle, idMash)
        viewModel.onReceiveBundle(bundle)
        viewModel.postReceiveBundle()
        assertThat(viewModel.screenState.value).isEqualTo(StateInsertOrUpdateMashScreen.WithResult)
    }


    @Test
    fun onPostReceiveBundleWithError() {
        var mash = Mash(idMash, "Mash 65", "Corpo Leve")
        var mashStep = MashStep(10,mash.id,"","",1,65.0, GregorianCalendar())
        val mockReposMash = mock<RepositoryMash> {
            on {
                getAllMash()
            } doReturn Single.error(Throwable())

            on {
                getMashById(idMash)
            } doReturn Single.just(mash)

            on {
                getAllMashStepByMashId(idMash)
            } doReturn Single.error(Throwable())
        }

        val viewModel = ViewModelInsertOrUpdateMash(applicationMock, mockReposMash)
        var bundle = Bundle()
        addIdMash(bundle, idMash)
        viewModel.onReceiveBundle(bundle)
        viewModel.postReceiveBundle()
        assertThat(viewModel.screenState.value).isEqualTo(StateInsertOrUpdateMashScreen.Error)
    }

    @Test
    fun onClickInsertOrUpdate() {
        var mash = Mash(idMash, "Mash 65", "Corpo Leve")
        var mashStep = MashStep(10,mash.id,"","",1,65.0, GregorianCalendar())
        val mockReposMash = mock<RepositoryMash> {
            on {
                getAllMash()
            } doReturn Single.error(Throwable())

            on {
                getMashById(idMash)
            } doReturn Single.just(mash)

            on {
                getAllMashStepByMashId(idMash)
            } doReturn Single.error(Throwable())
        }
        val viewModel = ViewModelInsertOrUpdateMash(applicationMock, mockReposMash)
        var bundle = Bundle()
        addIdMash(bundle, idMash)
        viewModel.onReceiveBundle(bundle)
        viewModel.postReceiveBundle()

        viewModel.onClickInsertMashStep()

        assertThat(viewModel.screenState.value).isEqualTo(StateInsertOrUpdateMashScreen.Error)
    }



}