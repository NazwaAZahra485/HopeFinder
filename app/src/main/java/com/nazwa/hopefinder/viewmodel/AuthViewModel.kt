package com.nazwa.hopefinder.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel: ViewModel() {
    private val auth: FirebaseAuth =  FirebaseAuth.getInstance()
    var user by mutableStateOf<FirebaseUser?>(null)
    var isLoading by mutableStateOf(false)
    var errMsg by mutableStateOf<String?>(null)

    init {
        user = auth.currentUser
    }

    fun signInEmailPassword(email: String, password: String){
        isLoading = true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            isLoading = false
            if(task.isSuccessful){
                user = auth.currentUser
            }else{
                errMsg = task.exception?.localizedMessage
            }
        }
    }

    fun signOut(){
        auth.signOut()
        user = null
    }
}