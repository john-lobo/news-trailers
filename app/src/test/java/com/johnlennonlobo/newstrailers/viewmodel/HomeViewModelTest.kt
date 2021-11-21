package com.johnlennonlobo.newstrailers.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.johnlennonlobo.newstrailers.AppConstant
import com.johnlennonlobo.newstrailers.network.ErrorResponse
import com.johnlennonlobo.newstrailers.network.NetworkResponse
import com.johnlennonlobo.newstrailers.network.model.dto.MovieDTO
import com.johnlennonlobo.newstrailers.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()
    private var homeDataSourceMock : HomeDataSourceMock ? = null
    private lateinit var listOfListMoviesMock: List<List<MovieDTO>>
    private lateinit var listOfMoviesMock: List<MovieDTO>

    @Before
    fun init(){
        listOfMoviesMock = listOf(MovieDTO("",0,"",""))
        listOfListMoviesMock = listOf(listOfMoviesMock , listOfMoviesMock , listOfMoviesMock, listOfMoviesMock )
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when LISTS OF MOVIES request returns SUCCESSFULLY expect live lists filled`() = dispatcher.runBlockingTest {
        // Arrange
        homeDataSourceMock = HomeDataSourceMock(NetworkResponse.Success(listOfListMoviesMock))
        val viewModel = HomeViewModel(homeDataSourceMock!!,dispatcher)

        // Act
        viewModel.getlistsOfMovies()

        // Assert
        assertEquals(listOfListMoviesMock,viewModel.listOfMovies?.value)
        assertEquals(false,viewModel.isLoading?.value)
        assertEquals(false,viewModel.errorMessageVisibility?.value)
    }

    @Test
    fun `when LISTS OF MOVIES request returns NETWORK ERROR expect error live data filled`() = dispatcher.runBlockingTest {
        // Arrange
        homeDataSourceMock = HomeDataSourceMock(NetworkResponse.NetworkError(IOException()))
        val viewModel = HomeViewModel(homeDataSourceMock!!,dispatcher)

        // Act
        viewModel.getlistsOfMovies()

        // Assert
        errorTest(viewModel,AppConstant.NETWORK_ERROR_MESSAGE)
    }

    @Test
    fun `when LISTS OF MOVIES request returns API ERROR expect error live data filled`() = dispatcher.runBlockingTest {
        // Arrange
        homeDataSourceMock = HomeDataSourceMock(NetworkResponse.ApiError(ErrorResponse(),400))
        val viewModel = HomeViewModel(homeDataSourceMock!!,dispatcher)

        // Act
        viewModel.getlistsOfMovies()

        // Assert
        errorTest(viewModel,AppConstant.API_ERROR_MESSAGE)
    }

    @Test
    fun `when LISTS OF MOVIES request returns UNKNOWN ERROR expect error live data filled`() = dispatcher.runBlockingTest {
        // Arrange
        homeDataSourceMock = HomeDataSourceMock(NetworkResponse.UnknownError(Throwable()))
        val viewModel = HomeViewModel(homeDataSourceMock!!,dispatcher)

        // Act
        viewModel.getlistsOfMovies()

        // Assert
        errorTest(viewModel,AppConstant.UNKNOWN_ERROR_MESSAGE)
    }

    private fun errorTest(viewModel: HomeViewModel, errorMessage:String) {
        assertEquals(null, viewModel.listOfMovies?.value)
        assertEquals(false, viewModel.isLoading?.value)
        assertEquals(true, viewModel.errorMessageVisibility?.value)
        assertEquals(errorMessage, viewModel.errorMessage?.value)
    }

}

class HomeDataSourceMock(private val result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) : HomeDataSource{

    override suspend fun getListOfMovies(
        dispatcher: CoroutineDispatcher,
        homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) -> Unit
    ) {
        homeResultCallback(result)
    }

}