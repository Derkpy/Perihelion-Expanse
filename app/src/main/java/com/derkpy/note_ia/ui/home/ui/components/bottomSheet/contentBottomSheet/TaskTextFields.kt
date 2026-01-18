package com.derkpy.note_ia.ui.home.ui.components.bottomSheet.contentBottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.ContainerSubTasksComponent
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.SheetMode
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.TextFieldComponent
import com.derkpy.note_ia.ui.home.vm.HomeViewModel

@Composable
fun TaskTextFields(viewModel: HomeViewModel){

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        TextFieldComponent(viewModel, state.taskState.title, SheetMode.TITLE_TASK)

        Spacer(modifier = Modifier.height(20.dp))

        TextFieldComponent(viewModel, state.taskState.description, SheetMode.DESCRIPTION_TASK)

        Spacer(modifier = Modifier.height(20.dp))

        ContainerSubTasksComponent(viewModel, state.taskState.contentTask)


    }

}