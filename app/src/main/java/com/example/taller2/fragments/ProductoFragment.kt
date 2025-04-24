// Paquete donde se encuentra el fragmento
package com.example.taller2.fragments

// Importación de clases necesarias para inflar vistas, manejar widgets y trabajar con fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.taller2.R
import com.example.taller2.fragments.LoginFragment
import android.content.Context



// Fragmento que muestra una lista de productos dinámicamente en base a su categoría
class ProductoFragment : Fragment() {

    // Lista de productos estática con distintos tipos de categorías
    private val productos = listOf(
        Producto("Lamborghini Huracan", "Deportes", R.drawable.lamborghini, "Motor V8, diseño aerodinámico", "$1,200,000", 5),
        Producto("BMW M3", "Deportes", R.drawable.bmw_m3, "Gran velocidad, super confort", "$300,000", 3),
        Producto("Televisor 4K", "Electrónica", R.drawable.televisor, "Resolución 4K UHD, HDR", "$2,000,000", 10),
        Producto("Laptop Gamer", "Electrónica", R.drawable.laptop, "RTX 4090, 32GB RAM", "$8,500,000", 7),
        Producto("Camiseta Nike", "Ropa", R.drawable.camiseta, "Tela transpirable, diseño moderno", "$120,000", 15),
        Producto("Sofá de cuero", "Hogar", R.drawable.sofa, "Cuero premium, 3 plazas", "$2,500,000", 2)
    )

    // Se ejecuta cuando se crea la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout asociado al fragmento (fragment_producto.xml)
        val view = inflater.inflate(R.layout.fragment_producto, container, false)

        // Obtiene el contenedor de productos donde se colocarán las tarjetas dinámicamente
        val contenedorProductos: LinearLayout = view.findViewById(R.id.contenedor_productos)

        // Se obtiene la categoría seleccionada desde el fragmento anterior (por defecto es "Todos")
        val categoriaSeleccionada = arguments?.getString("categoria") ?: "Todos"

        // Se filtran los productos por la categoría seleccionada
        val productosFiltrados = if (categoriaSeleccionada == "Todos") {
            productos
        } else {
            productos.filter { it.categoria == categoriaSeleccionada }
        }

        // Se limpia el contenedor de productos antes de agregar nuevos
        contenedorProductos.removeAllViews()

        // Si no hay productos en la categoría, se muestra un mensaje
        if (productosFiltrados.isEmpty()) {
            val mensaje = TextView(requireContext())
            mensaje.text = "No hay productos en esta categoría"
            contenedorProductos.addView(mensaje)
        } else {
            // Si hay productos, se agregan al contenedor usando tarjetas (CardView)
            for (producto in productosFiltrados) {
                contenedorProductos.addView(crearCardProducto(producto))
            }
        }

        return view
    }

    // Función que crea la tarjeta visual de un producto usando un layout personalizado
    private fun crearCardProducto(producto: Producto): View {
        // Infla el layout de la tarjeta de producto (item_producto.xml)
        val cardView = layoutInflater.inflate(R.layout.item_producto, null) as CardView

        // Se enlazan los elementos visuales dentro de la tarjeta
        val imgProducto: ImageView = cardView.findViewById(R.id.img_producto)
        val txtNombre: TextView = cardView.findViewById(R.id.txt_nombre)
        val txtDescripcion: TextView = cardView.findViewById(R.id.txt_descripcion)
        val txtPrecio: TextView = cardView.findViewById(R.id.txt_precio)
        val txtCantidad: TextView = cardView.findViewById(R.id.txt_cantidad)
        val btnAgregar: Button = cardView.findViewById(R.id.btn_agregar)

        // Se asignan los valores del producto a cada vista
        imgProducto.setImageResource(producto.imagen)
        txtNombre.text = producto.nombre
        txtDescripcion.text = producto.descripcion
        txtPrecio.text = "Precio: ${producto.precio}"
        txtCantidad.text = "Cantidad: ${producto.cantidad}"

        // Acción al hacer clic en el botón "Agregar": se envía el producto al carrito
        btnAgregar.setOnClickListener {
            if (!haySesionActiva()) {
                // Redirigir al LoginFragment si no hay sesión activa
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .addToBackStack(null)
                    .commit()
            } else {
                // Agregar producto al carrito si hay sesión
                CarritoManager.agregarProducto(producto)
            }
        }


        // Se definen márgenes entre tarjetas para una mejor visualización
        val params = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(16, 8, 16, 8) // Margen izquierdo, superior, derecho, inferior
        cardView.layoutParams = params

        return cardView
    }

    private fun haySesionActiva(): Boolean {
        val sharedPreferences = requireContext().getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        return sharedPreferences.getString("correo_activo", null) != null
    }

}

// Clase de datos que representa un producto
data class Producto(
    val nombre: String,
    val categoria: String,
    val imagen: Int,
    val descripcion: String,
    val precio: String,
    var cantidad: Int
)

