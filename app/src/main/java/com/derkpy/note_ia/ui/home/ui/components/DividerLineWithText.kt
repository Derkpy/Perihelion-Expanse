package com.derkpy.note_ia.ui.home.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.derkpy.note_ia.ui.theme.primaryTwoLigth

@Composable
fun DividerLineWithText (text: String) {
    Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))

        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = primaryTwoLigth
        )

        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}