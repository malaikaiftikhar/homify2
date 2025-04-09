package com.example.homify
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signupButton: Button
    private lateinit var loginRedirect: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize views
        fullNameEditText = findViewById(R.id.full_name)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirm_password)
        signupButton = findViewById(R.id.signup_button)
        loginRedirect = findViewById(R.id.login_redirect)

        // Set onClick listeners
        signupButton.setOnClickListener {
            handleSignup()
        }

        loginRedirect.setOnClickListener {
            redirectToLogin()
        }
    }

    private fun handleSignup() {
        val fullName = fullNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        // Check for empty fields and password match
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            // Handle empty fields (e.g., show a toast)
            return
        }

        if (password != confirmPassword) {
            // Handle password mismatch (e.g., show a toast)
            return
        }

        // Proceed with sign up logic (e.g., API call, saving data)
        // Example: navigate to the home page after successful signup
        // val intent = Intent(this, HomeActivity::class.java)
        // startActivity(intent)
    }

    private fun redirectToLogin() {
        // Redirect to login activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
