package com.example.roomfirebasesync.db.repository

import android.util.Log
import com.example.roomfirebasesync.db.entities.Car
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class CarsRepositoryFirestore() {

    private val logTag = "FirestoreRepository"

    private val carsColletionRef = Firebase.firestore.collection("cars")

    fun saveCarFirestore(car: Car) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                carsColletionRef.add(car).await()
                Log.d(logTag, "Successfully saved car $car to Firestore!")
            } catch(e: Exception) {
                Log.d(logTag, e.message!!)
            }
        }
    }
}