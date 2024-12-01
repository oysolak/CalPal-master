package com.example.calpal.model

import android.os.Parcel
import android.os.Parcelable
import java.util.FormattableFlags

data class Meal(val mealImage : Int, val mealName : String) : Parcelable {
    constructor(parcel : Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mealImage)
        parcel.writeString(mealName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Meal> {
        override fun createFromParcel(parcel: Parcel): Meal {
            return Meal(parcel)
        }

        override fun newArray(size: Int): Array<Meal?> {
            return arrayOfNulls(size)
        }
    }

}
