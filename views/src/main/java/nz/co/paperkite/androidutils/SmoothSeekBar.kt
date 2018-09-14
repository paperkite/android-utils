package nz.co.paperkite.androidutils

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import nz.co.paperkite.androidutils.SmoothSeekBar.Companion.MAX_VALUE

/**
 * A simple extension to [SeekBar] which allows you to have a small amount of anchor points but allowing
 * for smooth seeking by actually having [MAX_VALUE] points for the underlying [SeekBar].
 *
 * The thumb will return back to the closest anchor point with a simple animation.
 *
 * Note: use [setOnSmoothSeekBarChangeListener] instead of [setOnSeekBarChangeListener].
 */
class SmoothSeekBar : SeekBar {
	
	private var seekBarListener: OnSmoothSeekBarChangeListener? = null
	private lateinit var anchorPoints: List<Int>
	private var currentAnimator: ValueAnimator? = null
	private var lastBroadcastValue = 0
	
	private val numDivisions: Int
		get() = anchorPoints.size - 1
	private val anchorDelta: Int
		get() = max / numDivisions
	
	constructor(context: Context?) : super(context)
	constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
	constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
	
	init {
		super.setMax(MAX_VALUE)
		super.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
			override fun onStartTrackingTouch(seekBar: SeekBar?) {
				stopResetToAnchorAnimation()
				seekBarListener?.onStartTrackingTouch(this@SmoothSeekBar)
			}
			
			override fun onStopTrackingTouch(seekBar: SeekBar?) {
				resetToAnchorPoint()
			}
			
			override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
				seekBarListener?.onProgressChanged(this@SmoothSeekBar, closestPointForCurrentProgress(), fromUser)
			}
		})
	}
	
	override fun setMax(max: Int) {
		val anchorDelta = this.max / max
		anchorPoints = emptyList()
		
		(0 until max).forEach {
			anchorPoints = anchorPoints.plus(it * anchorDelta)
		}
		
		anchorPoints = anchorPoints.plus(MAX_VALUE)
	}
	
	override fun setProgress(progress: Int) {
		super.setProgress(progress * anchorDelta)
		lastBroadcastValue = progress
	}
	
	fun setOnSmoothSeekBarChangeListener(l: OnSmoothSeekBarChangeListener?) {
		seekBarListener = l
		l?.onProgressChanged(this, closestPointForCurrentProgress(), false)
	}
	
	private fun stopResetToAnchorAnimation() {
		currentAnimator?.removeAllUpdateListeners()
		currentAnimator = null
	}
	
	private fun resetToAnchorPoint() {
		val closestAnchor = closestPointForCurrentProgress()
		currentAnimator?.removeAllUpdateListeners()
		with(ValueAnimator.ofInt(progress, anchorPoints[closestAnchor])) {
			currentAnimator = this
			duration = ANCHOR_RETURN_ANIMATION_TIME
			addUpdateListener {
				super.setProgress(it.animatedValue as Int)
			}
			addListener(object : Animator.AnimatorListener {
				override fun onAnimationRepeat(animation: Animator?) = Unit
				override fun onAnimationCancel(animation: Animator?) = Unit
				override fun onAnimationStart(animation: Animator?) = Unit
				override fun onAnimationEnd(animation: Animator?) {
					removeListener(this)
					if (closestAnchor != lastBroadcastValue) {
						lastBroadcastValue = closestAnchor
						seekBarListener?.onStopTrackingTouch(this@SmoothSeekBar, closestAnchor)
					}
				}
			})
			start()
		}
	}
	
	private fun closestPointForCurrentProgress(): Int {
		var currentBestDx: Int = Int.MAX_VALUE
		var currentBestAnchorIndex = 0
		anchorPoints.forEachIndexed { index, anchor ->
			
			val dx = Math.abs(anchor - progress)
			if (dx < currentBestDx) {
				currentBestAnchorIndex = index
				currentBestDx = dx
			}
			
		}
		
		return currentBestAnchorIndex
	}
	
	interface OnSmoothSeekBarChangeListener {
		fun onStartTrackingTouch(seekBar: SmoothSeekBar)
		fun onStopTrackingTouch(seekBar: SmoothSeekBar, progress: Int)
		fun onProgressChanged(seekBar: SmoothSeekBar, progress: Int, fromUser: Boolean)
	}
	
	companion object {
		private const val MAX_VALUE = 100
		private const val ANCHOR_RETURN_ANIMATION_TIME = 200L
	}
	
}