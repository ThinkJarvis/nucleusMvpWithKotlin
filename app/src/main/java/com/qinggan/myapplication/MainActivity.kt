package com.app.mykotlin


import android.os.Bundle
import android.util.Log
import com.qinggan.myapplication.MainPresenter
import com.qinggan.myapplication.R
import com.qinggan.myapplication.nucleusMvp.RequiresPresenter
import com.qinggan.myapplication.nucleusMvp.view.NucleusActivity
import kotlinx.android.synthetic.main.activity_main.*


@RequiresPresenter(MainPresenter::class)
open class MainActivity : NucleusActivity<MainPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("wjq", "MainActivity onCreate")
    }


    open fun setTextValue() {
        message.setText("Hello Kotlin")
    }
}
