package com.example.taller2.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.taller2.R
import com.example.taller2.activities.MainActivity
import com.example.taller2.activities.RegistroActivity
import com.example.taller2.activities.RecuContraActivity

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout del fragmento
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etCorreo = view.findViewById<EditText>(R.id.et_username)
        val etContraseña = view.findViewById<EditText>(R.id.et_contraseña)
        val btnIngresar = view.findViewById<Button>(R.id.ingresa)
        val tvRegistro = view.findViewById<TextView>(R.id.registro)
        val tvRecuperar = view.findViewById<TextView>(R.id.recuperar)

        btnIngresar.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contraseña = etContraseña.text.toString()

            if (correo.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese su correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (validarUsuario(correo, contraseña)) {
                guardarSesionActiva(correo)
                Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java).apply {
                    putExtra("correo", correo)
                }
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegistro.setOnClickListener {
            startActivity(Intent(requireContext(), RegistroActivity::class.java))
        }

        tvRecuperar.setOnClickListener {
            startActivity(Intent(requireContext(), RecuContraActivity::class.java))
        }
    }

    private fun validarUsuario(correo: String, contraseña: String): Boolean {
        val sharedPref = requireContext().getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
        val contraseñaGuardada = sharedPref.getString("$correo-contraseña", null)
        return contraseñaGuardada == contraseña
    }

    private fun guardarSesionActiva(correo: String) {
        val sharedPref = requireContext().getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("correo_activo", correo)
            apply()
        }
    }
}




