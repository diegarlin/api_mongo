package org.altbeacon.beaconreference

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.altbeacon.api.ApiClient
import android.util.Log

class LoginActivity : Activity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Aquí llama al método para realizar el inicio de sesión
            loginUser(username, password)
        }
    }

    private fun loginUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // Realiza la llamada a la API para el inicio de sesión
            val token = ApiClient.login(this@LoginActivity,username, password)

            // Verifica si el inicio de sesión fue exitoso
            if (token != null) {
                // Guarda el token en SharedPreferences u otro almacenamiento seguro
                Log.d("TOKEN", token.toString())
                saveTokenToSharedPreferences(token)

                // Abre la actividad principal después del inicio de sesión exitoso
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish() // Cierra la actividad de inicio de sesión para evitar volver a ella con el botón de retroceso
            } else {
                // Muestra un mensaje de error en caso de inicio de sesión fallido
                // Esto puede ser en el hilo principal ya que se trata de la interfaz de usuario
                runOnUiThread {
                    // Mostrar un Toast o un diálogo de error
                    // Toast.makeText(this@LoginActivity, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show()
                    // O mostrar el mensaje de error en un TextView en la interfaz de usuario
                }
            }
        }
    }

    private fun saveTokenToSharedPreferences(token: String) {
        // Aquí guarda el token en SharedPreferences u otro almacenamiento seguro
        // Puedes usar SharedPreferences para almacenar el token de forma segura
    }
}
