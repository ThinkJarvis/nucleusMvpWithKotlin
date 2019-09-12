package com.qinggan.myapplication

open class TestA{
    var mTest = Test<Any>()


    open fun onResume(view: Any) {
        mTest.takeView()
    }


}