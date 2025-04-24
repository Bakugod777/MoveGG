// Paquete del fragmento
package com.example.taller2.fragments

// Importaciones necesarias para el funcionamiento del fragmento
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.taller2.R
import com.example.taller2.activities.MainActivity

class EditarFragment : Fragment() {

    // Referencias a los elementos de la interfaz de usuario
    private lateinit var etNombreApellido: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etNumero: EditText
    private lateinit var btnGuardarCambios: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_editar, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar vistas
        etNombreApellido = view.findViewById(R.id.nombre_apellido_editar)
        etCorreo = view.findViewById(R.id.correo_editar)
        etNumero = view.findViewById(R.id.numero_editar)
        btnGuardarCambios = view.findViewById(R.id.cambios_editar)

        // Cargar datos actuales del usuario desde SharedPreferences
        cargarDatosUsuario()

        // Guardar cambios al hacer clic en el botón y volver al perfil
        btnGuardarCambios.setOnClickListener {
            guardarCambios()
            (activity as? MainActivity)?.abrirFragmento(PerfilFragment())
        }
    }


    private fun cargarDatosUsuario() {
        val sharedPreferences: SharedPreferences = requireContext()
            .getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        val correoActivo = sharedPreferences.getString("correo_activo", null)

        if (correoActivo != null) {
            val userPrefs = requireContext()
                .getSharedPreferences("Usuarios", Context.MODE_PRIVATE)

            val nombre = userPrefs.getString("$correoActivo-nombre", "No disponible") ?: "No disponible"
            val apellido = userPrefs.getString("$correoActivo-apellido", "No disponible") ?: "No disponible"
            val numero = userPrefs.getString("$correoActivo-numero", "No disponible") ?: "No disponible"

            etNombreApellido.setText("$nombre $apellido")
            etCorreo.setText(correoActivo)
            etCorreo.isEnabled = false // Evita que el usuario modifique el correo
            etNumero.setText(numero)
        }
    }


    private fun guardarCambios() {
        val sharedPreferences: SharedPreferences = requireContext()
            .getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        val correoActivo = sharedPreferences.getString("correo_activo", null)

        if (correoActivo != null) {
            val editor = requireContext()
                .getSharedPreferences("Usuarios", Context.MODE_PRIVATE)
                .edit()

            // Separa el nombre y el apellido a partir del texto ingresado
            val nombreApellido = etNombreApellido.text.toString().split(" ", limit = 2)
            val nombre = nombreApellido.getOrNull(0) ?: ""
            val apellido = nombreApellido.getOrNull(1) ?: ""

            // Guarda los valores en SharedPreferences
            editor.putString("$correoActivo-nombre", nombre)
            editor.putString("$correoActivo-apellido", apellido)
            editor.putString("$correoActivo-numero", etNumero.text.toString())
            editor.apply()
        }
    }
}


