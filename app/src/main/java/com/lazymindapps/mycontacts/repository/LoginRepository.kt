package com.lazymindapps.mycontacts.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lazymindapps.mycontacts.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.StringBuilder

class LoginRepository(
    val conxtext:Context
) {

    companion object{
        var auth:FirebaseAuth = FirebaseAuth.getInstance()
        var contactCollection = Firebase.firestore.collection("contacts")


    }


suspend fun registerUser(userName:String,password:String) = withContext(Dispatchers.IO){
    try {
        auth.createUserWithEmailAndPassword(userName,password).await()
        withContext(Dispatchers.Main){
            Toast.makeText(conxtext,"Registered Successfully",Toast.LENGTH_LONG).show()
        }

    }catch (e:Exception){
        withContext(Dispatchers.Main){
            Toast.makeText(conxtext,e.message,Toast.LENGTH_LONG).show()
        }

    }

}

    suspend fun updateContact(previousContact: Contact,newContact: Map<String,Any>) : Boolean = withContext(Dispatchers.IO){
        val contactQuery = contactCollection.whereEqualTo("firstName",previousContact.firstName)
                .whereEqualTo("lastName",previousContact.lastName)
                .whereEqualTo("number",previousContact.number)
                .whereEqualTo("userId", auth.uid)
                .get().await()
        if (contactQuery.documents.isNotEmpty()){
            for (document in contactQuery){
                try {
                    contactCollection.document(document.id).set(
                            newContact,
                            SetOptions.merge()
                    ).await()
                    withContext(Dispatchers.Main){
                        Toast.makeText(conxtext,"Data updated Successfully",Toast.LENGTH_LONG).show()
                    return@withContext true
                    }

                } catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(conxtext,e.message,Toast.LENGTH_LONG).show()
                        return@withContext false
                    }
                }
            }

        }else{
            withContext(Dispatchers.Main){
                Toast.makeText(conxtext,"No any contact found",Toast.LENGTH_LONG).show()
                return@withContext false
            }
        }

        return@withContext true


    }

suspend fun saveContact(contact: Contact) :Boolean= withContext(Dispatchers.IO){
try {
    contactCollection.add(contact).await()
    withContext(Dispatchers.Main){
        Toast.makeText(conxtext,"Contact Added Successfully",Toast.LENGTH_LONG).show()
        return@withContext true
    }


}catch (e:Exception){
    withContext(Dispatchers.Main){
        Toast.makeText(conxtext,"Error : ${e.message}",Toast.LENGTH_LONG).show()
        return@withContext false
    }

}
}

    suspend fun loginUser(userName:String,password:String) = withContext(Dispatchers.IO){
        try {

            auth.signInWithEmailAndPassword(userName, password).await()
            withContext(Dispatchers.Main){
                Toast.makeText(conxtext,"Login in successful",Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(conxtext,e.message.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }

    suspend fun checkUserStatus():Boolean = withContext(Dispatchers.IO){
        val user = auth.currentUser
        return@withContext user!=null
    }

    suspend fun logOut() = withContext(Dispatchers.IO){
        auth.signOut()
    }


    suspend fun retriveContact(userId:String) :MutableList<Contact>  = withContext(Dispatchers.IO){
        val list = mutableListOf<Contact>()
        try {
            val querySnapShot = contactCollection
                    .whereEqualTo("userId", userId)

                    .get()
                    .await()
            val sb =StringBuilder()
            for (document in querySnapShot.documents){
                val contact = document.toObject(Contact::class.java)
                if (contact != null) {
                    list.add(contact)
                }

            }

        }
        catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(conxtext,"Error : ${e.message}",Toast.LENGTH_LONG).show()
            }
        }
        return@withContext list
    }

    fun makeCall(number:String){
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$number")
        conxtext.startActivity(dialIntent)
    }

    suspend fun deleteContact(contact:Contact) = withContext(Dispatchers.IO){
        val deleteQuery = contactCollection.whereEqualTo("firstName",contact.firstName)
            .whereEqualTo("lastName",contact.lastName)
            .whereEqualTo("number",contact.number)
            .whereEqualTo("userId",contact.userId)
            .get()
            .await()

        if (deleteQuery.documents.isNotEmpty()){
            for (document in deleteQuery) {
                try {
                    contactCollection.document(document.id).delete().await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(conxtext, "Contact Deleted", Toast.LENGTH_LONG).show()
                    }
                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(conxtext,e.message,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }else {
            withContext(Dispatchers.Main) {
                Toast.makeText(conxtext, "No any contact found", Toast.LENGTH_LONG).show()
            }
        }
        }

    suspend fun forgetPassword(email:String) :Boolean = withContext(Dispatchers.IO){
        try {
            Firebase.auth.sendPasswordResetEmail(email).addOnCompleteListener{task->
                if (task.isSuccessful){
                    Toast.makeText(conxtext,"Password reset link is sent to your email...",Toast.LENGTH_LONG).show()

                }
                else{
                    Toast.makeText(conxtext, task.exception?.message,Toast.LENGTH_LONG).show()

                }

            }
            return@withContext true
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(conxtext,e.message,Toast.LENGTH_LONG).show()



            }
            return@withContext  false
        }




    }
    }





