package com.derkpy.note_ia.ui.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
            .background(primaryOneLigth),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {

                Icon(
                    painter = painterResource(R.drawable.ic_back_24),
                    contentDescription = "",
                    tint = primaryTwoLigth,
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .clickable() {  }
                )

                Spacer(modifier = Modifier.weight(1f))

            }

            Title()

            TitleTask(state.task.title, state.enableTextField, viewModel)

            DescriptionTask(state.task.description, state.enableTextField, viewModel)

            SubTasks(state.task.content, state.enableTextField, viewModel)

            Buttons()

        }

    }
}

@Composable
fun Buttons() {

    Row {

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Editar")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Eliminar")
        }
    }

}

@Composable
fun Title(){
    Text(
        text = "TAREA",
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 20.sp,
        color = white,
    )
}


@Composable
fun TitleTask(text: String, enableTextField: Boolean, viewModel: DetailViewModel){

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        modifier = Modifier,
        onValueChange = { title ->
            viewModel.onEvent(DetailEvent.TitleChanged(title = title))
        },

        label = { Text(viewModel.noteId) },

        enabled = enableTextField,

        )
}

@Composable
fun DescriptionTask(text: String, enableTextField: Boolean, viewModel: DetailViewModel){

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        modifier = Modifier,
        onValueChange = { description ->
            viewModel.onEvent(DetailEvent.DescriptionChanged(description = description))
        },
        enabled = enableTextField,

        )
}

@Composable
fun SubTasks(subTasks: List<SubTaskModel>, enableTextField: Boolean, viewModel: DetailViewModel){



}

