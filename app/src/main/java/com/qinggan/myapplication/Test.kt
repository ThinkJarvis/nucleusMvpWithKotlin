package com.qinggan.myapplication

open class Test<View> {

    var view: View? = null


    protected fun onTakeView(view: View) {

    }

    open fun takeView(view: View) {
        this.view = view
        onTakeView(view)
    }
}