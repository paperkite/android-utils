package nz.co.paperkite.androidutils.app

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import nz.co.paperkite.androidutils.app.databinding.ActivityMainBinding
import nz.co.paperkite.androidutils.arch.createViewModel

class MainActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityMainBinding
	private lateinit var viewModel: MainViewModel
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel = createViewModel { MainViewModel(9) }
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
		binding.setLifecycleOwner(this)
		binding.viewModel = viewModel
	}
}
