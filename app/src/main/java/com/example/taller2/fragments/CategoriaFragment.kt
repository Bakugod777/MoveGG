package com.example.taller2.fragments

// Importaciones necesarias para funcionamiento de vistas y fragmentos
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.taller2.R

class CategoriaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_categoria, container, false)

        // Lista de categorías de ejemplo
        val categorias = listOf("Electrónica", "Ropa", "Hogar", "Deportes", "Accesorios")

        // Obtener referencia al ListView definido en el layout
        val listView: ListView = view.findViewById(R.id.lista_categorias)

        // Crear un adaptador para mostrar las categorías como elementos de lista
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categorias)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val categoriaSeleccionada = categorias[position]
            abrirProductoFragment(categoriaSeleccionada)
        }

        return view
    }


    private fun abrirProductoFragment(categoria: String) {
        // Crear una nueva instancia del fragmento de productos
        val fragmentoProducto = ProductoFragment()

        // Pasar la categoría como argumento al nuevo fragmento
        val bundle = Bundle()
        bundle.putString("categoria", categoria)
        fragmentoProducto.arguments = bundle

        // Reemplazar el fragmento actual por el fragmento de productos
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentoProducto)
            .addToBackStack(null)  // Permite volver atrás con el botón de retroceso
            .commit()
    }
}

