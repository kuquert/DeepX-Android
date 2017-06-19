package com.kuquert.deepx.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuquert.deepx.Model.Repo
import com.kuquert.deepx.R

/**
 * Created by kuquert on 14/06/17.
 */
class DetailFragment: Fragment() {

    private var mListAdapter: DetailRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerView = view?.findViewById(R.id.recycler_view) as RecyclerView
        mListAdapter = DetailRecyclerAdapter(activity)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mListAdapter
        recyclerView.visibility = View.VISIBLE

        val repos = activity.intent.extras.getSerializable("Repos") as List<Repo>
        mListAdapter?.setData(repos)

    }
}