package pontini.systems

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pontini.systems.repository.RepositoryMash
import pontini.systems.room.DatabaseRoom
import pontini.systems.room.Mash
import pontini.systems.room.MashStep
import java.util.*

@RunWith(RobolectricTestRunner::class)
open class RepositoryMashTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: DatabaseRoom
    private lateinit var mash: Mash
    private lateinit var mashStep: MashStep

    @Before
    fun initDb() {

        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, DatabaseRoom::class.java)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build()
        insertMash()

    }


    private fun insertMash() {
        database.runInTransaction() {
            try {
                mash = Mash(1, "Mash 65 C", "Corpo leve")
                mashStep = MashStep(1, 1, "", "",1, 57.5, GregorianCalendar(0, 0, 0, 1, 0))
                var repositoryMash = RepositoryMash(database)
                repositoryMash.insertMash(mash, listOf(mashStep))
            } catch (e: Exception) {
                throw Exception(" [${e.message}]", e)
            }
        }
    }



    @Test
    fun getAllMash(){
        var repositoryMash = RepositoryMash(database)
        repositoryMash.getAllMash().test().assertSubscribed().assertNoErrors().assertValue {
            assert(it.isNotEmpty().equals(true))
            assert(it[0].id.equals(mash.id))
            assert(it[0].name.equals(mash.name))
            assert(it[0].note.equals(mash.note))

            true
        }.assertComplete()
    }

}
