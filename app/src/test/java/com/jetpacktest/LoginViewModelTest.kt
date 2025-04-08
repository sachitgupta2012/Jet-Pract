package com.jetpacktest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.JsonObject
import com.jetpacktest.data.Models
import com.jetpacktest.data.RepositoryClass
import com.jetpacktest.viewmodel.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.After
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import io.mockk.coVerify

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: RepositoryClass
    private lateinit var viewModel: LoginViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        coEvery { repository.getUserToken() } returns flowOf("mock_token")
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success with status code 200 saves user data`() = runTest {
        // Given
        val mockResponse = Models.LoginResponse(StatusCode = 200)
        val response = Response.success(mockResponse)


        val jsonObject = JsonObject()
        jsonObject.addProperty("UserId", "sachitgupta.augurs@gmail.com")
        jsonObject.addProperty("Password", "11111111")
        jsonObject.addProperty("UserRole", "user")
        jsonObject.addProperty("FcmDeviceToken", "ifusdf8943uiebf8934")

        coEvery { repository.loginUser(jsonObject) } returns flowOf(response)

        // When
        viewModel.login("sachitgupta.augurs@gmail.com", "11111111", "ifusdf8943uiebf8934")

        // Then
        advanceUntilIdle()

        assertEquals(mockResponse, viewModel.loginState.value)
        coVerify { repository.saveUserData(mockResponse) }
    }

    @Test
    fun `login success but status code not 200 shows dialog`() = runTest {
        val mockResponse = Models.LoginResponse(StatusCode = 401)
        val response = Response.success(mockResponse)

        coEvery { repository.loginUser(any()) } returns flowOf(response)

        viewModel.login("sachitgupta.augurs@gmail.com", "wrongpass", "ifusdf8943uiebf8934")

        advanceUntilIdle()

        assertEquals(mockResponse, viewModel.loginState.value)
        assertEquals(true, viewModel.showDialog.value)
    }

    @Test
    fun `login failure sets default login response`() = runTest {
        val errorResponse = Response.error<Models.LoginResponse>(400, "".toResponseBody())

        coEvery { repository.loginUser(any()) } returns flowOf(errorResponse)

        viewModel.login("fail@email.com", "fail", "ifusdf8943uiebf8934")

        advanceUntilIdle()

        assertEquals(Models.LoginResponse(), viewModel.loginState.value)
    }

    @Test
    fun `setShowDialog updates dialog state`() {
        viewModel.setShowDialog(true)
        assertTrue(viewModel.showDialog.value)
    }
}
