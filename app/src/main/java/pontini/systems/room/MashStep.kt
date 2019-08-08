package pontini.systems.room

import androidx.room.*
import pontini.systems.room.converters.DateTimeConverter
import java.util.*


@Entity(foreignKeys = arrayOf(
        ForeignKey(entity = Mash::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("fk_id_mash"),
        onDelete = ForeignKey.CASCADE)
))
data class MashStep(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Long = 0,
        @ColumnInfo(name = "fk_id_mash")
        var fkIdMash: Long,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "description")
        val description: String,
        @ColumnInfo(name = "type")
        val type: Int,
        @ColumnInfo(name = "temperature")
        val temperature: Double,
        @ColumnInfo(name = "time")
        @TypeConverters(DateTimeConverter::class)
        val time: GregorianCalendar = GregorianCalendar()) {
        @Ignore
        var sequence:Long = 0
}





