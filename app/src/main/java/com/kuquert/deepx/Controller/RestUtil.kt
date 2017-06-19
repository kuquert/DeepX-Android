package com.kuquert.deepx.Controller

import com.google.gson.GsonBuilder
import com.kuquert.deepx.Model.Language
import com.kuquert.deepx.Model.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



/**
 * Created by kuquert on 08/06/17.
 */


class RestUtil {

    var gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

    val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    private val githubEndPoint: GitHubEndPoint

    init {

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        githubEndPoint = retrofit.create(GitHubEndPoint::class.java)
    }

    fun getRepos(user: String): Call<List<Repo>> {
        return githubEndPoint.repos(user)
    }

    interface RestUtilCallback<in T> {
        fun onSucces(value: T)
        fun onFailure(t: Throwable?)
    }

    fun getLanguageForRepo(repo: Repo, callback: RestUtilCallback<List<Language>?>) {

        val call = githubEndPoint.languages((repo.owner?.login ?: ""), (repo.name ?: ""))

        call.enqueue(object : Callback<Map<String, Int>> {
            override fun onResponse(call: Call<Map<String, Int>>?, response: Response<Map<String, Int>>?) {

                val lgs = mutableListOf<Language>()
                response?.body()?.forEach {
                    var l = Language()
                    l.id = "fuck"
                    l.name = it.key
                    l.linesCount = it.value
                    l.ownerID = repo.owner?.id
                    l.repoID = repo.id
                    lgs?.add(l)
                }
                callback.onSucces(lgs?.toList())
            }

            override fun onFailure(call: Call<Map<String, Int>>?, t: Throwable?) {
                callback.onFailure(t)
            }
        })

//        return githubEndPoint.languages((repo.name ?: ""), (repo.owner?.login ?: ""))
    }

    // TODO("agregateLanguages")

    fun agragateLanguagesForUser(user: String, callback: RestUtilCallback<HashMap<String, Pair<Int, List<Repo>>>?>) {
        getLanguagesForUser(user, object : RestUtilCallback<List<Repo>?> {
            override fun onSucces(value: List<Repo>?) {
                var a = HashMap<String, Pair<Int, List<Repo>>>()

                if (value != null) {
                    for (repo in value) {
                        for (l in repo.languages!!) {
                            val value = l.linesCount + (a[l.name]?.first ?: 0)
                            val rs = mutableListOf<Repo>()
                            rs.add(repo)
                            a[l.name]?.second?.let { rs.addAll(it) }
                            a.put(l.name!!, Pair(value, rs))
                        }
                    }
                }

                callback.onSucces(a)
            }

            override fun onFailure(t: Throwable?) {
                callback.onFailure(t)
            }

        })
    }

    fun getLanguagesForUser(user: String, callback: RestUtilCallback<List<Repo>>) {
        var count = 0
        getRepos(user).enqueue(object : Callback<List<Repo>> {

            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                val repos = response?.body()

                for (repo in repos!!) {
                    getLanguageForRepo(repo, object : RestUtilCallback<List<Language>?> {
                        override fun onSucces(value: List<Language>?) {
                            count++
                            repo.languages = value
                            if (count == response.body()?.count()) {
                                //TODO maybe it need a copyied List
                                callback.onSucces(repos)
                            }
                        }

                        override fun onFailure(t: Throwable?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                    })
                }
            }

            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                callback.onFailure(t)
            }
        })
    }
}