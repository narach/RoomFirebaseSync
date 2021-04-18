package com.example.roomfirebasesync.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.roomfirebasesync.R
import com.example.roomfirebasesync.databinding.FragmentAddBinding
import com.example.roomfirebasesync.db.entities.Car
import com.example.roomfirebasesync.viewmodels.CarsViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class AddFragment(
    private val carsViewModel: CarsViewModel
) : Fragment(R.layout.fragment_add) {

    private val logTag = "AddFragment"

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private var newCar = Car(null, "", "", "", 0, "", "", "")

    private val carsColletionRef = Firebase.firestore.collection("cars")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSave.setOnClickListener {
                newCar.brand = etBrand.text.toString()
                newCar.model = etModel.text.toString()
                newCar.engineType = etEngineType.text.toString()
                newCar.engineSize = etEngineSize.text.toString().toInt()
                newCar.transmissionType = etTransmissionType.text.toString()
                newCar.color = etColor.text.toString()

                carsViewModel.insert(newCar)
                saveCarFirestore(newCar)
                Log.d(logTag, "New car: $newCar is added!")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveCarFirestore(car: Car) {
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