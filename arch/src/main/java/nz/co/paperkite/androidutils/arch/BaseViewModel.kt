package nz.co.paperkite.androidutils.arch

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<D>(private val data: D) : ViewModel() {
	
	protected val disposables = CompositeDisposable()
	
	override fun onCleared() {
		super.onCleared()
		disposables.dispose()
	}
	
	/**
	 * Utility to allow e.g.
	 * disposables += DataManager.getSomething().subscribe(...)
	 */
	protected operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
		disposables.add(disposable)
	}
	
}