package com.example.homify
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.clickable

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)  // Make sure this matches your XML file
        }
        }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // Username Field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        // Forgot Password
        TextButton(
            onClick = { /* Handle forgot password */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Forgot Password?")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = { /* Handle login */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Login", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign up prompt
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an Account ")
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )) {
                        append("Signup")
                    }
                },
                modifier = Modifier.clickable { /* Handle sign up */ }
            )
        }
    }
}