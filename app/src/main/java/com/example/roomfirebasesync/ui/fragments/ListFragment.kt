package com.example.roomfirebasesync.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.roomfirebasesync.R
import com.example.roomfirebasesync.databinding.FragmentListBinding
import com.example.roomfirebasesync.viewmodels.CarsViewModel
import com.example.roomfirebasesync.workmanager.SyncViewModel
import timber.log.Timber

class ListFragment(
    private val carsViewModel: CarsViewModel,
    private val syncViewModel: SyncViewModel,
) : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val logTag = "ListFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carsViewModel.carsData.observe(
            viewLifecycleOwner, { carsList->
                Timber.d("Cars list updated to $carsList")
                Log.d(logTag, "Cars list updated to $carsList!")
            }
        )

        with(binding) {
            btnSyncDb.setOnClickListener {
                syncViewModel.applySync()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}