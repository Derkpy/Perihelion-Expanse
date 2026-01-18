package com.derkpy.note_ia.ui.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.derkpy.note_ia.domain.model.NoteModel
import com.derkpy.note_ia.ui.theme.black
import com.derkpy.note_ia.ui.theme.white
import com.derkpy.note_ia.utils.Utils.toMexicanDate

@Composable
fun NoteItem(noteModel: NoteModel) {
    Card(modifier = Modifier
        .padding(6.dp)
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = white
        ),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            TextCard(noteModel.title)
            TextCard(noteModel.date.toMexicanDate())
        }
    }
}

@Composable
fun TextCard(text: String) {

    Text(text = text,
        color = black,
        fontWeight = FontWeight.Bold,
        maxLines = 2
    )
}