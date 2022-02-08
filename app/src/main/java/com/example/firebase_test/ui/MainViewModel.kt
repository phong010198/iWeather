package com.example.firebase_test.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.firebase_test.base.BaseViewModel
import com.example.firebase_test.data.model.Movie
import com.example.firebase_test.util.NetworkHelper
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var networkHelper: NetworkHelper
    val TAG = "ngphong"
    var token = MutableLiveData<String>()

    //register
    var registerEmail = MutableLiveData<String>()
    var registerPassword = MutableLiveData<String>()
    var registerRePassword = MutableLiveData<String>()

    //login
    var loginEmail = MutableLiveData<String>()
    var loginPassword = MutableLiveData<String>()
    var loggedInUser = MutableLiveData<FirebaseUser>()

    var movies = MutableLiveData<MutableList<Movie>>()

    private val database = FirebaseDatabase.getInstance()
    fun getMovies() {
        val myRef = database.getReference("Movies")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listMovie = mutableListOf<Movie>()
                for (movie in dataSnapshot.children) {
                    listMovie.add(movie.getValue(Movie::class.java)!!)
                }
                movies.value = listMovie
                Log.d(TAG, "listMovie: ${movies.value}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    var seats = MutableLiveData<MutableList<MutableList<Int>>>()
    fun getSeats() {
        val myRef = database.getReference("Seat")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listSeat = mutableListOf<MutableList<Int>>()
                for (seat in dataSnapshot.children) {
                    val row = mutableListOf<Int>()
                    for (s in seat.children){
                        row.add(s.getValue(Int::class.java)!!)
                    }
                    listSeat.add(row)
                }
                Log.d(TAG, "listSeat: $listSeat")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}