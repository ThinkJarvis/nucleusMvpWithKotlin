package com.qinggan.myapplication

import android.os.Bundle
import android.util.Log
import com.app.mykotlin.MainActivity
import com.qinggan.myapplication.nucleusMvp.Presenter

open class MainPresenter : Presenter<MainActivity>() {

    override fun create(bundle: Bundle?) {
        super.create(bundle)
        Log.e("wjq", "MainPresenter onCreate")
    }

    override fun takeView(view: MainActivity) {
        super.takeView(view)
        Log.e("wjq", "MainPresenter takeView")
    }


    override fun dropView() {
        super.dropView()
        Log.e("wjq", "MainPresenter dropView")
    }


    override fun destroy() {
        super.destroy()
        Log.e("wjq", "MainPresenter destroy")
    }

}