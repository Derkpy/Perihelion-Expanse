package com.derkpy.note_ia.ui.detail.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.derkpy.note_ia.R
import com.derkpy.note_ia.domain.model.SubTaskModel
import com.derkpy.note_ia.ui.detail.contract.DetailEvent
import com.derkpy.note_ia.ui.detail.vm.DetailViewModel
import com.derkpy.note_ia.ui.theme.black
import com.derkpy.note_ia.ui.theme.primaryOneLigth
import com.derkpy.note_ia.ui.theme.primaryTwoLigth
import com.derkpy.note_ia.ui.theme.white
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(viewModel: DetailViewModel = koinViewModel()) {
    DetailContent(viewModel)
}
@Composable
fun DetailContent(viewModel: DetailViewModel) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryOneLigth) // Color de fondo global
    ) {
        Crossfade(
            targetState = state.isLoading,
            label = "loading_transition",
            animationSpec = tween(500),
            modifier = Modifier.fillMaxSize()
        ) { isLoading ->

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = black)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp, bottom = 20.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back_24),
                            contentDescription = "Back",
                            tint = primaryTwoLigth,
                            modifier = Modifier
                                .clickable { /* viewModel.onBack() */ }
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Title()

                        Spacer(modifier = Modifier.weight(1f))

                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TitleTask(
                        text = state.task.title,
                        enableTextField = state.enableTextField,
                        viewModel = viewModel)

                    Spacer(modifier = Modifier.height(10.dp))

                    DescriptionTask(
                        text = state.task.description,
                        enableTextField = state.enableTextField,
                        viewModel = viewModel
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    SubTasks(
                        subTasks = state.task.content,
                        enableTextField = state.enableTextField,
                        viewModel = viewModel
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Buttons(viewModel)
                }
            }
        }
    }
}

@Composable
fun Buttons(viewModel: DetailViewModel) {

    Row {

        Button(

            onClick = {

            viewModel.onEvent(DetailEvent.StateTextFieldEnabled(true))

        }) {
            Text(text = "Editar")
        }

        Button(
            onClick = {

            viewModel.onEvent(DetailEvent.EditTask)

        }) {
            Text(text = "Guardar")
        }
    }
}

@Composable
fun Title(){
    Text(
        modifier = Modifier.padding(vertical = 2.dp),
        text = "DETALLES DE LA TAREA",
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 20.sp,
        color = white
    )
}


@Composable
fun TitleTask(text: String, enableTextField: Boolean, viewModel: DetailViewModel){

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        modifier = Modifier
            .padding(vertical = 5.dp)
        ,
        onValueChange = { title ->
            viewModel.onEvent(DetailEvent.TitleChanged(title = title))
        },
        maxLines = 2,
        enabled = enableTextField
    )
}

@Composable
fun DescriptionTask(text: String, enableTextField: Boolean, viewModel: DetailViewModel){

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        modifier = Modifier
            .padding(vertical = 5.dp),
        onValueChange = { description ->
            viewModel.onEvent(DetailEvent.DescriptionChanged(description = description))
        },
        maxLines = 2,
        enabled = enableTextField
    )
}

@Composable
fun SubTasks(subTasks: List<SubTaskModel>, enableTextField: Boolean, viewModel: DetailViewModel){

    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        subTasks.forEach { subtask ->
            TextField(
                value = subtask.name,
                modifier = Modifier
                    .padding(vertical = 5.dp),
                onValueChange = { text ->

                    val updateTask = subtask.copy(name = text)

                    viewModel.onEvent(DetailEvent.SubTaskChanged(subTask = updateTask))

                },
                enabled = enableTextField,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = white,
                    unfocusedContainerColor = white
                )
            )
        }
    }
}

