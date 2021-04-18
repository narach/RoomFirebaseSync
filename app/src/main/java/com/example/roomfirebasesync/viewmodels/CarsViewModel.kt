package com.example.roomfirebasesync.viewmodels

import androidx.lifecycle.*
import com.example.roomfirebasesync.db.entities.Car
import com.example.roomfirebasesync.db.repository.CarsRepositoryRoom
import kotlinx.coroutines.launch

class CarsViewModel(private val carsRepoRoom: CarsRepositoryRoom) : ViewModel() {

    val carsData: LiveData<List<Car>> = carsRepoRoom.carsList.asLiveData()

    val selectedCar: MutableLiveData<Car> = MutableLiveData()

    fun insert(car: Car) = viewModelScope.launch {
        carsRepoRoom.insert(car)
    }

    fun update(device: Car) = viewModelScope.launch {
        carsRepoRoom.update(device)
    }

    fun delete(device: Car) = viewModelScope.launch {
        carsRepoRoom.delete(device)
    }

    fun selectCar(devicePos: Int) {
        carsData.value?.let { carsList ->
            this.selectedCar.postValue(carsList[devicePos])
        }
    }
}

class CarsViewModelFactory(private val repositoryRoom: CarsRepositoryRoom) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarsViewModel(repositoryRoom) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}