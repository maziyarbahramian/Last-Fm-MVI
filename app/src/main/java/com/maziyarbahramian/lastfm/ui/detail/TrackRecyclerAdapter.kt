package com.maziyarbahramian.lastfm.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.maziyarbahramian.lastfm.R
import com.maziyarbahramian.lastfm.api.networkResponse.TrackItem
import kotlinx.android.synthetic.main.list_item_track.view.*
import java.util.concurrent.TimeUnit

class TrackRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<TrackItem>() {
        override fun areItemsTheSame(oldItem: TrackItem, newItem: TrackItem): Boolean {
            return oldItem.name.equals(newItem.name)
        }

        override fun areContentsTheSame(oldItem: TrackItem, newItem: TrackItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(
        TrackRecyclerChangeCallback(this),
        AsyncDifferConfig.Builder(diffCallback).build()
    )

    fun submitList(list: List<TrackItem?>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item_track,
                    parent, false
                )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TrackViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }


    internal inner class TrackRecyclerChangeCallback(
        private val adapter: TrackRecyclerAdapter
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


    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(track: TrackItem) = with(itemView) {
            itemView.track_name.text = track.name
            itemView.track_time.text = track.duration
        }
    }
}