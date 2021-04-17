package com.example.roomfirebasesync.viewmodels

import androidx.lifecycle.*
import com.example.roomfirebasesync.db.entities.Car
import com.example.roomfirebasesync.db.repository.CarsRepository
import kotlinx.coroutines.launch

class CarsViewModel(private val carsRepo: CarsRepository) : ViewModel() {

    val carsData: LiveData<List<Car>> = carsRepo.carsList.asLiveData()

    val selectedCar: MutableLiveData<Car> = MutableLiveData()

    fun insert(car: Car) = viewModelScope.launch {
        carsRepo.insert(car)
    }

    fun update(device: Car) = viewModelScope.launch {
        carsRepo.update(device)
    }

    fun delete(device: Car) = viewModelScope.launch {
        carsRepo.delete(device)
    }

    fun selectCar(devicePos: Int) {
        carsData.value?.let { carsList ->
            this.selectedCar.postValue(carsList[devicePos])
        }
    }
}

class CarsViewModelFactory(private val repository: CarsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}