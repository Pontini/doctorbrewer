package pontini.systems.room.converters

import androidx.room.TypeConverter
import pontinisistemas.doctorbrewer.room.converters.GregorianCalendarData
import java.text.SimpleDateFormat

class DataConverter {

    private fun stringParaCalendar(strData: String): GregorianCalendarData? {

        try {

            val calendar = GregorianCalendarData()
            calendar.clear()
            calendar.time = SimpleDateFormat("yyyy-MM-dd").parse(strData)
            return calendar

        } catch (var3: Exception) {
            var3.printStackTrace()
        }

        return null
    }

    private fun calendarParaString(calendar: GregorianCalendarData?): String? {
        val formatBra = SimpleDateFormat("yyyy-MM-dd")

        try {
            return formatBra.format(calendar?.time)
        } catch (var3: Exception) {
            var3.printStackTrace()
            return null
        }

    }


    @TypeConverter
    fun fromSqlFormat(value: String?): GregorianCalendarData? {

        return when (value) {
            null -> null
            else -> stringParaCalendar(value)
        }
    }

    @TypeConverter
    fun toSqlFormat(calendar: GregorianCalendarData?): String? {

        return when (calendar) {
            null -> null
            else -> calendarParaString(calendar)
        }
    }
}