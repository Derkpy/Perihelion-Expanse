package com.derkpy.note_ia.ui.home.vm

import app.cash.turbine.test
import com.derkpy.note_ia.domain.DataRepository
import com.derkpy.note_ia.domain.model.NoteModel
import com.derkpy.note_ia.ui.home.contract.FormState
import com.derkpy.note_ia.ui.home.contract.HomeEvent
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.SheetMode
import com.derkpy.note_ia.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import io.mockk.every


class HomeViewModelTest {

    @RelaxedMockK
    private lateinit var repository: DataRepository

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun `when title is changed then uiState is updated`() {

        //GIVEN
        val newTitle = "TITULO DOS JSJSJSJA 2"

        //WHEN
        viewModel.onEvent(HomeEvent.TitleNoteChanged(newTitle))

        //THEN
        val curremtTitle = viewModel.uiState.value.noteState.title
        assertEquals(newTitle, curremtTitle)
    }

    @Test
    fun `when description is changed then uiState is updated`() {

        val newDescription = "Description 2"

        viewModel.onEvent(HomeEvent.DescriptionNoteChanged(newDescription))

        val currentDescription = viewModel.uiState.value.noteState.description
        assertEquals(newDescription, currentDescription)

    }

    @Test
    fun `when SheetMode is changed then uiState is updated`(){

        val mode = SheetMode.DESCRIPTION_NOTE

        viewModel.onEvent(HomeEvent.ModeSheetChanged(mode))

        val current = viewModel.uiState.value.modeSheet
        assertEquals(mode, current)

    }

    @Test
    fun `when subTask is changed then uiState is updated`() {

        val subTask = "SubTask 1\n" +
                "SubTask 2\n" +
                "SubTask 3"

        viewModel.onEvent(HomeEvent.SubTaskChanged(subTask))

        val current = viewModel.uiState.value.taskState.contentTask
        assertEquals(subTask, current)

    }

    @Test
    fun `when title is blank in saveNote then error is updated`() = runTest {

        onBefore()

        viewModel.toggleBottomSheetVisibility()

        val newTitle = ""
        val newDescription = "Description 2"

        viewModel.onEvent(HomeEvent.TitleNoteChanged(newTitle))
        viewModel.onEvent(HomeEvent.DescriptionNoteChanged(newDescription))
        viewModel.onEvent(event = HomeEvent.SaveNote)

        val error = viewModel.uiState.value.error
        val bottomSheetVisibility = viewModel.showSheet.value

        assertEquals("El título no puede estar vacío", error)
        coVerify(exactly = 0) { repository.makePostSaveNote(any()) }
        assertEquals(true, bottomSheetVisibility )

    }

    @Test
    fun `when title is not blank in saveNote then note is success`() = runTest{

        onBefore()

        viewModel.toggleBottomSheetVisibility()
        coEvery { repository.makePostSaveNote(any()) } returns Result.success("1ajkasl1")

        val newTitle = "Titulo 1"
        val newDescription = "Description 2"

        viewModel.onEvent(HomeEvent.TitleNoteChanged(newTitle))
        viewModel.onEvent(HomeEvent.DescriptionNoteChanged(newDescription))
        viewModel.onEvent(event = HomeEvent.SaveNote)

        val error = viewModel.uiState.value.error
        val bottomSheetVisibility = viewModel.showSheet.value

        assertEquals(null, error)
        coVerify(exactly = 1) { repository.makePostSaveNote(any()) }
        assertEquals(false, bottomSheetVisibility )
        assert(!viewModel.uiState.value.isSavingNote)
        assertEquals(FormState() , viewModel.uiState.value.noteState)
    }

    @Test
    fun `when the user opens the bottom sheet then the bottom sheet is visible with mode NOTE` () = runTest {

        onBefore()
        viewModel.visibilityExpanded()


        viewModel.openBottomSheet(SheetMode.NOTE)

        val expanded = viewModel.expanded.value
        val bottomSheetVisibility = viewModel.showSheet.value
        val mode = viewModel.uiState.value.modeSheet

        assertEquals(false, expanded)
        assertEquals(true, bottomSheetVisibility)
        assertEquals(SheetMode.NOTE, mode)

    }

    @Test
    fun `when the user opens the bottom sheet then the bottom sheet is visible with mode TASK`() = runTest {

        onBefore()
        viewModel.visibilityExpanded()


        viewModel.openBottomSheet(SheetMode.TASK)

        val expanded = viewModel.expanded.value
        val bottomSheetVisibility = viewModel.showSheet.value
        val mode = viewModel.uiState.value.modeSheet

        assertEquals(false, expanded)
        assertEquals(true, bottomSheetVisibility)
        assertEquals(SheetMode.TASK, mode)

    }

    @Test
    fun `When ViewModel init, notes are loaded from repository`() = runTest {

        val list = listOf(NoteModel(title = "Nota 1"), NoteModel(title = "Nota 2"))

        coEvery { repository.requestNotes(any()) } returns flowOf(list)
        coEvery { repository.currentUser() } returns mockk { every { uid } returns "user123" }

        viewModel.uiState.test {

            val iniState = awaitItem()
            assert(iniState.listNotes.isEmpty())

            //Trigger the observer. To test this, you must activate the observer in the ViewModel.
            //viewModel.observer()

            val stateWithNote = awaitItem()
            assertThat(stateWithNote.listNotes).isEqualTo(list)

            cancelAndIgnoreRemainingEvents()
        }
    }


}