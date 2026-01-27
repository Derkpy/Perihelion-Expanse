package com.derkpy.note_ia.ui.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.derkpy.note_ia.R
import com.derkpy.note_ia.ui.login.vm.LoginViewModel
import com.derkpy.note_ia.ui.theme.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToHome: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navigateToHome()
            viewModel.onNavigationDone()
        }
    }

    Login(viewModel = viewModel)
}


@Composable
fun Login(viewModel: LoginViewModel) {
    val email: String by viewModel.email.collectAsStateWithLifecycle()
    val password: String by viewModel.password.collectAsStateWithLifecycle()
    val loginEnable: Boolean by viewModel.loginEnable.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryTwoDark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_back_24),
                    contentDescription = "",
                    tint = white,
                    modifier = Modifier.padding(top = 40.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Image(
                modifier = Modifier.scale(scaleX = 0.5f, scaleY = 0.5f),
                painter = painterResource(R.drawable.logo_noteia),
                contentDescription = "Logo"
            )
            Text(text = "User o Email", color = secondaryOneDark)
            Spacer(modifier = Modifier.height(12.dp))
            TextFieldEmail(email) { viewModel.onLoginChanged(it, password) }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Password", color = secondaryOneDark)
            Spacer(modifier = Modifier.height(12.dp))
            TextFieldPassword(password) { viewModel.onLoginChanged(email, it) }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                loginEnable = loginEnable && !uiState.isLoading,
                onLoginSelected = { viewModel.onLoginWithEmailAndPassword() }
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = tertiaryOneDark
            )
        }
    }
}

@Composable
fun Button(loginEnable: Boolean, onLoginSelected: () -> Unit){
    Button(
        onClick = onLoginSelected,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = tertiaryOneDark,
            disabledContainerColor = tertiaryOneDark.copy(alpha = 0.5f)
        ),
        enabled = loginEnable
    ) {
        Text("Log in", color = black, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TextFieldEmail(email: String, onTextChanged: (String) -> Unit){

    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = CircleShape),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = unselectedField,
            focusedContainerColor = selectedField
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Email),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down)}
        ),
        singleLine = true,
        maxLines = 1,
        value = email,
        onValueChange = onTextChanged,
    )
}

@Composable
fun TextFieldPassword(password: String, onTextChanged: (String) -> Unit){

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = CircleShape),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = unselectedField,
            focusedContainerColor = selectedField
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                }),
        singleLine = true,
        maxLines = 1,
        value = password,
        onValueChange = onTextChanged
    )
}