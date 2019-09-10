package com.mjb.android.mvvm.ui.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mjb.android.mvvm.R
import com.mjb.android.mvvm.database.Photo
import com.mjb.android.mvvm.util.AppExecutors
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumsAdapter(
    private val appExecutors: AppExecutors,
    private val albums: Map<Int, List<Photo>>
) :
    RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    private var keysArray: IntArray = albums.keys.toIntArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = albums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photos = albums[keysArray[position]]
        holder.itemView.albumNameTv.text = "Album ${photos?.get(0)?.albumId ?: 0}"
        photoRecyclerView(holder,photos)
    }

    private fun photoRecyclerView(holder: ViewHolder, photos: List<Photo>?){
        val photosAdapter = PhotosAdapter(appExecutors, 1) {}
        holder.itemView.photosRecyclerView.adapter = photosAdapter
        photosAdapter.submitList(photos)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

