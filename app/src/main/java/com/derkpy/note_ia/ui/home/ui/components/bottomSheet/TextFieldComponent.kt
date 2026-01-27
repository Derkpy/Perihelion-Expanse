package com.derkpy.note_ia.ui.home.ui.components.bottomSheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.derkpy.note_ia.ui.home.contract.HomeEvent
import com.derkpy.note_ia.ui.home.vm.HomeViewModel
import com.derkpy.note_ia.ui.theme.selectedField
import com.derkpy.note_ia.ui.theme.unselectedField
import com.derkpy.note_ia.ui.theme.white

@Composable
fun TextFieldComponent(viewModel: HomeViewModel, text: String, showTextField: SheetMode){

    val keyboardController = LocalSoftwareKeyboardController.current


    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = CircleShape)
            .padding(vertical = 10.dp)
            .height(50.dp),
        value = text,
        onValueChange = { newTex ->
            when (showTextField) {

                SheetMode.TITLE_NOTE ->
                    viewModel.onEvent(HomeEvent.TitleNoteChanged(newTex))

                SheetMode.DESCRIPTION_NOTE ->
                    viewModel.onEvent(HomeEvent.DescriptionNoteChanged(newTex))

                SheetMode.TITLE_TASK ->
                    viewModel.onEvent(HomeEvent.TitleTaskChanged(newTex))

                SheetMode.DESCRIPTION_TASK ->
                    viewModel.onEvent(HomeEvent.DescriptionTaskChanged(newTex))

                else -> { }
            } },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = selectedField,
            focusedContainerColor = unselectedField,
            focusedTextColor = white,
            unfocusedTextColor = white
        ),
        singleLine = true
        ,
        keyboardOptions =
            if (showTextField == SheetMode.DESCRIPTION_NOTE)
                KeyboardOptions(imeAction = ImeAction.Done) else
                KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }),
        placeholder = {

            when (showTextField) {

                SheetMode.TITLE_NOTE -> Text("Titulo de la nota", color = white)

                SheetMode.DESCRIPTION_NOTE -> Text("Descripción de la nota", color = white)

                SheetMode.TITLE_TASK -> Text("Título de la tarea", color = white)

                SheetMode.DESCRIPTION_TASK -> Text("Descripción de la tarea", color = white)

                else -> { }
            }
        }
    )
}