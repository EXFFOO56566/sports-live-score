package com.aroniez.futaa.ui.content.videos

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.video.VideoHighlight
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_single_item.view.*

class VideosAdapter(private val videos: ArrayList<VideoHighlight>, val context: Context) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(video: VideoHighlight) {
            itemView.videoTitle.text = video.title
            itemView.competitionName.text = video.competition!!.name
            itemView.videoLayout.setOnClickListener {
                val intent = Intent(context, VideoActivity::class.java)
                intent.putExtra("video", video)
                context.startActivity(intent)
            }
            Picasso.get().load(video.thumbnail).into(itemView.newsImage, object : Callback {
                override fun onSuccess() {
                    itemView.progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    itemView.progressBar.visibility = View.GONE
                }

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.video_single_item, parent, false))
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) = holder.bindData(videos[position])
}