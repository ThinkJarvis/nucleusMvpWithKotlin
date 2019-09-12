package com.qinggan.myapplication.nucleusMvp

import kotlin.reflect.KClass


open class ReflectionPresenterFactory<P : Presenter<View>, View> constructor(presenterClass: KClass<P>) :
    PresenterFactory<P, View> {

    private var presenterClass: KClass<P>? = presenterClass

    companion object {
        /**
         * This method returns a {@link ReflectionPresenterFactory} instance if a given view class has
         * a {@link RequiresPresenter} annotation, or null otherwise.
         *
         * @param viewClass a class of the view
         * @param <P>       a type of the presenter
         * @return a {@link ReflectionPresenterFactory} instance that is supposed to create a presenter from {@link RequiresPresenter} annotation.
         */

        fun <P : Presenter<View>, View> fromViewClass(viewClass: Class<*>): ReflectionPresenterFactory<P, View>? {
            val annotation = viewClass.getAnnotation(RequiresPresenter::class.java)
            val presenterClass = annotation?.value as KClass<P>
            return if (presenterClass == null) null else ReflectionPresenterFactory(presenterClass)
        }
    }


    override fun createPresenter(): P? {
        try {
            return presenterClass?.java?.newInstance()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}