package pt.ipca.escutas.utils

import java.util.Calendar
import java.util.Date

object DateUtils {
    fun DateValue(year: Int, month: Int, day: Int): Date {

        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = year
        cal[Calendar.MONTH] = month
        cal[Calendar.DAY_OF_MONTH] = day
        return cal.time
    }
}
