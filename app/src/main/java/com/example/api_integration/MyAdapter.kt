package com.example.api_integration

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(val context: Activity, val dataList: List<Data>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // Single MediaPlayer instance for the adapter
    private var mediaPlayer: MediaPlayer? = null
    private var currentlyPlayingPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.each_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = dataList[position]

        // Populate the data into the view
        holder.title.text = currentData.title
        Picasso.get().load(currentData.album.cover).into(holder.image)

        // Set the visibility of play/pause buttons based on whether this item is currently playing
        if (position == currentlyPlayingPosition) {
            holder.play.visibility = View.INVISIBLE
            holder.pause.visibility = View.VISIBLE
        } else {
            holder.play.visibility = View.VISIBLE
            holder.pause.visibility = View.INVISIBLE
        }

        // Handle play button click
        holder.play.setOnClickListener {
            // Stop any currently playing media
            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                mediaPlayer!!.stop()
                mediaPlayer!!.release()
                mediaPlayer = null

                // Update the UI of the previous playing item
                if (currentlyPlayingPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(currentlyPlayingPosition)
                }
            }

            // Start playing the new media
            mediaPlayer = MediaPlayer.create(context, currentData.preview.toUri())
            mediaPlayer!!.start()

            // Update the current position and UI
            currentlyPlayingPosition = position
            notifyItemChanged(position)

            // Set up the visibility of play/pause buttons
            holder.play.visibility = View.INVISIBLE
            holder.pause.visibility = View.VISIBLE
        }

        // Handle pause button click
        holder.pause.setOnClickListener {
            mediaPlayer?.pause()

            // Update the visibility of play/pause buttons
            holder.pause.visibility = View.INVISIBLE
            holder.play.visibility = View.VISIBLE
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.musicImage)
        val title: TextView = itemView.findViewById(R.id.musicTitle)
        val play: ImageButton = itemView.findViewById(R.id.btnPlay)
        val pause: ImageButton = itemView.findViewById(R.id.btnPause)
    }
}
