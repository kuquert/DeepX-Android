package com.kuquert.deepx.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kuquert.deepx.Controller.RestUtil
import com.kuquert.deepx.Model.Repo
import com.kuquert.deepx.R
import java.io.Serializable
import java.util.*

/**
 * Created by kuquert on 08/06/17.
 */
class FirstFragment : Fragment(), FirstRecyclerAdapter.OnItemSelectedListener {


    private var mListAdapter: FirstRecyclerAdapter? = null
    private var mKeysList: Set<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerView = view?.findViewById(R.id.recycler_view) as RecyclerView
        mListAdapter = FirstRecyclerAdapter()
        mListAdapter?.onItemSelectedListener = this
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mListAdapter
        recyclerView.visibility = View.VISIBLE

        val items = ArrayList<String>()

        RestUtil().agragateLanguagesForUser("Apple", object : RestUtil.RestUtilCallback<HashMap<String, Pair<Int, List<Repo>>>?> {
            override fun onSucces(value: HashMap<String, Pair<Int, List<Repo>>>?) {
                mListAdapter?.setData(value!!)
            }

            override fun onFailure(t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun onItemSelected(repos: List<Repo>) {

        activity.intent.putExtra("Repos", repos as Serializable)

        val fragment = DetailFragment()

        val ft = activity.supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, fragment)
        ft.commit()
    }

}