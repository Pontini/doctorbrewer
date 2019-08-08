package pontini.systems.room.dao

import androidx.room.*
import io.reactivex.Single
import pontini.systems.room.Mash

@Dao
abstract class MashDao {

    @Insert
    abstract fun insert(mash: Mash):Long

    @Delete
    abstract fun delete(mash: Mash)

    @Update
    abstract fun update(mash: Mash): Int

    @Query("SELECT * FROM Mash WHERE Mash.id=:id")
    abstract fun getMashById(id: Long): Single<Mash>

    @Query("SELECT * FROM Mash")
    abstract fun getAllMash(): Single<List<Mash>>


    /* @Query("SELECT * FROM MashStep ORDER BY id ASC")
     abstract fun getLastIdMashStep(): Single<Long>*/


}