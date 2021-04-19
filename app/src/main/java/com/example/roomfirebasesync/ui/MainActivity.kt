package com.example.roomfirebasesync.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.roomfirebasesync.DbSyncApp
import com.example.roomfirebasesync.R
import com.example.roomfirebasesync.databinding.ActivityMainBinding
import com.example.roomfirebasesync.ui.fragments.AddFragment
import com.example.roomfirebasesync.ui.fragments.ListFragment
import com.example.roomfirebasesync.viewmodels.CarsViewModel
import com.example.roomfirebasesync.viewmodels.CarsViewModelFactory
import com.example.roomfirebasesync.workmanager.SyncViewModel
import com.example.roomfirebasesync.workmanager.SyncViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var frCarsList: ListFragment
    private lateinit var frAddCar: AddFragment

    private val carsViewModel: CarsViewModel by viewModels {
        CarsViewModelFactory(
            (application as DbSyncApp).carsRepository,
            (application as DbSyncApp).carsRepositoryFirestore,
        )
    }

    private val syncViewModel: SyncViewModel by viewModels {
        SyncViewModelFactory(
            application,
            (application as DbSyncApp).carsRepository,
            (application as DbSyncApp).carsRepositoryFirestore,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        frCarsList = ListFragment(carsViewModel, syncViewModel)
        frAddCar = AddFragment(carsViewModel)

        binding.bottomMenu.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.miList -> setCurrentFragment(frCarsList)
                R.id.miAdd -> setCurrentFragment(frAddCar)
            }
            true
        }

        setCurrentFragment(frCarsList)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }

    private fun listDevices() {
        binding.bottomMenu.selectedItemId = R.id.miList
        setCurrentFragment(frCarsList)
    }

}