package pontini.systems.room

import androidx.room.*
import pontini.systems.room.converters.DateTimeConverter
import java.util.*

@Entity(indices = [Index(value = arrayOf("id"), unique = true)])
data class Boil(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id" )
        val id: Long=0,
        @ColumnInfo(name = "description" )
        val name: String,
        @TypeConverters(DateTimeConverter::class)
        val time: GregorianCalendar = GregorianCalendar(),
        @ColumnInfo(name = "notes" )
        val note:String){
}
