package com.example.backtolife.models

import android.os.Parcel
import android.os.Parcelable


class patient(val image: Int,  val titre: String, val nom: String):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(titre)
        parcel.writeString(nom)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<patient> {
        override fun createFromParcel(parcel: Parcel): patient {
            return patient(parcel)
        }

        override fun newArray(size: Int): Array<patient?> {
            return arrayOfNulls(size)
        }
    }
}