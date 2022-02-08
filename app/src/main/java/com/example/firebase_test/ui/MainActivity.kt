package com.example.firebase_test.ui

import androidx.activity.viewModels
import com.example.firebase_test.BR
import com.example.firebase_test.ui.home.HomeFragment
import com.example.firebase_test.ui.login.LoginFragment
import com.example.firebase_test.R
import com.example.firebase_test.base.BaseActivity
import com.example.firebase_test.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var auth: FirebaseAuth

    override fun initView() {
        val currentUser = auth.currentUser

        viewModel.getMovies()
        viewModel.getSeats()

        if (currentUser != null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.layout_frame,
                    HomeFragment.newInstance(),
                    HomeFragment::class.java.name
                )
                .addToBackStack(HomeFragment::class.java.name).commit()
        } else if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.layout_frame,
                    LoginFragment.newInstance(),
                    LoginFragment::class.java.name
                )
                .addToBackStack(LoginFragment::class.java.name).commit()
        }
    }

    override fun setupObserver() {
    }

    override fun onBackPressed() {
        when {
            viewModel.token.value.isNullOrEmpty() -> super.onBackPressed()
            supportFragmentManager.findFragmentByTag(LoginFragment::class.java.name) != null -> finishAffinity()
            else -> finishAffinity()
        }
    }
}