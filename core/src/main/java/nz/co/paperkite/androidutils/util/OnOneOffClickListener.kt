package nz.co.paperkite.androidutils.util

import android.os.Handler
import android.os.SystemClock
import android.view.View


/**
 * Created by katehenderson on 10/07/18.
 */
abstract class OnOneOffClickListener(private val delay: Long = MIN_CLICK_INTERVAL) : View.OnClickListener {
	
	private var mLastClickTime: Long = 0
	
	abstract fun onSingleClick(v: View)
	
	override fun onClick(v: View) {
		val currentClickTime = SystemClock.uptimeMillis()
		val elapsedTime = currentClickTime - mLastClickTime
		
		
		if (elapsedTime <= delay)
			return
		if (!isViewClicked) {
			isViewClicked = true
			mLastClickTime = currentClickTime
			startTimer()
		} else {
			return
		}
		onSingleClick(v)
	}
	
	/**
	 * This method delays simultaneous touch events of multiple views.
	 */
	private fun startTimer() {
		val handler = Handler()
		
		handler.postDelayed({ isViewClicked = false }, delay)
		
	}
	
	companion object {
		const val MIN_CLICK_INTERVAL: Long = 600
		
		var isViewClicked = false
	}
	
}