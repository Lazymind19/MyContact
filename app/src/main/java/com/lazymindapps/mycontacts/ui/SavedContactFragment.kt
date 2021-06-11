package com.lazymindapps.mycontacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.contentcapture.ContentCaptureCondition
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.lazymindapps.mycontacts.`interface`.ContactClickListner
import com.lazymindapps.mycontacts.adapter.SavedContactAdapter
import com.lazymindapps.mycontacts.databinding.FragmentSavedContactsBinding
import com.lazymindapps.mycontacts.model.Contact
import com.lazymindapps.mycontacts.viewModel.LoginViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SavedContactFragment:Fragment(),CoroutineScope {
    private var savedContactBinding:FragmentSavedContactsBinding?=null
    lateinit var loginViewModel: LoginViewModel
    lateinit var auth:FirebaseAuth
    var userId:String=""
    private var singleContact: Contact? = null
    private var contactList = mutableListOf<Contact>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job+ Dispatchers.Main

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        savedContactBinding = FragmentSavedContactsBinding.inflate(inflater,container,false)
        return savedContactBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = (activity as MainActivity).loginViewModel
        auth = FirebaseAuth.getInstance()
        userId = auth.uid.toString()

        savedContactBinding!!.ibNewContact.setOnClickListener {

            val action = SavedContactFragmentDirections.actionSavedContactFragmentToAfterLoginFragment2()
            findNavController().navigate(action)
        }

        launch {
            contactList = loginViewModel.retriveContact(userId)
            recyclerViewSetup(contactList)


        }







    }

    private fun recyclerViewSetup(contactList:MutableList<Contact>) {
        if (contactList!=null){
            savedContactBinding!!.tvNoFoundMessage.visibility = View.GONE
            savedContactBinding!!.rvSavedContact.visibility=View.VISIBLE
            savedContactBinding!!.rvSavedContact.layoutManager = LinearLayoutManager(context)
            val adapter = context?.let { SavedContactAdapter(it,object : ContactClickListner {
                override fun clickedItem(contact: Contact) {
                    singleContact = contact
                    singleContactProfile(singleContact!!)

                }

                override fun makeCall(number: String) {
                    loginViewModel.makeCall(number)
                }
            },
            contactList) }
            savedContactBinding!!.rvSavedContact.adapter = adapter

        }else{
            savedContactBinding!!.rvSavedContact.visibility = View.GONE
            savedContactBinding!!.tvNoFoundMessage.visibility = View.VISIBLE
        }


    }

    private fun singleContactProfile(singleContact: Contact) {
        if (singleContact!=null){
            val action = SavedContactFragmentDirections.actionSavedContactFragmentToContactProfileFragment(singleContact)

            findNavController().navigate(action)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        savedContactBinding = null
    }

}