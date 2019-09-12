package com.qinggan.myapplication.nucleusMvp.view

import android.app.Activity
import android.os.Bundle
import com.qinggan.myapplication.nucleusMvp.*

abstract class NucleusActivity<P : Presenter<View>, View> : Activity(), ViewWithPresenter<P, View> {
    companion object {
        private const val PARENT_STATE_KEY = "parent_state"
        private const val PRESENTER_STATE_KEY = "presenter_state"
    }


    private var presenterDelegate =
        PresenterLifecycleDelegate<P, View>(
            ReflectionPresenterFactory.fromViewClass(
                this.javaClass
            )
        )


    override fun getPresenterFactory(): PresenterFactory<P, View>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return presenterDelegate.getPresenterFactory()
    }

    override fun setPresenterFactory(presenterFactory: PresenterFactory<P, View>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        presenterDelegate.setPresenterFactory(presenterFactory)
    }

    override fun getPresenter(): P? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return presenterDelegate.getPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterDelegate?.onRestoreInstanceState(savedInstanceState?.getBundle(PRESENTER_STATE_KEY))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState())
    }

    override fun onResume() {
        super.onResume()
        presenterDelegate.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        presenterDelegate.onDropView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterDelegate.onDestroy(!isChangingConfigurations)
    }
}