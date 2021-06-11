package com.lazymindapps.mycontacts.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lazymindapps.mycontacts.databinding.FragmentContactProfileBinding
import com.lazymindapps.mycontacts.model.Contact
import com.lazymindapps.mycontacts.viewModel.LoginViewModel

class ContactProfileFragment :Fragment() {
    private var bindingContactProfile:FragmentContactProfileBinding?=null
    private val args:ContactProfileFragmentArgs by navArgs()
    private var singleContact:Contact?=null
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingContactProfile = FragmentContactProfileBinding.inflate(inflater,container,false)

        loginViewModel = (activity as MainActivity).loginViewModel
        return bindingContactProfile!!.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        singleContact = args.singleContact
        Toast.makeText(context,singleContact.toString(),Toast.LENGTH_LONG).show()

        bindingContactProfile!!.tvFirstName.text ="First name : "+ singleContact!!.firstName.capitalize()
        bindingContactProfile!!.tvLastName.text = "Last name : "+singleContact!!.lastName.capitalize()
        bindingContactProfile!!.tvNumber.text = "Number : "+singleContact!!.number.capitalize()
        bindingContactProfile!!.tvAddress.text = "Address : "+singleContact!!.address.capitalize()
        bindingContactProfile!!.tvEmail.text = "Email address : "+singleContact!!.email.capitalize()

        bindingContactProfile!!.btnEdit.setOnClickListener {
            editContact()
        }

        bindingContactProfile!!.btnCall.setOnClickListener {
            val num = singleContact!!.number
            loginViewModel.makeCall(num)

        }


    }

    private fun editContact(){
        val action= ContactProfileFragmentDirections.actionContactProfileFragmentToUpdateContactFragment(singleContact!!)
        findNavController().navigate(action)

    }
}