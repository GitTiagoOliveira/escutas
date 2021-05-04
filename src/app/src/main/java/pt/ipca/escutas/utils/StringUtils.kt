package pt.ipca.escutas.utils

import java.util.regex.Pattern

/**
 * Contains helper methods related to the String type.
 */
object StringUtils {

    /**
     * Validates if the string is a valid email.
     *
     * @return Boolean whether the email is valid or not.
     */
    fun String.isEmailValid(): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(this)
        return matcher.matches()
    }
}
