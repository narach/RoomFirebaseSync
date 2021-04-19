package com.example.roomfirebasesync.db.repository

import android.util.Log
import com.example.roomfirebasesync.db.entities.Car
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
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

    fun getAllCars() : MutableList<Car> {
        val carsList = mutableListOf<Car>()

        CoroutineScope(Dispatchers.IO).launch {
            val querySnapshot = carsColletionRef.get().await()
            for (document in querySnapshot.documents) {
                val car = document.toObject<Car>()
                car?.let {
                    carsList.add(it)
                }
            }
        }

        return carsList
    }

    fun deleteAllCars() {
        CoroutineScope(Dispatchers.IO).launch {
            val carsQuery = carsColletionRef.get().await()
            for(document in carsQuery) {
                carsColletionRef.document(document.id).delete()
            }
        }
    }
}