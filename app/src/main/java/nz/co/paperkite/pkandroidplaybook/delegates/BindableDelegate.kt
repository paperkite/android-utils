package nz.co.paperkite.pkandroidplaybook.delegates

import android.databinding.BaseObservable
import kotlin.reflect.KProperty

/**
 * Created by nate on 24/11/17.
 *
 * Will call BaseObservable#notifyPropertyChanged(int) when the var is changed
 *
 * can only be used from a class that extends BaseObservable
 *
 * @see BaseObservable#notifyPropertyChanged(int)
 */
class BindableDelegate<T>(default: T, private vararg val fieldId: Int) {
	private var field: T = default
	operator fun getValue(thisRef: BaseObservable?, property: KProperty<*>): T = field
	
	operator fun setValue(thisRef: BaseObservable?, property: KProperty<*>, value: T) {
		field = value
		fieldId.forEach {
			thisRef?.notifyPropertyChanged(it)
		}
	}
}