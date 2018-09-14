package nz.co.paperkite.androidutils.util

import android.text.format.DateUtils
import java.util.*


/**
 * @return true if the supplied date is tomorrow else false
 */
fun Date.isTomorrow(): Boolean = DateUtils.isToday(this.time - DateUtils.DAY_IN_MILLIS)

/**
 * @return true if the supplied date is the same day as [other]
 */
infix fun Date.isSameDayAs(other: Date?): Boolean {
	other ?: return false
	
	val cal1 = Calendar.getInstance()
	val cal2 = Calendar.getInstance()
	
	cal1.time = this
	cal2.time = other
	
	return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
			cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

/**
 * @return true if the supplied date is the same time as [other]
 */
infix fun Date.isSameTimeAs(other: Date?): Boolean {
	other ?: return false
	
	val cal1 = Calendar.getInstance()
	val cal2 = Calendar.getInstance()
	
	cal1.time = this
	cal2.time = other
	
	return cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY) &&
			cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE)
}