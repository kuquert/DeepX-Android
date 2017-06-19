package com.kuquert.deepx.Model

import java.util.*

/**
 * Created by kuquert on 08/06/17.
 */
class Repo : java.io.Serializable {
    val id: String? = null
    val name: String? = null
    val owner: Owner? = null
    val created_at: Date? = null
    val updated_at: Date? = null
    var languages: List<Language>? = null
}
