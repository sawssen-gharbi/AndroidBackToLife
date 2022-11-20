package com.example.backtolife.models

import android.os.Parcel
import android.os.Parcelable

data class event(val nom:String, val time:String, val doctorName:String, val img:Int):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nom)
        parcel.writeString(time)
        parcel.writeString(doctorName)
        parcel.writeInt(img)
    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<event> {
        override fun createFromParcel(parcel: Parcel): event {
            return event(parcel)
        }

        override fun newArray(size: Int): Array<event?> {
            return arrayOfNulls(size)
        }
    }
}