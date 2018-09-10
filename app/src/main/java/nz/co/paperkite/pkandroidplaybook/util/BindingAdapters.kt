package nz.co.paperkite.pkandroidplaybook.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide


object BindingAdapters {
	
	@JvmStatic
	@BindingAdapter("loadUrl")
	fun loadImageUrl(view: ImageView, url: String) {
		Glide.with(view).load(url).into(view)
	}
	
}