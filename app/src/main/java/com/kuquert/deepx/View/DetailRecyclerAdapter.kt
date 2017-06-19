package com.kuquert.deepx.View

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kuquert.deepx.Model.Repo
import com.kuquert.deepx.R


/**
 * Created by kuquert on 08/06/17.
 */
class DetailRecyclerAdapter(private val activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Repo>? = null

    fun setData(items: List<Repo>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_item_holder, parent, false)
        return ItemView(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder as ItemView
        itemView.titleTextView.text = items!![position].name
        itemView.updatedAtTextView.text = items!![position].updated_at.toString()
        itemView.createdAtTextView.text = items!![position].created_at.toString()
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class ItemView internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var titleTextView: TextView = view.findViewById(R.id.title_textView) as TextView
        internal var updatedAtTextView: TextView = view.findViewById(R.id.updatedAt_textView) as TextView
        internal var createdAtTextView: TextView = view.findViewById(R.id.createdAt_textView) as TextView
    }
}