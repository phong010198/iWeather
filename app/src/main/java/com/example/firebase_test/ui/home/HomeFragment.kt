package com.example.firebase_test.ui.home

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.example.firebase_test.BR
import com.example.firebase_test.R
import com.example.firebase_test.base.BaseFragment
import com.example.firebase_test.databinding.FragmentHomeBinding
import com.example.firebase_test.ui.MainViewModel
import com.example.firebase_test.ui.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_home

    @Inject
    lateinit var auth: FirebaseAuth

    override val viewModel: MainViewModel by activityViewModels()

    override fun initView() {
        viewBinding.btnLogout.setOnClickListener {
            auth.signOut()
            clearLoginData()
            if (parentFragmentManager.findFragmentByTag(LoginFragment::class.java.name) != null)
                parentFragmentManager.popBackStack(
                    LoginFragment::class.java.name,
                    0
                )
            else parentFragmentManager.beginTransaction().replace(
                R.id.layout_frame,
                LoginFragment.newInstance(),
                LoginFragment::class.java.name
            ).addToBackStack(LoginFragment::class.java.name).commit()
        }
    }

    override fun setupObserver() {
    }

    private fun clearLoginData() {
        viewModel.loginEmail = MutableLiveData<String>()
        viewModel.loginPassword = MutableLiveData<String>()
        viewModel.loggedInUser = MutableLiveData<FirebaseUser>()
    }
}