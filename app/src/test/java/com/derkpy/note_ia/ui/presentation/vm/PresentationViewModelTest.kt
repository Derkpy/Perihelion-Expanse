package com.derkpy.note_ia.ui.presentation.vm

import android.content.Context
import app.cash.turbine.test
import com.derkpy.note_ia.domain.DataRepository
import com.derkpy.note_ia.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PresentationViewModelTest {

    @RelaxedMockK
    private lateinit var repository: DataRepository

    private lateinit var viewModel: PresentationViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = PresentationViewModel(repository)
    }

    @Test
    fun `Given login with Google When is successful Then uiState is updated isSuccess to true`() =
        runTest {

            onBefore()

            val mockContext = mockk<Context>(relaxed = true)

            coEvery { repository.requestSingWithGoogle(any(), any()) } coAnswers {
                delay(5)
                mockk()
            }
            viewModel.uiState.test {

                //Antes de entrar a la funcion
                val inicio = awaitItem()
                println("Llegó estado: Inicio isLoading=${inicio.isLoading}")
                println("Llegó estado: Inicio error=${inicio.error}")

                assertFalse(inicio.isLoading)
                assertNull(inicio.error)

                viewModel.loginWithGoogle(mockContext)

                println("Esperando cambio de estado")

                //Comienza la funcion a cambiar el estado
                val loading = awaitItem()
                println("Llegó estado: isLoading=${loading.isLoading}")
                println("Llegó estado: isSuccess=${loading.isSuccess}")
                println("Llegó estado: Error=${loading.error}")

                assertTrue(loading.isLoading)
                assertFalse(loading.isSuccess)
                assertNull(loading.error)

                //Estado Final
                val success = awaitItem()
                println("Llegó estado final: Success=${success.isSuccess}")
                println("Llegó estado final: isLoading=${success.isLoading}")
                println("Llegó estado final: Error=${success.error}")

                assertTrue(success.isSuccess)
                assertFalse(success.isLoading)
                assertNull(success.error)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Given login with Google When is not successful Then uiState is updated isSuccess to false`() =
        runTest {
            onBefore()

            val mockContext = mockk<Context>(relaxed = true)

            coEvery { repository.requestSingWithGoogle(any(), any()) } coAnswers {
                delay(5)
                null
            }
            viewModel.uiState.test {

                //Antes de entrar a la funcion
                val inicio = awaitItem()
                println("Llegó estado: Inicio isLoading=${inicio.isLoading}")
                println("Llegó estado: Inicio error=${inicio.error}")

                assertFalse(inicio.isLoading)
                assertNull(inicio.error)

                viewModel.loginWithGoogle(mockContext)

                println("Esperando cambio de estado")

                //Comienza la funcion a cambiar el estado
                val loading = awaitItem()
                println("Llegó estado: isLoading=${loading.isLoading}")
                println("Llegó estado: isSuccess=${loading.isSuccess}")
                println("Llegó estado: Error=${loading.error}")

                assertTrue(loading.isLoading)
                assertFalse(loading.isSuccess)
                assertNull(loading.error)

                //Estado Final
                val success = awaitItem()
                println("Llegó estado final: Success=${success.isSuccess}")
                println("Llegó estado final: isLoading=${success.isLoading}")
                println("Llegó estado final: Error=${success.error}")

                assertFalse(success.isSuccess)
                assertFalse(success.isLoading)
                assertEquals("Login failed. Please check your credentials.", success.error)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `When user cancel then no error is shown`() = runTest {

        onBefore()

        val mockContext = mockk<Context>(relaxed = true)

        coEvery { repository.requestSingWithGoogle(any(), any()) } coAnswers {
            delay(100)
            throw Exception("cancelled")
        }

        viewModel.uiState.test {

            skipItems(1)

            viewModel.loginWithGoogle(mockContext)

            val success = awaitItem().error
            assertNull(success)

            cancelAndIgnoreRemainingEvents()

        }
    }

    @Test
    fun `When a error is thrown then error is shown`() = runTest {

        onBefore()

        val mockContext = mockk<Context> (relaxed = true)
        val errorMsg = "Falla de red"

        coEvery { repository.requestSingWithGoogle(any(), any()) } coAnswers {
            delay(5)
            throw Exception(errorMsg)
        }

        viewModel.uiState.test {

            skipItems(1)

            viewModel.loginWithGoogle(mockContext)

            awaitItem()

            val success = awaitItem()
            val err = success.error

            assertEquals("Error al iniciar sesión: Falla de red",err)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When navigation is done then isSuccess is false`() = runTest {

        onBefore()

        val mockContext = mockk<Context>(relaxed = true)

        coEvery { repository.requestSingWithGoogle(any(), any()) } coAnswers {
            delay(5)
            mockk()
        }

        viewModel.uiState.test {

            skipItems(1)

            viewModel.loginWithGoogle(mockContext)

            skipItems(1)

            val stateLoading = awaitItem()

            assertTrue(stateLoading.isSuccess)
            assertFalse(stateLoading.isLoading)
            assertNull(stateLoading.error)

            viewModel.onNavigationDone()

            val success = awaitItem()

            println("Estado Final de Success: ${success.isSuccess}")
            assertFalse(success.isSuccess)
            assertNull(success.error)


            cancelAndIgnoreRemainingEvents()
        }
    }
}