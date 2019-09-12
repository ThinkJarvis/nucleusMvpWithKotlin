package com.qinggan.myapplication.nucleusMvp.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.qinggan.myapplication.nucleusMvp.*

open class NucleusConstraintLayout<P : Presenter<Any>>
constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr),
    ViewWithPresenter<P> {

    companion object {
        private val PARENT_STATE_KEY = "parent_state"
        private val PRESENTER_STATE_KEY = "presenter_state"
    }


    private var presenterDelegate =
        PresenterLifecycleDelegate<P>(
            ReflectionPresenterFactory.fromViewClass(
                this.javaClass
            )
        )

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, -1)

    constructor(context: Context) : this(context, null, -1)

    override fun getPresenterFactory(): PresenterFactory<P>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return presenterDelegate.getPresenterFactory()
    }

    override fun setPresenterFactory(presenterFactory: PresenterFactory<P>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        presenterDelegate.setPresenterFactory(presenterFactory)
    }

    override fun getPresenter(): P? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return presenterDelegate.getPresenter()
    }


    fun getActivity(): Activity {
        var context = context
        while (context !is Activity && context is ContextWrapper)
            context = context.baseContext
        if (context !is Activity)
            throw IllegalStateException("Expected an activity context, got " + context.javaClass.simpleName)
        return context
    }

    override fun onSaveInstanceState(): Parcelable? {
        var bundle = Bundle()
        bundle.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState())
        bundle.putParcelable(PARENT_STATE_KEY, super.onSaveInstanceState())
        return super.onSaveInstanceState()
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val bundle = state as Bundle
        super.onRestoreInstanceState(bundle.getParcelable(PARENT_STATE_KEY))
        presenterDelegate.onRestoreInstanceState(bundle.getBundle(PRESENTER_STATE_KEY))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!isInEditMode)
            presenterDelegate.onResume(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenterDelegate.onDropView()
        presenterDelegate.onDestroy(true)
    }

}