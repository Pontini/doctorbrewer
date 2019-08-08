package pontinisistemas.doctorbrewer.room.converters

import java.util.*

class GregorianCalendarData: GregorianCalendar {
    constructor(year: Int, month: Int, dayOfMonth: Int): super(year, month, dayOfMonth)
    constructor(): super()

}