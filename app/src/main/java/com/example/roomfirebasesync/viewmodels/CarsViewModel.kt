package com.example.roomfirebasesync.viewmodels

import androidx.lifecycle.*
import com.example.roomfirebasesync.db.entities.Car
import com.example.roomfirebasesync.db.repository.CarsRepositoryFirestore
import com.example.roomfirebasesync.db.repository.CarsRepositoryRoom
import kotlinx.coroutines.launch

class CarsViewModel(
    private val carsRepoRoom: CarsRepositoryRoom,
    private val carsRepoFirestore: CarsRepositoryFirestore
) : ViewModel() {

    val carsData: LiveData<List<Car>> = carsRepoRoom.carsList.asLiveData()
    val carsDataFirestore: MutableLiveData<List<Car>> = MutableLiveData()

    val selectedCar: MutableLiveData<Car> = MutableLiveData()

    fun insert(car: Car) = viewModelScope.launch {
        carsRepoRoom.insert(car)
    }

    fun update(car: Car) = viewModelScope.launch {
        carsRepoRoom.update(car)
    }

    fun delete(car: Car) = viewModelScope.launch {
        carsRepoRoom.delete(car)
    }

    fun selectCar(carPos: Int) {
        carsData.value?.let { carsList ->
            this.selectedCar.postValue(carsList[carPos])
        }
    }

    fun saveCarFirestore(car: Car) {
        viewModelScope.launch {
            carsRepoFirestore.saveCarFirestore(car)
        }
    }
}

class CarsViewModelFactory(
    private val repositoryRoom: CarsRepositoryRoom,
    private val repositoryFirestore: CarsRepositoryFirestore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarsViewModel(repositoryRoom, repositoryFirestore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}