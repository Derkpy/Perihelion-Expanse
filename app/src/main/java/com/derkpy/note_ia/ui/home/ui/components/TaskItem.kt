package com.derkpy.note_ia.ui.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.derkpy.note_ia.domain.model.TaskModel
import com.derkpy.note_ia.ui.theme.black
import com.derkpy.note_ia.ui.theme.primaryOneDark
import com.derkpy.note_ia.ui.theme.white
import com.derkpy.note_ia.utils.Utils.toMexicanDate

@Composable
fun TaskItem(task: TaskModel, onClick: () -> Unit){

    Card(modifier = Modifier
        .padding(6.dp)
        .clip(RoundedCornerShape(8.dp)),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

    ){

        Box(modifier = Modifier
            .aspectRatio(1f)
            .background(primaryOneDark)
            .padding(10.dp)
        ){
            Column(modifier = Modifier.fillMaxSize()) {

                TextTitle(task.title)

                Spacer(modifier = Modifier.height(10.dp))

                TextDescription(task.description)

                Spacer(modifier = Modifier.height(10.dp))

                TextCard(task.date.toMexicanDate())
            }
        }
    }
}

@Composable
fun TextTitle(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        fontSize = 20.sp,
        color = white,
    )
}

@Composable
fun TextDescription(text: String) {

    Text(text = text,
        color = black,
        fontWeight = FontWeight.Bold,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}