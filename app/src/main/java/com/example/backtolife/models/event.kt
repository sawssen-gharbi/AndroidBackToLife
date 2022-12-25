package com.example.backtolife.models

import android.os.Parcel
import android.os.Parcelable

data class event(val nomm:String, val time:String, val doctorName:String, val imagee:Int):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nomm)
        parcel.writeString(time)
        parcel.writeString(doctorName)
        parcel.writeInt(imagee )
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