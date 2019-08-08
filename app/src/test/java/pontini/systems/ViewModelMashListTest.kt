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
import pontini.systems.repository.RepositoryMash
import pontini.systems.room.Mash
import pontini.systems.state.StateGenericListScreen
import pontini.systems.view_model.ViewModelMashList


@RunWith(AndroidJUnit4::class)
open class ViewModelMashListTest {

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
        val mockRepositoryMash = mock<RepositoryMash> {
            on {
                getAllMash()
            } doReturn Single.error(Throwable())
        }

        val viewModel = ViewModelMashList(applicationMock, mockRepositoryMash)
        viewModel.loadMash()
        assertThat(viewModel.screenState.value).isEqualTo(StateGenericListScreen.Error)
    }

    @Test
    fun stateWithResult() {
        var mash = Mash(0, "Mash 65", "Corpo Leve")
        val mockRepositoryMash = mock<RepositoryMash> {
            on {
                getAllMash()
            } doReturn Single.just(listOf(mash))

        }
        val viewModel = ViewModelMashList(applicationMock, mockRepositoryMash)
        viewModel.loadMash()
        assertThat(viewModel.screenState.value).isEqualTo(StateGenericListScreen.WithResult)
    }

    @Test
    fun stateWithResultEmpty() {
        val mockRepositoryMash = mock<RepositoryMash> {
            on {
                getAllMash()
            } doReturn Single.just(listOf())
        }
        val viewModel = ViewModelMashList(applicationMock, mockRepositoryMash)
        viewModel.loadMash()
        assertThat(viewModel.screenState.value).isEqualTo(StateGenericListScreen.ResultEmpty)
    }
}