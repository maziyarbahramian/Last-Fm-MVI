package com.maziyarbahramian.lastfm.ui.launcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.maziyarbahramian.lastfm.R
import com.maziyarbahramian.lastfm.api.networkResponse.ArtistItem
import kotlinx.android.synthetic.main.list_item_artist.view.*

class LauncherRecyclerAdapter(
    val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ArtistItem>() {
        override fun areItemsTheSame(oldItem: ArtistItem, newItem: ArtistItem): Boolean {
            return oldItem.name.equals(newItem.name)
        }

        override fun areContentsTheSame(oldItem: ArtistItem, newItem: ArtistItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(
        ArtistRecyclerChangeCallback(this),
        AsyncDifferConfig.Builder(diffCallback).build()
    )

    fun submitList(list: List<ArtistItem?>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArtistViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item_artist,
                    parent, false
                )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArtistViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    internal inner class ArtistRecyclerChangeCallback(
        private val adapter: LauncherRecyclerAdapter
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

    inner class ArtistViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(artist: ArtistItem) = with(itemView) {
            itemView.setOnClickListener {
                artist.name?.let {
                    onItemSelected(artist.name)
                }
            }

            Glide.with(itemView.context)
                .load(artist.image?.get(3)?.text)
                .into(itemView.artist_img)

            itemView.artist_name_tv.text = artist.name
            itemView.artist_listener_tv.text = artist.listeners
        }

    }
}
