package com.derkpy.note_ia.ui.home.ui.components.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.contentBottomSheet.BottonComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetComponent(
                         mode : SheetMode? ,
                         onSave : () -> Unit,
                         content : @Composable () -> Unit
){
    /*
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ModalBottomSheet(
        onDismissRequest = { viewModel.toggleBottomSheetVisibility() },
        sheetState = sheetState
        )
    {*/
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            Text(
                text = if (mode == SheetMode.NOTE) "Nueva Nota" else "Nueva Tarea",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            content()

            /*Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        viewModel.toggleBottomSheetVisibility()
                    }
                }
            }) {
                Text("Ocultar")
            }*/

            Spacer(modifier = Modifier.height(30.dp))

            BottonComponent(onSave = onSave)

            Spacer(modifier = Modifier.height(30.dp))
        }

}

