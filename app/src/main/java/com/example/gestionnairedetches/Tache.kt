package com.example.gestionnairedetches

import android.os.Parcel
import android.os.Parcelable

data class Tache(
    var nom: String? ="",
    var date: String? ="",
    var filename: String? =""):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nom)
        parcel.writeString(date)
        parcel.writeString(filename)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tache> {
        override fun createFromParcel(parcel: Parcel): Tache {
            return Tache(parcel)
        }

        override fun newArray(size: Int): Array<Tache?> {
            return arrayOfNulls(size)
        }
    }

}