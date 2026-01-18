package com.derkpy.note_ia.ui.home.ui.components.bottomSheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
fun ContainerSubTasksComponent(viewModel: HomeViewModel, text: String){

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
     modifier = Modifier
         .clip(shape = RoundedCornerShape(20.dp))
         .fillMaxWidth()
         .padding(10.dp),
        value = text,
        onValueChange = { tasks ->
                viewModel.onEvent(HomeEvent.SubTaskChanged(tasks))
            },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = unselectedField,
            focusedContainerColor = selectedField,
            focusedTextColor = white,
            unfocusedTextColor = white
        ),
        minLines = 3,
        maxLines = 5,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )

}