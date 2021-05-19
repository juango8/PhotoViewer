package com.juango.photoviewer.ui.photolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juango.photoviewer.databinding.ListItemPhotoBinding
import com.juango.photoviewer.model.Photo
import com.juango.photoviewer.utils.setImageByGlide

class PhotoListAdapter(
    private val onItemSelected: (Photo) -> Unit
) :
    RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var photo: Photo

        fun bind(photo: Photo, onItemSelected: (Photo) -> Unit) {
            this.photo = photo
            binding.title.text = photo.title
            binding.photoImage.setImageByGlide(photo.thumbnailUrl)
            binding.root.setOnClickListener { onItemSelected(photo) }
        }

    }

    private val photos: MutableList<Photo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemPhotoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position], onItemSelected)
    }

    override fun getItemCount() = photos.size

    fun setData(data: List<Photo>) {
        this.photos.clear()
        this.photos.addAll(data)
        notifyDataSetChanged()
    }
}