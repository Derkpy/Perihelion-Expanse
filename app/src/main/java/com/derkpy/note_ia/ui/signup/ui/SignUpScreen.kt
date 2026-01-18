package com.derkpy.note_ia.ui.signup.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.derkpy.note_ia.R
import com.derkpy.note_ia.ui.signup.vm.SingUpViewModel
import com.derkpy.note_ia.ui.theme.black
import com.derkpy.note_ia.ui.theme.primaryTwoDark
import com.derkpy.note_ia.ui.theme.secondaryOneDark
import com.derkpy.note_ia.ui.theme.selectedField
import com.derkpy.note_ia.ui.theme.tertiaryOneDark
import com.derkpy.note_ia.ui.theme.unselectedField
import com.derkpy.note_ia.ui.theme.white
import org.koin.androidx.compose.koinViewModel

@Composable
fun SingUpScreen(
    viewModel : SingUpViewModel = koinViewModel(),
    navigateToHome : () -> Unit
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navigateToHome()
            viewModel.onNavigationDone()
        }
    }

    SingUpBody(viewModel)
}

@Composable
fun SingUpBody(viewModel: SingUpViewModel){

    val email: String by viewModel.email.collectAsStateWithLifecycle()
    val password: String by viewModel.password.collectAsStateWithLifecycle()
    val singUpEnable: Boolean by viewModel.singUpEnable.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryTwoDark),
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
                tint = white,
                modifier = Modifier.padding(top = 35.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Image(
            modifier = Modifier.scale(scaleX = 0.5f, scaleY = 0.5f),
            painter = painterResource(R.drawable.logo_noteia),
            contentDescription = "Logo"
        )

        Text(
            text = "User o Email",
            color = secondaryOneDark
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextFieldEmail(email) { viewModel.onSingUpChanged(it, password) }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Password",
            color = secondaryOneDark
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextFieldPassword(password) { viewModel.onSingUpChanged(email, it) }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            singUpEnable = singUpEnable && !uiState.isLoading,
            onSinUpSelected = { viewModel.onSingUpSelected() }
        )


        Spacer(modifier = Modifier.weight(1f))
    }
        if (uiState.isLoading) {
            CircularProgressIndicator(color = tertiaryOneDark)
        }
    }
}

@Composable
fun Button(singUpEnable: Boolean, onSinUpSelected: () -> Unit){

    Button(
        onClick = onSinUpSelected,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = tertiaryOneDark,
            disabledContainerColor = tertiaryOneDark.copy(alpha = 0.5f)
        ),
        enabled = singUpEnable
    ) {
        Text(
            "Sing Up",
            color = black,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun TextFieldEmail(email: String, onTextChanged: (String) -> Unit){

    TextField(
        modifier = Modifier.fillMaxWidth()
            .clip(shape = CircleShape),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = unselectedField,
            focusedContainerColor = selectedField
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        value = email,
        onValueChange = onTextChanged
    )
}

@Composable
fun TextFieldPassword(password: String, onTextChanged: (String) -> Unit){

    TextField(modifier = Modifier.fillMaxWidth()
        .clip(shape = CircleShape),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = unselectedField,
            focusedContainerColor = selectedField),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        value = password,
        onValueChange = onTextChanged
    )
}