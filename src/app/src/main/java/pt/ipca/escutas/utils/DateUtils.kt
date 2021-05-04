package pt.ipca.escutas.utils

import java.util.Calendar
import java.util.Date

/**
 * Contains helper methods related to the Date type.
 */
object DateUtils {

    /**
     * Converts the specified arguments to a Date type.
     *
     * @param year The year value.
     * @param month The month value.
     * @param day The day value.
     * @return The Date type.
     */
    fun DateValue(year: Int, month: Int, day: Int): Date {

        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = year
        cal[Calendar.MONTH] = month
        cal[Calendar.DAY_OF_MONTH] = day
        return cal.time
    }
}
