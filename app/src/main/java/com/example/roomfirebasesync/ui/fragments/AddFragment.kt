package com.example.roomfirebasesync.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomfirebasesync.R
import com.example.roomfirebasesync.databinding.FragmentAddBinding
import com.example.roomfirebasesync.db.entities.Car
import com.example.roomfirebasesync.viewmodels.CarsViewModel

class AddFragment(
    private val carsViewModel: CarsViewModel
) : Fragment(R.layout.fragment_add) {

    private val logTag = "AddFragment"

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private var newCar = Car(null, "", "", "", 0, "", "", "")

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
                Log.d(logTag, "New car: $newCar is added!")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}