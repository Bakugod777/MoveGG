package com.example.taller2.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.taller2.R
import com.example.taller2.fragments.LoginFragment


class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Referencias a los elementos de la interfaz gráfica
        val etCorreo = findViewById<EditText>(R.id.correo)
        val etContraseña = findViewById<EditText>(R.id.contra)
        val etNombre = findViewById<EditText>(R.id.nombre)
        val etApellido = findViewById<EditText>(R.id.apellido)
        val etNumero = findViewById<EditText>(R.id.numero)
        val btnRegistrar = findViewById<Button>(R.id.acabarregistro)

        // Configurar el botón de registro
        btnRegistrar.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contraseña = etContraseña.text.toString()
            val nombre = etNombre.text.toString()
            val apellido = etApellido.text.toString()
            val numero = etNumero.text.toString()

            // Validación: Verifica que ningún campo esté vacío
            if (correo.isEmpty() || contraseña.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || numero.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Almacena los datos en SharedPreferences
            val sharedPref = getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("$correo-contraseña", contraseña)
                putString("$correo-nombre", nombre)
                putString("$correo-apellido", apellido)
                putString("$correo-numero", numero)
                apply()
            }

            // Muestra un mensaje de éxito
            Toast.makeText(this, "Registro exitoso. Inicia sesión.", Toast.LENGTH_SHORT).show()

            // Redirige a la pantalla de inicio de sesión
            val intent = Intent(this, LoginFragment::class.java)
            startActivity(intent)
            finish() // Finaliza esta actividad para que el usuario no pueda volver con el botón de retroceso
        }
    }
}



