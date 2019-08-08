package pontini.systems.room.dao

import androidx.room.*
import io.reactivex.Single
import pontini.systems.room.MashStep

@Dao
abstract class MashStepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(mashStep: MashStep)

    @Insert
    abstract fun insert(mashStep: List<MashStep>) :List<Long>

    @Delete
    abstract fun delete(mashStep: MashStep)

    @Update
    abstract fun update(mashStep: MashStep): Int

    @Update
    abstract fun update(mashStep: List<MashStep>): Int

    @Query("SELECT * FROM MashStep WHERE MashStep.fk_id_mash=:idMash")
    abstract fun getAllMashStepByMashId(idMash:Long): Single<List<MashStep>>

    @Query("SELECT COUNT(MashStep.fk_id_mash)FROM MashStep WHERE MashStep.fk_id_mash=:idMash")
    abstract fun getCountStepMash(idMash: Long): Single<Int>

}