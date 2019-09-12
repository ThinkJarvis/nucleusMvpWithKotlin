package com.qinggan.myapplication.nucleusMvp


interface PresenterFactory<P : Presenter<View>, View> {
    open fun createPresenter(): P?
}