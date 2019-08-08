package pontini.systems.repository


import io.reactivex.Single
import pontini.systems.room.DatabaseRoom
import pontini.systems.room.Mash
import pontini.systems.room.MashStep
import pontini.systems.room.dao.MashDao
import pontini.systems.room.dao.MashStepDao
import pontinisistemas.doctorbrewer.room.DatabaseCreator
import java.util.*

class RepositoryMash(open val database: DatabaseRoom) {

    private val mashDao: MashDao = database.mashDao()
    private val mashStepDao: MashStepDao = database.mashStepDao()


    constructor() : this((DatabaseCreator.database as DatabaseRoom))


    fun updateMash(mash: Mash, mashSteps: List<MashStep>) {
        database.runInTransaction {
            try {
                mashDao.update(mash)
                for (i in mashSteps.indices) {
                    mashSteps[i].fkIdMash = mash.id
                }
                mashSteps.forEach()
                {
                    mashStepDao.insert(it)
                }

            } catch (e: Exception) {
                throw Exception(" [${e.message}]", e)
            }
        }
    }


    fun insertMash(mash: Mash, mashSteps: List<MashStep>) {
        database.runInTransaction {
            try {
                var id = mashDao.insert(mash)
                for (i in mashSteps.indices) {
                    mashSteps[i].fkIdMash = id
                }
                mashStepDao.insert(mashSteps)
            } catch (e: Exception) {
                throw Exception(" [${e.message}]", e)
            }
        }
    }


    fun getMashById(id: Long): Single<Mash> {
        return mashDao.getMashById(id)
    }
    fun getMashByIdMock(id: Long): Mash {
        return Mash(1,"Boddy Highy","");
    }
    fun getAllMash(): Single<List<Mash>> {
        return mashDao.getAllMash()
    }

    fun getAllMashStepByMashId(idMash: Long): Single<List<MashStep>> {
        return mashStepDao.getAllMashStepByMashId(idMash)
    }


    fun getAllMashStepByMashIdMock(idMash: Long): List<MashStep> {
        return listOf(MashStep(1,1,"","",1,75.0,GregorianCalendar()))
    }

    fun deleteMash(mashId: Mash) {
        database.runInTransaction {
            try {
                mashDao.delete(mashId)
            } catch (e: Exception) {
                throw Exception(" [${e.message}]", e)
            }
        }
    }

    fun getCountStepMash(idMash: Long): Single<Int> {
        return mashStepDao.getCountStepMash(idMash)
    }


}