package com.qinggan.myapplication.nucleusMvp

import android.os.Parcel

open class ParcelFn {
    companion object {
        private val CLASS_LOADER: ClassLoader = ParcelFn::class.java.classLoader

        fun <T> unmarshall(array: ByteArray?): T {
            val parcel = Parcel.obtain()
            parcel.unmarshall(array, 0, array?.size?:0)
            parcel.setDataPosition(0)
            val value = parcel.readValue(CLASS_LOADER)
            parcel.recycle()
            return value as T
        }

        fun marshall(o: Any?): ByteArray {
            val parcel = Parcel.obtain()
            parcel.writeValue(o)
            val result = parcel.marshall()
            parcel.recycle()
            return result
        }
    }


}