package nz.co.paperkite.androidutils.util

/**
 * Created by nate on 15/03/18.
 */
sealed class Either<L, R> {
	class Left<L, R>(private val left: L) : Either<L, R>() {
		operator fun invoke(): L = left
	}
	
	class Right<L, R>(private val right: R) : Either<L, R>() {
		operator fun invoke(): R = right
	}
}