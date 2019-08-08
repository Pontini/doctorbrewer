package pontinisistemas.doctorbrewer.base.ext

import java.text.SimpleDateFormat
import java.util.*


val FORMAT_HH_MM_SS = "HH:mm:ss"
val FORMAT_yyyy_MM_dd_HH_MM = "yyyy/MM/dd HH:mm"

fun convertGregorioCalendarForMinute(gregorianCalendar: GregorianCalendar): Long {

    var minute = gregorianCalendar.get(Calendar.MINUTE)
    var hours = gregorianCalendar.get(Calendar.HOUR_OF_DAY) * 60
    return (minute + hours).toLong()

}


fun convertGregorioCalendarForYYYMMDDHHMM(time: GregorianCalendar): String {

    val format = SimpleDateFormat(FORMAT_yyyy_MM_dd_HH_MM)
    return format.format(time.time)
}


fun convertGregorioCalendarForSeconds(gregorianCalendar: GregorianCalendar): Long {

    var minute = gregorianCalendar.get(Calendar.MINUTE)*60
    var hours = gregorianCalendar.get(Calendar.HOUR_OF_DAY) * 60*60
    return (minute + hours).toLong()
}


fun convertGregorioCalendarForHHMMSS(time: GregorianCalendar): String {

    val format = SimpleDateFormat(FORMAT_HH_MM_SS)
    return format.format(time.time)
}