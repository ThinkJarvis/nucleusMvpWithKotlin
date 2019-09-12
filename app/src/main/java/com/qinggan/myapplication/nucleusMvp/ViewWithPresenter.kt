package com.qinggan.myapplication.nucleusMvp

/**
 * Returns a current presenter factory.
 */
interface ViewWithPresenter<P : Presenter<*>> {

    /**
     * Returns a current presenter factory.
     */
    fun getPresenterFactory(): PresenterFactory<P>?

    /**
     * Sets a presenter factory.
     * Call this method before onCreate/onFinishInflate to override default {@link ReflectionPresenterFactory} presenter factory.
     * Use this method for presenter dependency injection.
     */
    fun setPresenterFactory(presenterFactory: PresenterFactory<P>?)

    /**
     * Returns a current attached presenter.
     * This method is guaranteed to return a non-null value between
     * onResume/onPause and onAttachedToWindow/onDetachedFromWindow calls
     * if the presenter factory returns a non-null value.
     *
     * @return a currently attached presenter or null.
     */
    fun getPresenter(): P?
}