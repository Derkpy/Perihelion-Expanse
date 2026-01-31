package com.derkpy.note_ia.ui.home.ui

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.derkpy.note_ia.R
import com.derkpy.note_ia.ui.home.vm.HomeViewModel
import com.derkpy.note_ia.ui.theme.primaryOneLigth
import com.derkpy.note_ia.ui.theme.primaryTwoLigth
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.derkpy.note_ia.ui.home.contract.HomeEvent
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.ButtomSheetComponent
import com.derkpy.note_ia.ui.home.ui.components.DividerLineWithText
import com.derkpy.note_ia.ui.home.ui.components.NoteItem
import com.derkpy.note_ia.ui.home.ui.components.SpeedDialFAB
import com.derkpy.note_ia.ui.home.ui.components.TaskItem
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.SheetMode
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.contentBottomSheet.NoteTextFields
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.contentBottomSheet.TaskTextFields
import com.derkpy.note_ia.ui.theme.white

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel(), navigateToDetail: (String) -> Unit){

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("Permissions", "Notificaciones permitidas")
        } else {
            Log.d("Permissions", "Notificaciones denegadas")
        }
    }
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    HomeContent(viewModel, navigateToDetail)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(viewModel: HomeViewModel, navigateToDetail: (String) -> Unit) {

    val sheetState = rememberModalBottomSheetState()
    val showSheet = viewModel.showSheet.collectAsState().value
    val state by viewModel.uiState.collectAsStateWithLifecycle()


    Scaffold(
        floatingActionButton = {

            SpeedDialFAB(viewModel)

        }) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(primaryOneLigth),
                contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = painterResource(R.drawable.ic_miscellaneous_services_24),
                        contentDescription = "",
                        tint = primaryTwoLigth,
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .clickable() { viewModel.closeSession() }
                    )
                }

                Column(modifier = Modifier.padding(innerPadding)) {

                    DividerLineWithText("NOTAS")

                    LazyColumn(
                        modifier = Modifier
                            .weight(.5f)
                            .fillMaxWidth()
                    ) {
                        items(state.listNotes) { note ->
                            NoteItem(noteModel = note)
                        }
                    }

                    DividerLineWithText("TAREAS")

                    LazyHorizontalGrid(
                        modifier = Modifier
                            .weight(.45f)
                            .fillMaxWidth(),
                        rows = GridCells.Adaptive(minSize = 120.dp),
                        contentPadding = PaddingValues(9.dp),
                        verticalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        items(state.listTasks){ task ->
                            TaskItem(task = task, onClick = { navigateToDetail(task.id) })
                        }
                    }
                    Spacer(modifier = Modifier.weight(.05f))
                }
            }
        }

        if (showSheet) {

            ModalBottomSheet(

                onDismissRequest = { viewModel.toggleBottomSheetVisibility() },
                sheetState = sheetState,
                containerColor = white

            ){
                ButtomSheetComponent(
                    state.modeSheet,
                    onSave = {
                       if (state.modeSheet == SheetMode.NOTE) {
                           viewModel.onEvent(HomeEvent.SaveNote)
                       }
                       else {
                           viewModel.onEvent(HomeEvent.SaveTask)
                       }
                            },
                    generateContent = {
                       viewModel.onEvent(HomeEvent.GenerateSubtasks)
                    })
                {
                    if (state.modeSheet == SheetMode.NOTE) {
                       NoteTextFields(viewModel)
                    } else {
                       TaskTextFields(viewModel)
                    }
               }
            }
        }
    }
}