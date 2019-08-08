package pontini.systems.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pontini.systems.room.converters.DataConverter
import pontini.systems.room.dao.BoilDao
import pontini.systems.room.dao.MashDao
import pontini.systems.room.dao.MashStepDao
import pontini.systems.room.converters.DateTimeConverter
import pontinisistemas.doctorbrewer.room.converters.HoraConverter


@Database(entities = [Mash::class, MashStep::class, Boil::class], version = 119)

@TypeConverters(DataConverter::class, HoraConverter::class, DateTimeConverter::class)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun mashDao(): MashDao
    abstract fun mashStepDao(): MashStepDao
    abstract fun boilDao(): BoilDao

    companion object {
        const val ROOM_DATABASE_NAME = "doctorbrewer.db"
    }

}
