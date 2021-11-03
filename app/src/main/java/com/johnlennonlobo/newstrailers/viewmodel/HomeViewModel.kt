package com.johnlennonlobo.newstrailers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnlennonlobo.newstrailers.AppConstant.API_ERROR_MESSAGE
import com.johnlennonlobo.newstrailers.AppConstant.NETWORK_ERROR_MESSAGE
import com.johnlennonlobo.newstrailers.AppConstant.UNKNOWN_ERROR_MESSAGE
import com.johnlennonlobo.newstrailers.database.IoDispatcher
import com.johnlennonlobo.newstrailers.network.NetworkResponse
import com.johnlennonlobo.newstrailers.network.TmdbAPI
import com.johnlennonlobo.newstrailers.network.model.dto.MovieDTO
import com.johnlennonlobo.newstrailers.network.model.dto.MovieResponseDTO
import com.johnlennonlobo.newstrailers.repository.HomeDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val homeDataSource: HomeDataSource,
        @IoDispatcher
        private val ioDispatcher: CoroutineDispatcher ) : ViewModel() {

    private val _listOfMovies: MutableLiveData<List<List<MovieDTO>>>? = MutableLiveData()
    val listOfMovies: LiveData<List<List<MovieDTO>>>? = _listOfMovies

    private val _errorMessage: MutableLiveData<String>? = MutableLiveData()
    val errorMessage: LiveData<String>? = _errorMessage

    private val _errorMessageVisibility: MutableLiveData<Boolean>? = MutableLiveData()
    val errorMessageVisibility: LiveData<Boolean>? = _errorMessageVisibility

    private val _isLoading: MutableLiveData<Boolean>? = MutableLiveData()
    val isLoading: LiveData<Boolean>? = _isLoading


    fun getlistsOfMovies() {
        showErrorMessage(false)
        try {
            viewModelScope.launch(ioDispatcher) {
                homeDataSource.getListOfMovies(ioDispatcher) { result ->

                    when (result) {
                        is NetworkResponse.Success -> {
                            _isLoading?.value = false
                            _errorMessageVisibility?.value = false
                            _listOfMovies?.value = result.body

                        }
                        is NetworkResponse.NetworkError -> {
                            showErrorMessage(true , NETWORK_ERROR_MESSAGE)
                        }
                        is NetworkResponse.ApiError -> {
                            showErrorMessage(true, API_ERROR_MESSAGE)
                        }
                        is NetworkResponse.UnknownError -> {
                            showErrorMessage(true, UNKNOWN_ERROR_MESSAGE)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private fun showErrorMessage(show: Boolean, errorMessage:String? = null){
        _isLoading?.postValue(!show)
        _errorMessageVisibility?.postValue(show)
        _errorMessage?.postValue(errorMessage)
    }
}