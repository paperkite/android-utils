package nz.co.paperkite.androidutils.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.TranslateAnimation

private const val SHAKE_DEFAULT_DELTA_NEGATIVE = -8f
private const val SHAKE_DEFAULT_DELTA_POSITIVE = 8f
private const val SHAKE_DEFAULT_DURATION = 30L
private const val SHAKE_HORIZONTAL = 1
private const val SHAKE_VERTICAL = 0
private const val SHAKE_DELAY = 0L
private const val SHAKE_REPETITIONS_DEFAULT = 5

fun View.shake(
		numberOfShakes: Int = SHAKE_REPETITIONS_DEFAULT,
		duration: Long = SHAKE_DEFAULT_DURATION,
		direction: Int = SHAKE_HORIZONTAL,
		deltaNegative: Float = SHAKE_DEFAULT_DELTA_NEGATIVE,
		deltaPositive: Float = SHAKE_DEFAULT_DELTA_POSITIVE,
		delay: Long = SHAKE_DELAY,
		listener: Animation.AnimationListener? = null
) {
	val anim = TranslateAnimation(
			if(direction == SHAKE_HORIZONTAL) deltaNegative else 0f,
			if(direction == SHAKE_HORIZONTAL) deltaPositive else 0f,
			if(direction == SHAKE_VERTICAL) deltaNegative else 0f,
			if(direction == SHAKE_VERTICAL) deltaPositive else 0f
	).apply {
		this.duration = duration
		this.repeatCount = numberOfShakes
		this.repeatMode = Animation.REVERSE
		listener?.let { this.setAnimationListener(it) }
	}
	
	if(delay == 0L) {
		startAnimation(anim)
	} else {
		(handler ?: Handler()).postDelayed({
			startAnimation(anim)
		}, delay)
	}
}

infix fun View.goneIf(shouldBeGone: Boolean) = apply {
	visibility = if(shouldBeGone) View.GONE else View.VISIBLE
}

infix fun View.invisibleIf(shouldBeInvisible: Boolean) = apply {
	visibility = if(shouldBeInvisible) View.INVISIBLE else View.VISIBLE
}

fun View.gone() = apply { visibility = View.GONE }

fun View.invisible() = apply { visibility = View.INVISIBLE }

fun View.visible() = apply { visibility = View.VISIBLE }

inline fun View.doOnceOnGlobalLayout(crossinline action: (View) -> Unit) {
	viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
		override fun onGlobalLayout() {
			viewTreeObserver.removeOnGlobalLayoutListener(this)
			action(this@doOnceOnGlobalLayout)
		}
	})
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun View.getAbsCenterX(): Float {
	val absPos: IntArray = intArrayOf(0, 0)
	this.getLocationOnScreen(absPos)
	return absPos[0] + this.width / 2f
}

fun View.getAbsCenterY(): Float {
	val absPos: IntArray = intArrayOf(0, 0)
	this.getLocationOnScreen(absPos)
	return absPos[1] + this.height / 2f
}

fun Drawable.toBitmap(): Bitmap {
	val bitmap: Bitmap?
	
	if (this is BitmapDrawable) {
		return this.bitmap
	}
	
	if (this.intrinsicWidth <= 0 || this.intrinsicHeight <= 0) {
		bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
	} else {
		bitmap = Bitmap.createBitmap(this.intrinsicWidth, this.intrinsicHeight, Bitmap.Config.ARGB_8888)
	}
	
	val canvas = Canvas(bitmap)
	this.setBounds(0, 0, canvas.width, canvas.height)
	this.draw(canvas)
	return bitmap
}

/**
 * Sets a click listener with the given delay, to prevent duplicate actions from fast taps
 */
fun View.setOneOffClickListener(delay: Long = OnOneOffClickListener.MIN_CLICK_INTERVAL, listener: (View) -> Unit) {
	setOnClickListener(object : OnOneOffClickListener(delay) {
		override fun onSingleClick(v: View) {
			listener(v)
		}
	})
}