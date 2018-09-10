package nz.co.paperkite.pkandroidplaybook.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

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

