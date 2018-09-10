package nz.co.paperkite.delegates

import kotlin.reflect.KProperty

/**
 * Created by nate on 23/03/18.
 */
class LazyVar<T>(private val initilizer: () -> T) {
	private var _field: T? = null
	
	operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
		if (_field == null) {
			_field = initilizer()
		}
		
		return _field!!
	}
	
	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		_field = value
	}
}