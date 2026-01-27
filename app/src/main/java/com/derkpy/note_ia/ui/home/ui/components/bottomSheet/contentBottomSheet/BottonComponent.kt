package com.derkpy.note_ia.ui.home.ui.components.bottomSheet.contentBottomSheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonComponent(onSave : () -> Unit, generateContent : () -> Unit){

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
    ){
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                onSave()
            })
        {
            Text(text = "Guardar")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                generateContent()
            }
        ) {
            Text(text = "Autogenerar")
        }

    }
}