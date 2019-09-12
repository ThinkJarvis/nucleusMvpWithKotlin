package com.qinggan.myapplication.nucleusMvp

import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

@Inherited
@Retention(RetentionPolicy.RUNTIME)
annotation class RequiresPresenter(val value: KClass<*>)
