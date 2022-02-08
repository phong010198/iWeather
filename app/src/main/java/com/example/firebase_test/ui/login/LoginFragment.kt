package com.example.firebase_test.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.firebase_test.BR
import com.example.firebase_test.R
import com.example.firebase_test.base.BaseFragment
import com.example.firebase_test.databinding.FragmentLoginBinding
import com.example.firebase_test.ui.MainViewModel
import com.example.firebase_test.ui.UIUtil
import com.example.firebase_test.ui.home.HomeFragment
import com.example.firebase_test.ui.register.RegisterFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, MainViewModel>() {
    companion object {
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_login

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

        viewBinding.btnLogin.setOnClickListener {
            val validateEmail = uiUtil.validateEmail(viewModel.loginEmail.value)
            val validatePassword = uiUtil.validatePassword(viewModel.loginPassword.value)
            if (validateEmail == "" && validatePassword == "") {
                Log.d(
                    TAG,
                    "initView: ${viewModel.loginEmail.value} ${viewModel.loginPassword.value}"
                )
                auth.signInWithEmailAndPassword(
                    viewModel.loginEmail.value!!,
                    viewModel.loginPassword.value!!
                )
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success
                            Log.d(TAG, "signInWithEmail:success")
                            viewModel.loggedInUser.value = auth.currentUser
                            parentFragmentManager.beginTransaction()
                                .replace(
                                    R.id.layout_frame,
                                    HomeFragment.newInstance(),
                                    HomeFragment::class.java.name
                                )
                                .addToBackStack(HomeFragment::class.java.name).commit()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            showErrorDialog("Email or password is not correct")
                            viewModel.loggedInUser.value = null
                        }
                    }
                viewBinding.tvLoginError.visibility = View.GONE
            } else {
                viewBinding.tvLoginError.text = validateEmail + validatePassword
                viewBinding.tvLoginError.visibility = View.VISIBLE
            }
        }

        viewBinding.tvQuestionRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(
                    R.id.layout_frame,
                    RegisterFragment.newInstance(),
                    RegisterFragment::class.java.name
                )
                .addToBackStack(RegisterFragment::class.java.name).commit()
        }
    }

    override fun setupObserver() {
    }
}