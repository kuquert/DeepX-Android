package com.kuquert.deepx.View

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kuquert.deepx.Model.Repo
import com.kuquert.deepx.R
import java.util.*


/**
 * Created by kuquert on 08/06/17.
 */
class FirstRecyclerAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemSelectedListener {
        fun onItemSelected(repos: List<Repo>)
    }

//    private var items: ArrayList<String>? = null
    var onItemSelectedListener: OnItemSelectedListener? = null
    private var mKeysList: Array<String>? = null
    var dataSource = HashMap<String, Pair<Int, List<Repo>>>()


    fun setData(items: HashMap<String, Pair<Int, List<Repo>>>) {
        dataSource = items

        mKeysList = dataSource.keys.toTypedArray()

        Arrays.sort(mKeysList, { first, sec ->
            val a = dataSource[first]
            val b = dataSource[sec]

            b?.first?.compareTo(a?.first!!)!!
        })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_item_holder, parent, false)
        return ItemView(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemView = holder as ItemView
        val key = mKeysList?.get(position)
        var linesRepos = dataSource[key]
        itemView.title.text = key

        if (linesRepos != null) {

            val linesCount = linesRepos.first
            val repos = linesRepos.second

            itemView.title.text = key
            itemView.subtitle.text = "Repos: " + repos.count().toString()
            itemView.detail.text = "Linhas: " + linesCount.toString()
        }
    }

    override fun getItemCount(): Int {
        return mKeysList?.size ?: 0
    }

    private inner class ItemView internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var title: TextView = view.findViewById(R.id.title) as TextView
        internal var subtitle: TextView = view.findViewById(R.id.subtitle) as TextView
        internal var detail: TextView = view.findViewById(R.id.detail) as TextView

        init {
            view.setOnClickListener {
                val key = mKeysList?.get(adapterPosition)
                val repos = dataSource[key]?.second

                if (repos != null) {
                    onItemSelectedListener?.onItemSelected(repos)
                }
            }
        }
    }

}