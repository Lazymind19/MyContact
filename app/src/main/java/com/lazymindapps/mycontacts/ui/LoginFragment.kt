package com.lazymindapps.mycontacts.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.lazymindapps.mycontacts.R
import com.lazymindapps.mycontacts.databinding.ActivityMainBinding.inflate
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
        bindingLogin!!.tvForgetPassword.setOnClickListener {
            bindingLogin!!.llLayout.visibility = View.VISIBLE
            bindingLogin!!.svView.visibility = View.GONE


        }

        bindingLogin!!.btnOk.setOnClickListener {
            val emailString   = bindingLogin!!.etForgetEmail.text.toString()
            if (emailString!=""){
                launch {
                   var forgetStatus = loginViewModel.forgetPassword(emailString)
                    if(forgetStatus){
                        bindingLogin!!.llLayout.visibility = View.GONE
                        bindingLogin!!.svView.visibility = View.VISIBLE

                    }
                    else
                    {
                        Toast.makeText(context,"Something went wrong please try again!!",Toast.LENGTH_LONG).show()
                        bindingLogin!!.etForgetEmail.text=null
                    }


                }

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

    fun forgetPasswordAlertDialog(){
        val alertDialog = AlertDialog.Builder(activity)
        val views =LayoutInflater.from(context).inflate(R.layout.password_reset_file,null)
        var emailString :EditText = views.findViewById(R.id.etForgetEmail)





            alertDialog.setView(views)
                    .setCancelable(true)
            .setPositiveButton("Send",DialogInterface.OnClickListener { dialogInterface, i ->
                var emailValue :String= emailString.text.toString()
                launch {
                    loginViewModel.forgetPassword(emailValue)
                }



            })
            .setNegativeButton("Cancel",DialogInterface.OnClickListener{dialogInterface, i ->
                dialogInterface.dismiss()

            }).show()


    }


    override fun onDestroy() {
        super.onDestroy()
        bindingLogin = null
    }
}