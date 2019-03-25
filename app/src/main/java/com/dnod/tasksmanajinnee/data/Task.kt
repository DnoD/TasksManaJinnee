package com.dnod.tasksmanajinnee.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("dueBy")
    var dueBy: String = "",
    @SerializedName("priority")
    var priority: TaskPriority = TaskPriority.NORMAL
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readSerializable() as TaskPriority
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(dueBy)
        parcel.writeSerializable(priority)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }

}