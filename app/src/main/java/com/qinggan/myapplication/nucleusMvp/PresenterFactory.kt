package com.qinggan.myapplication.nucleusMvp


interface PresenterFactory<P : Presenter<*>> {
    open fun createPresenter(): P?
}