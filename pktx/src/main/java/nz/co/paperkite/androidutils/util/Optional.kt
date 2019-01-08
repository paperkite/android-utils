package nz.co.paperkite.androidutils.util

/**
 * Created by nate on 14/03/18.
 *
 * java.util.Optional isn't available < 24
 */
class  Optional<T: Any> private constructor(private var value: T? = null){
	
	fun get(): T = value!!
	
	fun isPresent() = value != null
	
	fun ifPresent(consumer: (T) -> Unit) {
		if(isPresent()) consumer(value!!)
	}
	
	fun orElse(other: T) = if(isPresent()) value!! else other
	
	fun orElse(other: () -> T) = if(isPresent()) value!! else other()
	
	companion object {
		private val EMPTY = Optional<Any>()
		
		@Suppress("UNCHECKED_CAST")
		fun <T: Any> empty(): Optional<T> = EMPTY as Optional<T>
		
		fun <T: Any> of(value: T) = Optional(value)
		
		fun <T: Any> ofNullable(value: T?) = if(value != null) of(value) else empty()
	}
}