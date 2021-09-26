package com.johnlennonlobo.newstrailers.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnlennonlobo.newstrailers.network.NetworkResponse
import com.johnlennonlobo.newstrailers.network.TmdbAPI
import com.johnlennonlobo.newstrailers.network.model.dto.MovieResponseDTO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

val TAG = "logrequest"

class HomeViewModel @Inject constructor(val tmdbAPI: TmdbAPI) : ViewModel() {
  fun getTrending(){
      viewModelScope.launch {
         val response =  tmdbAPI.getTrending(
              "movie",
              "day",
              "pt-BR",
              1)

          when (response){
              is NetworkResponse.Success -> {
                  Log.d(TAG,"Success")
              }
              is NetworkResponse.ApiError -> {
                  Log.d(TAG,"ApiError")
              }
              is NetworkResponse.NetworkError ->{
                  Log.d(TAG,"NetworkError")
              }
              is NetworkResponse.UnknownError -> {
                  Log.d(TAG,"UnknownError")
              }

          }
      }

  }
}