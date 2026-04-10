package com.nazwa.hopefinder.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.nazwa.hopefinder.entity.Earthquake
import com.nazwa.hopefinder.util.FirebaseHelper

class EarthquakeRepository {
    private val ref = FirebaseHelper.categoryRef

    fun getAllEarthquake(onResult: (List<Earthquake>) -> Unit){
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val earthquakeList = mutableListOf<Earthquake>()
                snapshot.children.forEach{ child ->
                    val earthquake = child.getValue(Earthquake::class.java)
                    earthquake?.let { earthquakeList.add(it) }
                }
                onResult(earthquakeList)
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(emptyList())
            }

            fun addEarthquake(earthquake: Earthquake){
                ref.child(earthquake.id.toString()).setValue(earthquake)
            }
        })
    }
}