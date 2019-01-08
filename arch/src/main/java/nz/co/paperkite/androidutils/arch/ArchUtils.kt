package nz.co.paperkite.androidutils.arch

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations

/**
 * This function creates a [LiveData] of a [Pair] of the two types provided. The resulting LiveData is updated whenever either input LiveData updates and all LiveData have updated at least once before.
 *
 * The returned [LiveData] emits values on every internal [LiveData] update only upon and after each internal [LiveData] has been updated once.
 * e.g. Pattern 'AABAA' would emit three times. Upon 'B' and then the proceeding 'A'. Each subsequent a/b update will trigger another emission of [Pair].
 *
 * @param a the first LiveData
 * @param b the second LiveData
 * @param c the third LiveData
 */
fun <A, B> zipLiveData(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> = object : LiveData<Pair<A, B>>() {
	override fun observe(owner: LifecycleOwner, observer: Observer<Pair<A, B>>) {
		super.observe(owner, observer)
		
		var lastA: A? = null
		var lastB: B? = null
		
		fun update() {
			val localLastA = lastA
			val localLastB = lastB
			if (localLastA != null && localLastB != null) {
				observer.onChanged(Pair(localLastA, localLastB))
			}
		}
		
		a.observe(owner, Observer { lastA = it; update() })
		b.observe(owner, Observer { lastB = it; update() })
	}
}

/**
 * This function creates a [LiveData] of a [Triple] of the three types provided. The resulting LiveData is updated whenever either input LiveData updates and all LiveData have updated at least once before.
 *
 * The returned [LiveData] emits values on every internal [LiveData] update only upon and after each internal [LiveData] has been updated once.
 * e.g. Pattern 'AABACA' would emit twice. Upon 'C' and then the proceeding 'A'. Each subsequent a/b/c update will trigger another emission of [Triple].
 *
 * @param a the first LiveData
 * @param b the second LiveData
 * @param c the third LiveData
 */
fun <A, B, C> zipLiveData(a: LiveData<A>, b: LiveData<B>, c: LiveData<C>): LiveData<Triple<A, B, C>> = object : LiveData<Triple<A, B, C>>() {
	override fun observe(owner: LifecycleOwner, observer: Observer<Triple<A, B, C>>) {
		super.observe(owner, observer)
		
		var lastA: A? = null
		var lastB: B? = null
		var lastC: C? = null
		
		fun update() {
			val localLastA = lastA
			val localLastB = lastB
			val localLastC = lastC
			if (localLastA != null && localLastB != null && localLastC != null) {
				observer.onChanged(Triple(localLastA, localLastB, localLastC))
			}
		}
		
		a.observe(owner, Observer { lastA = it; update() })
		b.observe(owner, Observer { lastB = it; update() })
		c.observe(owner, Observer { lastC = it; update() })
	}
}

/**
 * Helper infix fun that calls [zipLiveData]
 */
infix fun <A, B> LiveData<A>.zip(b: LiveData<B>): LiveData<Pair<A, B>> = zipLiveData(this, b)

/**
 * Three [LiveData] version of [zip]
 */
fun <A, B, C> zip(a: LiveData<A>, b: LiveData<B>, c: LiveData<C>): LiveData<Triple<A, B, C>> = zipLiveData(a, b, c)

infix fun <T, Y> LiveData<T>.map(transformation: (T) -> Y): LiveData<Y> = Transformations.map(this, transformation)