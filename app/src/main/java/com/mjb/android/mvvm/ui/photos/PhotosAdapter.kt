package com.mjb.android.mvvm.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mjb.android.mvvm.R
import com.mjb.android.mvvm.binding.recyclerview.DataBoundListAdapter
import com.mjb.android.mvvm.database.Photo
import com.mjb.android.mvvm.databinding.ItemPhotoBinding
import com.mjb.android.mvvm.util.AppExecutors

/**
 * Created by Mustahsan on 05-Aug-17.
 */

class PhotosAdapter(
    appExecutors: AppExecutors,
    private val repoClickCallback: ((Photo) -> Unit)?
) : DataBoundListAdapter<Photo, ItemPhotoBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.thumbnailUrl == newItem.thumbnailUrl
                    && oldItem.albumId == newItem.albumId
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemPhotoBinding {
        val binding =
            DataBindingUtil.inflate<ItemPhotoBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_photo,
                parent,
                false
            )
        return binding
    }

    override fun bind(binding: ItemPhotoBinding, item: Photo, position: Int) {
        binding.root.setOnClickListener {
            binding.photo?.let { photo ->
                repoClickCallback?.invoke(photo)
            }
        }
        binding.photo = item
    }
}

