package pontini.systems.room.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateTimeConverter{
    private fun stringParaCalendar(strData: String): GregorianCalendar? {

        try {

            val calendar = GregorianCalendar()
            calendar.clear()
            calendar.time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strData)
            return calendar

        } catch (var3: Exception) {
            var3.printStackTrace()
        }

        return null
    }

    private fun calendarParaString(calendar: GregorianCalendar?): String? {
        val formatBra = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        try {
            return formatBra.format(calendar?.time)
        } catch (var3: Exception) {
            var3.printStackTrace()
            return null
        }

    }


    @TypeConverter
    fun fromSqlFormat(value: String?): GregorianCalendar? {

        return when (value) {
            null -> null
            else -> stringParaCalendar(value)
        }
    }

    @TypeConverter
    fun toSqlFormat(calendar: GregorianCalendar?): String? {

        return when (calendar) {
            null -> null
            else -> calendarParaString(calendar)
        }
    }
}