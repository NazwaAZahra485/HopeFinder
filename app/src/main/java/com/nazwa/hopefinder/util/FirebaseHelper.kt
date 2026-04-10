package com.nazwa.hopefinder.util

import com.google.firebase.database.FirebaseDatabase

object FirebaseHelper {
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val categoryRef = firebaseDatabase.getReference()
}