package com.johnlennonlobo.newstrailers.repository

import com.johnlennonlobo.newstrailers.AppConstant
import com.johnlennonlobo.newstrailers.network.ErrorResponse
import com.johnlennonlobo.newstrailers.network.NetworkResponse
import com.johnlennonlobo.newstrailers.network.TmdbAPI
import com.johnlennonlobo.newstrailers.network.model.dto.MovieDTO
import com.johnlennonlobo.newstrailers.network.model.dto.MovieResponseDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

interface HomeDataSource {
    suspend fun getListOfMovies(
        dispatcher: CoroutineDispatcher,
        homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) -> Unit
    )
}

class HomeDataSourceImpl @Inject constructor(private val tmdbAPI: TmdbAPI) : HomeDataSource {
    override suspend fun getListOfMovies(
        dispatcher: CoroutineDispatcher,
        homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) -> Unit
    ) {
        withContext(dispatcher) {
            try {
                   val trendingMoviesResponse = async { tmdbAPI.getTrending(AppConstant.LANGUAGE,1)}
                   val upcomingMoviesResponse = async {tmdbAPI.getUpcoming(AppConstant.LANGUAGE,1)}
                   val popularMoviesResponse = async {tmdbAPI.getPopular(AppConstant.LANGUAGE,1)}
                   val topRatedMoviesResponse = async {tmdbAPI.getTopRated(AppConstant.LANGUAGE,1)}

                    processData(
                        homeResultCallback = homeResultCallback,
                        trending = trendingMoviesResponse.await(),
                        upcoming=  upcomingMoviesResponse.await(),
                        popular = popularMoviesResponse.await(),
                        topRated = topRatedMoviesResponse.await()
                    )

            } catch (e: Exception) {
                throw e
            }
        }
    }

    private fun processData(
        homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) -> Unit,
        trending: NetworkResponse<MovieResponseDTO, ErrorResponse>,
        upcoming: NetworkResponse<MovieResponseDTO, ErrorResponse>,
        popular: NetworkResponse<MovieResponseDTO, ErrorResponse>,
        topRated: NetworkResponse<MovieResponseDTO, ErrorResponse>) {

        val pair1 = buidResponse(trending)
        val pair2 = buidResponse(upcoming)
        val pair3 = buidResponse(popular)
        val pair4 = buidResponse(topRated)

        when {
            pair1.first == null -> {
                pair1.second?.let{homeResultCallback(it)}
            }
            pair2.first == null -> {
                pair2.second?.let { homeResultCallback(it) }
            }
            pair3.first == null -> {
                pair3.second?.let { homeResultCallback(it) }
            }
            pair4.first == null -> {
                pair4.second?.let { homeResultCallback(it) }
            }
            else -> {
                val resultList = ArrayList<List<MovieDTO>>()
                pair1.first?.let { resultList.add(it) }
                pair2.first?.let { resultList.add(it) }
                pair3.first?.let { resultList.add(it) }
                pair4.first?.let { resultList.add(it) }
                homeResultCallback(NetworkResponse.Success(resultList))
            }
        }
    }

    private fun buidResponse(response: NetworkResponse<MovieResponseDTO, ErrorResponse>)
    : Pair<List<MovieDTO>?, NetworkResponse<List<List<MovieDTO>>, ErrorResponse>?>{
        return when(response){
            is NetworkResponse.Success -> {
                Pair(response.body.results , null)
            }
            is NetworkResponse.ApiError -> {
                Pair(null,NetworkResponse.ApiError(response.body,response.code))
            }
            is NetworkResponse.NetworkError -> {
                Pair(null, NetworkResponse.NetworkError(IOException()))
            }
            is NetworkResponse.UnknownError -> {
                Pair(null, NetworkResponse.UnknownError(Throwable()))
            }
        }
    }
}
