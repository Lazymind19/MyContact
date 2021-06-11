package com.lazymindapps.mycontacts.`interface`

import com.lazymindapps.mycontacts.model.Contact

interface ContactClickListner {
    fun clickedItem(contact:Contact)
    fun makeCall(number: String)
}