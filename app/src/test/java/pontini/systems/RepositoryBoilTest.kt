package pontini.systems

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pontini.systems.repository.RepositoryBoil
import pontini.systems.room.Boil
import pontini.systems.room.DatabaseRoom
import java.util.*

@RunWith(RobolectricTestRunner::class)
open class RepositoryBoilTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: DatabaseRoom
    private lateinit var boil: Boil

    @Before
    fun initDb() {

        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, DatabaseRoom::class.java)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries().build()
        insertBoil()
    }


    private fun insertBoil() {
        database.runInTransaction {
            try {
                var time = GregorianCalendar()
                boil = Boil(1, "Profile", time,"Description")
                var repository = RepositoryBoil(database)
                repository.insertHop(boil)
            } catch (e: Exception) {
                throw Exception(" [${e.message}]", e)
            }
        }
    }


    @Test
    fun getAllBoil(){
        var repository = RepositoryBoil(database)
        repository.getAllBoil().test().assertSubscribed().assertNoErrors().assertValue {
            assert(it.isNotEmpty().equals(true))
            assert(it[0].id == boil.id)
            assert(it[0].name == boil.name)
            assert(it[0].time.get(GregorianCalendar.HOUR) == boil.time.get(GregorianCalendar.HOUR))
            assert(it[0].time.get(GregorianCalendar.MINUTE) == boil.time.get(GregorianCalendar.MINUTE))
            assert(it[0].note == boil.note)
            true
        }.assertComplete()
    }

}
