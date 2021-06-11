package com.lazymindapps.mycontacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lazymindapps.mycontacts.databinding.FragmentLoginBinding
import com.lazymindapps.mycontacts.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginFragment :Fragment(),CoroutineScope {
    private var bindingLogin : FragmentLoginBinding?= null
    lateinit var loginViewModel:LoginViewModel

    private var job= Job()
    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        bindingLogin = FragmentLoginBinding.inflate(inflater,container,false)


        linking()

        return bindingLogin!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = (activity as MainActivity).loginViewModel
        checkUserStatus()

    }

    private fun linking(){
        bindingLogin!!.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        bindingLogin!!.btnLogin.setOnClickListener {
            val userName:String = bindingLogin!!.etUserName.text.toString()
            val password:String = bindingLogin!!.etPassword.text.toString()
            launch {
                loginViewModel.loginUser(userName,password)
                checkUserStatus()
            }

        }
    }

    private fun checkUserStatus(){
        launch {
            val userStatus = loginViewModel.checkUserStatus()
            if (userStatus){
               val action =LoginFragmentDirections.actionLoginFragmentToSavedContactFragment()
                findNavController().navigate(action)


            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        bindingLogin = null
    }
}