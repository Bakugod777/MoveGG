package com.example.taller2.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.taller2.R
import com.example.taller2.fragments.LoginFragment

class RecuContraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recucontra)

        // Referencias a los elementos de la interfaz gráfica
        val etCorreo = findViewById<EditText>(R.id.correo2)
        val btnEnviarSolicitud = findViewById<Button>(R.id.soli)

        // Configurar el botón para enviar la solicitud de recuperación
        btnEnviarSolicitud.setOnClickListener {
            val correo = etCorreo.text.toString().trim()

            // Validación: Verifica que el campo de correo no esté vacío
            if (correo.isEmpty()) {
                Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica si el correo está registrado
            if (verificarCorreoRegistrado(correo)) {
                Toast.makeText(this, "Se envió la solicitud correctamente", Toast.LENGTH_SHORT).show()
                regresarALogin()
            } else {
                Toast.makeText(this, "El correo no está registrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Verifica si el correo está registrado en SharedPreferences.//
    private fun verificarCorreoRegistrado(correo: String): Boolean {
        val sharedPref = getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
        return sharedPref.contains("$correo-contraseña")
    }

    //Redirige al usuario a la pantalla de inicio de sesión después de solicitar la recuperación.//
    private fun regresarALogin() {
        val intent = Intent(this, LoginFragment::class.java)
        startActivity(intent)
        finish()
    }
}


