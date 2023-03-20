package com.aroniez.futaa.ui.content.news

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.News
import com.aroniez.futaa.utils.DateTimeUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_single_item.view.*

class NewsAdapter(private val games: ArrayList<News>, private val context: Context) : RecyclerView.Adapter<NewsAdapter.LiveGameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): LiveGameViewHolder {
        return LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.news_single_item, parent, false))
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: LiveGameViewHolder, position: Int) = holder.bindData(position)

    inner class LiveGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(position: Int) {
            val game = games[position]
            itemView.newTitle.text = game.title
            itemView.newsTime.text = DateTimeUtil.getRelativeTime(game.timestamp!!.toLong())
            Picasso.get().load(game.cdn_image).into(itemView.newsImage)
            itemView.newsLayout.setOnClickListener {
                val intent = Intent(context, NewsDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("image", game.cdn_image)
                intent.putExtra("title", game.title)
                intent.putExtra("timestamp", game.timestamp)
                intent.putExtra("id", game.article_id)
                intent.putExtra("slug", game.slug)
                context.startActivity(intent)
            }
        }
    }
}