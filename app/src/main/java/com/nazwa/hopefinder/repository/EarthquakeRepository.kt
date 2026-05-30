package com.nazwa.hopefinder.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.nazwa.hopefinder.entity.GempaBumi
import com.nazwa.hopefinder.util.FirebaseHelper

class EarthquakeRepository {
    private val ref = FirebaseHelper.categoryRef

    fun getAllEarthquake(onResult: (List<GempaBumi>) -> Unit){
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val gempaBumiList = mutableListOf<GempaBumi>()
                snapshot.children.forEach{ child ->
                    val gempaBumi = child.getValue(GempaBumi::class.java)
                    gempaBumi?.let { gempaBumiList.add(it) }
                }
                onResult(gempaBumiList)
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(emptyList())
            }

            fun addEarthquake(gempaBumi: GempaBumi){
                ref.child(gempaBumi.id.toString()).setValue(gempaBumi)
            }
        })
    }
}