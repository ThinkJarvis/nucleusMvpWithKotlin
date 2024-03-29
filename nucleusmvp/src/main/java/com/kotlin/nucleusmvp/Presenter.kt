package com.qinggan.myapplication.nucleusMvp

import android.os.Bundle
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Returns a current view attached to the presenter or null.
 *
 * View is normally available between
 * {@link Activity#onResume()} and {@link Activity#onPause()},
 * {@link Fragment#onResume()} and {@link Fragment#onPause()},
 * {@link android.view.View#onAttachedToWindow()} and {@link android.view.View#onDetachedFromWindow()}.
 *
 * Calls outside of these ranges will return null.
 * Notice here that {@link Activity#onActivityResult(int, int, Intent)} is called *before* {@link Activity#onResume()}
 * so you can't use this method as a callback.
 *
 * @return a current attached view.
 */
open class Presenter<View> {

    /**
     * Returns a current view attached to the presenter or null.
     *
     * View is normally available between
     * {@link Activity#onResume()} and {@link Activity#onPause()},
     * {@link Fragment#onResume()} and {@link Fragment#onPause()},
     * {@link android.view.View#onAttachedToWindow()} and {@link android.view.View#onDetachedFromWindow()}.
     *
     * Calls outside of these ranges will return null.
     * Notice here that {@link Activity#onActivityResult(int, int, Intent)} is called *before* {@link Activity#onResume()}
     * so you can't use this method as a callback.
     *
     * @return a current attached view.
     */
    var view: View? = null

    var onDestroyListeners = CopyOnWriteArrayList<OnDestroyListener>()

    /**
     * This method is called after presenter construction.
     *
     * This method is intended for overriding.
     *
     * @param savedState If the presenter is being re-instantiated after a process restart then this Bundle
     *                   contains the data it supplied in {@link #onSave}.
     */
    protected fun onCreate(savedState: Bundle?) {

    }

    /**
     * This method is being called when a user leaves view.
     *
     * This method is intended for overriding.
     */
    protected fun onDestory() {

    }

    /**
     * A returned state is the state that will be passed to {@link #onCreate} for a new presenter instance after a process restart.
     *
     * This method is intended for overriding.
     *
     * @param state a non-null bundle which should be used to put presenter's state into.
     */
    protected fun onSave(state: Bundle?) {

    }

    /**
     * This method is being called when a view gets attached to it.
     * Normally this happens during {@link Activity#onResume()}, {@link Fragment#onResume()}
     * and {@link android.view.View#onAttachedToWindow()}.
     *
     * This method is intended for overriding.
     *
     * @param view a view that should be taken
     */
    protected fun onTakeView(view: View) {

    }

    /**
     * This method is being called when a view gets detached from the presenter.
     * Normally this happens during {@link Activity#onPause()} ()}, {@link Fragment#onDestroyView()}
     * and {@link android.view.View#onDetachedFromWindow()}.
     *
     * This method is intended for overriding.
     */
    protected fun onDropView() {

    }

    /**
     * A callback to be invoked when a presenter is about to be destroyed.
     */
    interface OnDestroyListener {
        /**
         * Called before {@link Presenter#onDestroy()}.
         */
        open fun onDestroy()
    }

    /**
     * Adds a listener observing {@link #onDestroy}.
     *
     * @param listener a listener to add.
     */
    open fun addOnDestroyListener(listener: OnDestroyListener) {
        onDestroyListeners.add(listener)
    }

    /**
     * Removed a listener observing {@link #onDestroy}.
     *
     * @param listener a listener to remove.
     */
    open fun removeOnDestroyListener(listener: OnDestroyListener) {
        onDestroyListeners.remove(listener)
    }


    /**
     * Initializes the presenter.
     */
    open fun create(bundle: Bundle?) {
        onCreate(bundle)
    }

    /**
     * Destroys the presenter, calling all {@link Presenter.OnDestroyListener} callbacks.
     */
    open fun destroy() {
        for (listener in onDestroyListeners) {
            listener.onDestroy()
        }
        onDestory()
    }

    /**
     * Saves the presenter.
     */
    open fun save(state: Bundle) {
        onSave(state)
    }

    /**
     * Attaches a view to the presenter.
     *
     * @param view a view to attach.
     */
    open fun takeView(view: View) {
        this.view = view
        onTakeView(view)
    }

    /**
     * Detaches the presenter from a view.
     */
    open fun dropView() {
        onDropView()
        this.view = null
    }

}