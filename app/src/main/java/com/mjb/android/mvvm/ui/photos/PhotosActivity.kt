package com.mjb.android.mvvm.ui.photos

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mjb.android.mvvm.R
import com.mjb.android.mvvm.di.InjectableActivity
import com.mjb.android.mvvm.network.Status
import com.mjb.android.mvvm.util.AppExecutors

import kotlinx.android.synthetic.main.activity_photos.*
import kotlinx.android.synthetic.main.content_photos.*
import java.lang.Error
import javax.inject.Inject

class PhotosActivity : InjectableActivity() {

    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var photosViewModel: PhotosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        setSupportActionBar(toolbar)

        photosViewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(PhotosViewModel::class.java)

        photosAdapter = PhotosAdapter(appExecutors) {

        }
        recyclerView.adapter = photosAdapter

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
                    photosAdapter.submitList(it.data)
                    progressBar.visibility = GONE
                }
            }
        })
    }

}
