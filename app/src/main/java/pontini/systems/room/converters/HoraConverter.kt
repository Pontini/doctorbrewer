package pontinisistemas.doctorbrewer.room.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat

class HoraConverter {

    private fun stringParaCalendar(strData: String): GregorianCalendarHora? {

        try {

            val calendar = GregorianCalendarHora()
            calendar.clear()
            calendar.time = SimpleDateFormat("HH:mm:ss").parse(strData)
            return calendar

        } catch (var3: Exception) {
            var3.printStackTrace()
        }

        return null
    }

    private fun calendarParaString(calendar: GregorianCalendarHora?): String? {
        val formatBra = SimpleDateFormat("HH:mm:ss")

        try {
            return formatBra.format(calendar?.time)
        } catch (var3: Exception) {
            var3.printStackTrace()
            return null
        }

    }


    @TypeConverter
    fun fromSqlFormat(value: String?): GregorianCalendarHora? {

        return when (value) {
            null -> null
            else -> stringParaCalendar(value)
        }
    }

    @TypeConverter
    fun toSqlFormat(calendar: GregorianCalendarHora?): String? {

        return when (calendar) {
            null -> null
            else -> calendarParaString(calendar)
        }
    }
}