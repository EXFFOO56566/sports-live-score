package com.aroniez.futaa.ui.fixture.commentary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.commentaries.Commentary
import kotlinx.android.synthetic.main.commentary_row_item.view.*

class CommentariesAdapter(private val commentaries: ArrayList<Commentary>, val context: Context) : RecyclerView.Adapter<CommentariesAdapter.LiveGameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): LiveGameViewHolder {
        return LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.commentary_row_item, parent, false))
    }

    override fun getItemCount() = commentaries.size

    override fun onBindViewHolder(holder: LiveGameViewHolder, position: Int) = holder.bindData(position)

    inner class LiveGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(position: Int) {
            val commentary = commentaries[position]
            itemView.commentary.text = commentary.comment
            itemView.commentaryTime.text = commentary.minute.toString() + "\""
            if (commentary.goal || commentary.important) {
                itemView.commentary.typeface = ResourcesCompat.getFont(context, R.font.font_bold)
                itemView.commentaryIcon.visibility = View.VISIBLE
            } else {
                itemView.commentary.typeface = ResourcesCompat.getFont(context, R.font.font_regular)
                itemView.commentaryIcon.visibility = View.GONE
            }
        }
    }
}