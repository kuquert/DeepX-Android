package com.kuquert.deepx.View

class MainActivity : android.support.v7.app.AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kuquert.deepx.R.layout.activity_main)

        supportFragmentManager
                .beginTransaction()
                .replace(com.kuquert.deepx.R.id.frame_layout, com.kuquert.deepx.View.FirstFragment(), com.kuquert.deepx.View.FirstFragment::class.java!!.getSimpleName())
                .commit()
    }

}