package com.kuquert.deepx.Controller

import com.kuquert.deepx.Model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by kuquert on 08/06/17.
 */
interface GitHubEndPoint {

    @GET("/users/{user}/repos?client_id=cf6fc09d97d5383a9a72&client_secret=bcafb4ff0dee03763d28c66974cf8fd336ad0fb9")
    fun repos(@Path("user") id: String): Call<List<Repo>>

    @GET("/repos/{user}/{repo}/languages?client_id=cf6fc09d97d5383a9a72&client_secret=bcafb4ff0dee03763d28c66974cf8fd336ad0fb9")
    fun languages(@Path("user") id: String, @Path("repo") repo: String): Call<Map<String, Int>>
}