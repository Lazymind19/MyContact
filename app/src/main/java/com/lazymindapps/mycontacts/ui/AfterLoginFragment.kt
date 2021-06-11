package com.lazymindapps.mycontacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.lazymindapps.mycontacts.databinding.FragmentAfterLoginBinding
import com.lazymindapps.mycontacts.model.Contact
import com.lazymindapps.mycontacts.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AfterLoginFragment :Fragment(),CoroutineScope {
    private var bindingLogin : FragmentAfterLoginBinding?= null
    lateinit var loginViewModel:LoginViewModel
    lateinit var auth: FirebaseAuth
    var userId:String = ""

    private var job= Job()
    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingLogin = FragmentAfterLoginBinding.inflate(inflater,container,false)



        return bindingLogin!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = (activity as MainActivity).loginViewModel
        bindingLogin!!.btnSignout.setOnClickListener {
            launch {
                loginViewModel.logOut()

                val action = AfterLoginFragmentDirections.actionAfterLoginFragmentToLoginFragment2()
                findNavController().navigate(action)


            }

        }

        auth = FirebaseAuth.getInstance()
        userId = auth.uid.toString()





        bindingLogin!!.btnSubmit.setOnClickListener {
            saveContact()
        }

    }

    private fun saveContact() {
        var firstName:String = bindingLogin!!.etFirstName.text.toString()
        var lastName:String = bindingLogin!!.etLastName.text.toString()
        var number:String = bindingLogin!!.etNumber.text.toString()
        var email:String = bindingLogin!!.etEmail.text.toString()
        var address:String = bindingLogin!!.etAddress.text.toString()

        val contact = Contact(firstName,lastName,number,address,email,userId)
        launch {
          val status =  loginViewModel.saveContact(contact)
            if (status){
                val action = AfterLoginFragmentDirections.actionAfterLoginFragmentToSavedContactFragment()
                findNavController().navigate(action)

            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        bindingLogin = null
    }
}