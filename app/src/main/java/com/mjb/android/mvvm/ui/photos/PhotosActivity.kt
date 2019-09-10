package com.mjb.android.mvvm.ui.photos

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mjb.android.mvvm.R
import com.mjb.android.mvvm.database.Photo
import com.mjb.android.mvvm.di.InjectableActivity
import com.mjb.android.mvvm.network.Status
import com.mjb.android.mvvm.util.AppExecutors
import com.mjb.android.mvvm.util.VerticalSpacingDecoration
import kotlinx.android.synthetic.main.activity_photos.*
import kotlinx.android.synthetic.main.content_photos.*
import javax.inject.Inject

class PhotosActivity : InjectableActivity() {

    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var photosViewModel: PhotosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        setSupportActionBar(toolbar)

        photosViewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(PhotosViewModel::class.java)
        fetchPhotos()
    }

    private fun fetchPhotos() {
        progressBar.visibility = VISIBLE
        photosViewModel.getPhotos().observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    Toast.makeText(this@PhotosActivity, it.message, Toast.LENGTH_SHORT).show()
                    progressBar.visibility = GONE
                }
                Status.SUCCESS -> {
                    it.data?.let { it1 -> prepareData(it1) }
                    progressBar.visibility = GONE
                }
            }
        })
    }


    private fun prepareData(data: List<Photo>) {
        val albums = data.groupBy { it.albumId }
        recyclerView.adapter = AlbumsAdapter(appExecutors, albums)
    }

}
