package com.maziyarbahramian.lastfm.ui.albumList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.maziyarbahramian.lastfm.R
import com.maziyarbahramian.lastfm.api.networkResponse.AlbumItem
import kotlinx.android.synthetic.main.list_item_album.view.*


class AlbumsRecyclerAdapter(
    val onItemSelected: (String, String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<AlbumItem>() {
        override fun areItemsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean {
            return oldItem.name.equals(newItem.name)
        }

        override fun areContentsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(
        AlbumRecyclerChangeCallback(this),
        AsyncDifferConfig.Builder(diffCallback).build()
    )

    fun submitList(list: List<AlbumItem?>) {
        differ.submitList(list)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item_album,
                    parent, false
                )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    internal inner class AlbumRecyclerChangeCallback(
        private val adapter: AlbumsRecyclerAdapter
    ) : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    inner class AlbumViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(album: AlbumItem) = with(itemView) {
            itemView.setOnClickListener {
                onItemSelected(album.name!!, album.artist?.name!!)
            }

            Glide.with(itemView.context)
                .load(album.image?.get(3)?.text)
                .into(itemView.album_img)

            itemView.album_name_tv.text = album.name
            itemView.album_artist_name_tv.text = album.artist?.name

        }
    }

}