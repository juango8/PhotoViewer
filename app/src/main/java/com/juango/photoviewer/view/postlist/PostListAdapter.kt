package com.juango.photoviewer.view.postlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juango.photoviewer.databinding.ListItemPostBinding
import com.juango.photoviewer.service.model.Post

class PostListAdapter(
    private val onItemSelected: () -> Unit
) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var post: Post

        @SuppressLint("SetTextI18n")
        fun bind(post: Post, onItemSelected: () -> Unit) {
            this.post = post
            binding.tvTitle.text = post.title
            binding.tvBody.text = "${post.body.substring(0, 60)}..."
            binding.root.setOnClickListener { onItemSelected() }

        }

    }

    private val posts: MutableList<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemPostBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position], onItemSelected)
    }

    override fun getItemCount() = posts.size

    fun setData(data: List<Post>) {
        this.posts.clear()
        this.posts.addAll(data)
        notifyDataSetChanged()
    }

}
