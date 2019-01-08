package nz.co.paperkite.androidutils.app

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import nz.co.paperkite.androidutils.arch.BaseViewModel
import nz.co.paperkite.androidutils.arch.map

class MainViewModel(private val initialCount: Int): BaseViewModel<Int>(initialCount) {
	
	private val _liveCount = MutableLiveData<Int>().apply { value = initialCount }
	val liveCount = _liveCount map {
		Log.d("MainViewModel", "Count: $it")
		"Count: $it"
	}
	
	fun incrementButtonClicked() {
		val newCount = (_liveCount.value ?: initialCount) + 1
		Log.d("MainViewModel", "newCount: $newCount")
		_liveCount.postValue(newCount)
	}
	
}