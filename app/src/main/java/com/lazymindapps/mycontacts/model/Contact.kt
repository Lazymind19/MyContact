package com.lazymindapps.mycontacts.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Contact (
        var firstName:String ="",
        var lastName:String = "",
        var  number:String="",
        var address:String="",
        var email:String="",
        var userId:String="",


        ):Serializable