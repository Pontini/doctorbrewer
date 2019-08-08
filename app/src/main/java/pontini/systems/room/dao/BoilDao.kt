package pontini.systems.room.dao

import androidx.room.*
import io.reactivex.Single
import pontini.systems.room.Boil

@Dao
abstract class BoilDao {

    @Insert
    abstract fun insert(boil: Boil):Long

    @Delete
    abstract fun delete(boil: Boil)

    @Update
    abstract fun update(boil: Boil): Int

    @Query("SELECT * FROM Boil WHERE Boil.id=:id")
    abstract fun getBoilById(id: Long): Single<Boil>

    @Query("SELECT * FROM Boil")
    abstract fun getAllBoil(): Single<List<Boil>>




}