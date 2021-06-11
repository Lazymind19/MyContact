package com.lazymindapps.mycontacts.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lazymindapps.mycontacts.repository.LoginRepository

class LoginViewModelFactory (val repository: LoginRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}