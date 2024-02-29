package org.altbeacon.beaconreference

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.altbeacon.api.ApiClient
import android.util.Log
import com.google.gson.JsonObject
import android.widget.TextView
import android.view.View

class LoginActivity : Activity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        errorTextView = findViewById(R.id.errorTextView)
        errorTextView.visibility = View.GONE

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            loginUser(username, password)
        }
    }

    private fun loginUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Realiza la llamada a la API para el inicio de sesión
                val response = ApiClient.login(this@LoginActivity, username, password)

                // Verifica si la respuesta es exitosa
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    // Verifica si la respuesta contiene un token
                    val token = responseBody?.get("access_token")?.asString

                    if (!token.isNullOrBlank()) {

                        SharedPreferencesManager.saveTokenToSharedPreferences(this@LoginActivity, token)

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    showError("Credenciales inválidas")
                }
            } catch (e: Exception) {
                showError("Error del servidor. Preguntar a los desarrolladores")
            }
        }
    }


    private fun showError(message: String) {
        runOnUiThread {
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = message
        }
    }
}