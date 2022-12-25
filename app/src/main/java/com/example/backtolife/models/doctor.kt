package com.example.backtolife.models

import android.os.Parcel
import android.os.Parcelable

class doctor(val img: Int, val date:String, val capacity:String, val titre: String):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(img)
        parcel.writeString(date)
        parcel.writeString(capacity)
        parcel.writeString(titre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<doctor> {
        override fun createFromParcel(parcel: Parcel): doctor {
            return doctor(parcel)
        }

        override fun newArray(size: Int): Array<doctor?> {
            return arrayOfNulls(size)
        }
    }

}