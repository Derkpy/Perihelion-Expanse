package com.derkpy.note_ia.ui.home.ui.components.bottomSheet.contentBottomSheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottonComponent(onSave : () -> Unit){

    Button(

        modifier = Modifier.fillMaxWidth(),

        onClick = {
            println("EL USUARIO HIZO CLICK")
            onSave()

        }

    ) {

        Text(text = "Guardar")

    }




}