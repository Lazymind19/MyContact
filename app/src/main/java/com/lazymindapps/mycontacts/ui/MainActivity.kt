package com.lazymindapps.mycontacts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lazymindapps.mycontacts.R
import com.lazymindapps.mycontacts.repository.LoginRepository
import com.lazymindapps.mycontacts.viewModel.LoginViewModel
import com.lazymindapps.mycontacts.viewModel.LoginViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginRepository = LoginRepository(this)
        val loginViewModelProviderFactory = LoginViewModelFactory(loginRepository)
        loginViewModel = ViewModelProvider(this, loginViewModelProviderFactory).get(LoginViewModel::class.java)


    }
}