package com.lazymindapps.mycontacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lazymindapps.mycontacts.databinding.FragmentRegisterBinding
import com.lazymindapps.mycontacts.viewModel.LoginViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegisterFragment :Fragment(),CoroutineScope {
    private var bindingRegister: FragmentRegisterBinding? = null
    lateinit var loginViewModel: LoginViewModel


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        loginViewModel = (activity as MainActivity).loginViewModel
        bindingRegister = FragmentRegisterBinding.inflate(inflater, container, false)
        bindingRegister!!.btnRegister.setOnClickListener {
            val username: String = bindingRegister!!.etUserName.text.toString()
            val password: String = bindingRegister!!.etPassword.text.toString()
            val repass: String = bindingRegister!!.etRePassword.text.toString()
            if (password == repass) {
                sendForRegisteration(username, password)
            } else {
                Toast.makeText(context, "Password and repassword doesnt match", Toast.LENGTH_LONG)
                        .show()
            }

        }



        return bindingRegister!!.root
    }

    private fun sendForRegisteration(username: String?, password: String?) {

        launch {

            loginViewModel.registerUser(username!!, password!!)

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        bindingRegister = null
    }

}

