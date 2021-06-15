package com.lazymindapps.mycontacts.viewModel

import androidx.lifecycle.ViewModel
import com.lazymindapps.mycontacts.model.Contact
import com.lazymindapps.mycontacts.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(
    val repository: LoginRepository
) :ViewModel(){
    init {

    }

    suspend fun registerUser(userName:String,password:String) = withContext(Dispatchers.IO){
        repository.registerUser(userName,password)
    }

    suspend fun loginUser(userName: String,password: String) = withContext(Dispatchers.IO){
        repository.loginUser(userName,password)
    }

    suspend fun checkUserStatus():Boolean = withContext(Dispatchers.IO){
        repository.checkUserStatus()
    }
    suspend fun logOut() = withContext(Dispatchers.IO){
        repository.logOut()
    }

    suspend fun saveContact(contact: Contact):Boolean = withContext(Dispatchers.IO){
        repository.saveContact(contact)
    }

    suspend fun retriveContact(userId:String):MutableList<Contact> = withContext(Dispatchers.IO){
        repository.retriveContact(userId)
    }

    suspend fun updateContact(previousContact: Contact,newContact: Map<String,Any>):Boolean = withContext(Dispatchers.IO){
        repository.updateContact(previousContact,newContact)
    }

    fun makeCall(number: String){
        repository.makeCall(number)
    }

    suspend fun deleteContact(contact:Contact) = withContext(Dispatchers.IO){
        repository.deleteContact(contact)
    }

    suspend fun forgetPassword(email:String):Boolean = withContext(Dispatchers.IO){
        repository.forgetPassword(email)
    }


}