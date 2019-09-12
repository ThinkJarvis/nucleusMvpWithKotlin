package com.qinggan.myapplication.nucleusMvp

import android.os.Bundle

/**
 * This class adopts a View lifecycle to the Presenter`s lifecycle.
 *
 * @param <P> a type of the presenter.
 */
open class PresenterLifecycleDelegate<P : Presenter<*>> constructor(presenterFactory: PresenterFactory<P>?) {
    companion object {
        const val PRESENTER_KEY: String = "presenter"
        const val PRESENTER_ID_KEY: String = "presenter_id"
    }

    private var presenterFactory: PresenterFactory<P>? = presenterFactory
    private var presenter: P? = null
    private var bundle: Bundle? = null
    private var presenterHasView: Boolean = false

    /**
     * {@link ViewWithPresenter#getPresenterFactory()}
     */
    open fun getPresenterFactory(): PresenterFactory<P>? {
        return presenterFactory
    }

    /**
     * {@link ViewWithPresenter#setPresenterFactory(PresenterFactory)}
     */
    open fun setPresenterFactory(presenterFactory: PresenterFactory<P>?) {
        if (presenter != null) throw  IllegalArgumentException(
            "setPresenterFactory() should be called before onResume()"
        )
        else this.presenterFactory = presenterFactory
    }

    /**
     * {@link ViewWithPresenter#getPresenter()}
     */
    open fun getPresenter(): P? {
        if (presenter == null)
            presenter =
                PresenterStorage.INSTANCE.getPresenter(bundle?.getString(PRESENTER_ID_KEY))

        presenter = presenterFactory?.createPresenter()
        PresenterStorage.INSTANCE.add(presenter)
        presenter?.create(bundle?.getBundle(PRESENTER_KEY) ?: null)

        bundle = null
        return presenter
    }

    /**
     * {@link android.app.Activity#onSaveInstanceState(Bundle)}, {@link android.app.Fragment#onSaveInstanceState(Bundle)}, {@link android.view.View#onSaveInstanceState()}.
     */
    open fun onSaveInstanceState(): Bundle {
        var bundle = Bundle()
        getPresenter()
        var presenterBundle = Bundle()
        presenter?.save(presenterBundle)
        bundle.putBundle(PRESENTER_KEY, presenterBundle)
        bundle.putString(PRESENTER_ID_KEY, PresenterStorage.INSTANCE.getId(presenter))
        return bundle
    }


    /**
     * {@link android.app.Activity#onCreate(Bundle)}, {@link android.app.Fragment#onCreate(Bundle)}, {@link android.view.View#onRestoreInstanceState(Parcelable)}.
     */
    open fun onRestoreInstanceState(presenterState: Bundle?) {
        if (presenter != null)
            throw  IllegalArgumentException("onRestoreInstanceState() should be called before onResume()")
        this.bundle = ParcelFn.unmarshall(ParcelFn.marshall(presenterState))
    }

    /**
     * {@link android.app.Activity#onResume()},
     * {@link android.app.Fragment#onResume()},
     * {@link android.view.View#onAttachedToWindow()}
     */
    open fun onResume(view: Any) {
        getPresenter()
        if (!presenterHasView) {
            presenter?.takeView(view as )
            presenterHasView = true
        }
    }

    /**
     * {@link android.app.Activity#onDestroy()},
     * {@link android.app.Fragment#onDestroyView()},
     * {@link android.view.View#onDetachedFromWindow()}
     */
    open fun onDropView() {
        if (presenter != null && presenterHasView) {
            presenter!!.dropView()
            presenterHasView = false
        }
    }

    /**
     * {@link android.app.Activity#onDestroy()},
     * {@link android.app.Fragment#onDestroy()},
     * {@link android.view.View#onDetachedFromWindow()}
     */
    open fun onDestroy(isFinal: Boolean) {
        if (presenter != null && isFinal) {
            presenter!!.destroy()
            presenter = null
        }
    }

}