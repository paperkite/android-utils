package nz.co.paperkite.androidutils

import io.reactivex.Single

inline fun asSingle(crossinline task: () -> Unit): Single<Unit> = Single.create {
	try {
		task()
		it.onSuccess(Unit)
	} catch(e: Exception) {
		it.onError(e)
	}
}