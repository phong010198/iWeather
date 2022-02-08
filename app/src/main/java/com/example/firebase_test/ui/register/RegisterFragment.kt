package com.example.firebase_test.ui.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.example.firebase_test.BR
import com.example.firebase_test.R
import com.example.firebase_test.base.BaseFragment
import com.example.firebase_test.databinding.FragmentRegisterBinding
import com.example.firebase_test.ui.MainViewModel
import com.example.firebase_test.ui.UIUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, MainViewModel>() {
    companion object {
        fun newInstance(): RegisterFragment {
            val args = Bundle()
            val fragment = RegisterFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_register

    override val viewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var uiUtil: UIUtil

    @SuppressLint("SetTextI18n")
    override fun initView() {
        viewBinding.layoutFrame.setOnClickListener {
            hideKeyboard(it)
        }

        viewBinding.btnRegister.setOnClickListener {
            val validateEmail = uiUtil.validateEmail(viewModel.registerEmail.value)
            val validatePassword = uiUtil.validatePassword(viewModel.registerPassword.value)
            val validateRePassword = uiUtil.checkRePassword(
                viewModel.registerPassword.value,
                viewModel.registerPassword.value
            )
            if (validateEmail == "" && validatePassword == "" && validateRePassword == "") {
                Log.d(
                    TAG,
                    "${viewModel.registerEmail.value} ${viewModel.registerPassword.value} ${viewModel.registerRePassword.value}"
                )
                auth.createUserWithEmailAndPassword(
                    viewModel.registerEmail.value!!,
                    viewModel.registerPassword.value!!
                )
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Register success
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                        } else {
                            // Register fails
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            showErrorDialog("Something went wrong")
                        }
                    }
                viewBinding.tvRegisterError.visibility = View.GONE
                showErrorDialog("Register OK")
                clearRegisterTemp()
                parentFragmentManager.popBackStack()
            } else {
                viewBinding.tvRegisterError.text =
                    validateEmail + validatePassword + validateRePassword
                viewBinding.tvRegisterError.visibility = View.VISIBLE
            }
        }
    }

    override fun setupObserver() {
    }

    private fun clearRegisterTemp() {
        viewModel.registerEmail = MutableLiveData<String>()
        viewModel.registerPassword = MutableLiveData<String>()
        viewModel.registerRePassword = MutableLiveData<String>()
    }
}