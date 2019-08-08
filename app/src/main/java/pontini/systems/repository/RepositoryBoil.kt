package pontini.systems.repository


import io.reactivex.Single
import pontini.systems.room.Boil
import pontini.systems.room.DatabaseRoom
import pontini.systems.room.dao.BoilDao
import pontinisistemas.doctorbrewer.room.DatabaseCreator
import java.util.*

class RepositoryBoil(open val database: DatabaseRoom) {

    private val boiDao: BoilDao = database.boilDao()
    constructor() : this((DatabaseCreator.database as DatabaseRoom))

    fun insertHop(boil: Boil): Long {
        var id: Long = 0
        database.runInTransaction {
            try {
                id = boiDao.insert(boil)
            } catch (e: Exception) {
                throw Exception(" [${e.message}]", e)
            }

        }
        return id
    }

    fun getAllBoil(): Single<List<Boil>> {
        return boiDao.getAllBoil()
    }

    fun getAllBoilMock(): List<Boil> {
        return listOf<Boil>(Boil(1,"Profile", GregorianCalendar(),"Ipa"))
    }

}