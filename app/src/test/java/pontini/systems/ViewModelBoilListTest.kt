package pontini.systems

import android.app.Application
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.MockitoAnnotations
import pontini.systems.repository.RepositoryBoil
import pontini.systems.state.StateGenericListScreen
import pontini.systems.view_model.ViewModelBoilList


@RunWith(AndroidJUnit4::class)
open class ViewModelBoilListTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    @Rule
    @JvmField
    var schedulerRule = RxTrampolineSchedulerRule()

    lateinit var applicationMock: Application
    lateinit var sharedPrefs: SharedPreferences

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val sharedEditor = mock<SharedPreferences.Editor> {
            on {
                putInt(any(), ArgumentMatchers.anyInt())
            } doReturn it

        }

        sharedPrefs = mock {
            on {
                getInt(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
            } doReturn 0
            on {
                edit()
            } doReturn sharedEditor

        }

        applicationMock = mock {

            on {
                getSharedPreferences(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
            } doReturn sharedPrefs
        }

    }


    @Test
    fun stateWithError() {
        val mockRepository = mock<RepositoryBoil> {
            on {
                getAllBoil()
            } doReturn Single.error(Throwable())
        }
        val viewModel = ViewModelBoilList(applicationMock, mockRepository)
        viewModel.load()
        assertThat(viewModel.screenState.value).isEqualTo(StateGenericListScreen.Error)
    }

    @Test
    fun stateWithResult() {

    }

    @Test
    fun stateWithResultEmpty() {

    }
}