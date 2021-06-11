package com.lazymindapps.mycontacts.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.lazymindapps.mycontacts.databinding.FragmentUpdateContactBinding
import com.lazymindapps.mycontacts.model.Contact
import com.lazymindapps.mycontacts.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UpdateContactFragment :Fragment(),CoroutineScope {
    private var bindingUpdate : FragmentUpdateContactBinding?= null
    lateinit var loginViewModel:LoginViewModel
    lateinit var auth: FirebaseAuth
    var userId:String = ""
    private val args: UpdateContactFragmentArgs by navArgs()
    private var contact:Contact?=null

    private var job= Job()
    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingUpdate = FragmentUpdateContactBinding.inflate(inflater,container,false)



        return bindingUpdate!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = (activity as MainActivity).loginViewModel

        contact = args.updateContact

        auth = FirebaseAuth.getInstance()
        userId = auth.uid.toString()





        bindingUpdate!!.btnUpdate.setOnClickListener {
            updateProcessing()
        }

    }

    private fun updateProcessing() {

        val editable = Editable.Factory.getInstance()
        bindingUpdate!!.etFirstName.text = editable.newEditable(contact!!.firstName)
        bindingUpdate!!.etLastName.text = editable.newEditable(contact!!.lastName)
        bindingUpdate!!.etNumber.text = editable.newEditable(contact!!.number)
        bindingUpdate!!.etEmail.text = editable.newEditable(contact!!.email)
        bindingUpdate!!.etAddress.text = editable.newEditable(contact!!.address)

        bindingUpdate!!.btnUpdate.setOnClickListener {
            var map  = mutableMapOf<String,Any>()
            var firstName: String = bindingUpdate!!.etFirstName.text.toString()
            var lastName: String = bindingUpdate!!.etLastName.text.toString()
            var number: String = bindingUpdate!!.etNumber.text.toString()
            var email: String = bindingUpdate!!.etEmail.text.toString()
            var address: String = bindingUpdate!!.etAddress.text.toString()

            if (firstName.isNotEmpty()){
                map["firstName"] = firstName
            }
            if (lastName.isNotEmpty()){
                map["lastName"] = lastName
            }
            if (number.isNotEmpty()){
                map["number"] = number
            }
            map["userId"] = auth.uid.toString()




//            val contact = Contact(firstName, lastName, number, address, email, userId)


            launch {
                val status = loginViewModel.updateContact(contact!!,map)
                if (status) {
                    val action = UpdateContactFragmentDirections.actionUpdateContactFragmentToSavedContactFragment()
                    findNavController().navigate(action)

                }

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        bindingUpdate = null
    }
}