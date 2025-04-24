package com.example.taller2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taller2.R

// Fragmento que representa el carrito de compras
class CarritoFragment : Fragment() {

    // Método que infla el layout del fragmento y configura la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carrito, container, false)

        // Referencias a los elementos de la interfaz
        val contenedorCarrito: LinearLayout = view.findViewById(R.id.contenedor_carrito)
        val txtTotal: TextView = view.findViewById(R.id.txt_total)
        val btnPagar: Button = view.findViewById(R.id.btn_pagar)

        // Cargar los productos en el carrito al iniciar la vista
        actualizarCarrito(contenedorCarrito, txtTotal)

        // Acción del botón "Pagar"
        btnPagar.setOnClickListener {
            if (CarritoManager.obtenerProductos().isNotEmpty()) {
                // Si hay productos, vaciar el carrito y actualizar la interfaz
                CarritoManager.limpiarCarrito()
                actualizarCarrito(contenedorCarrito, txtTotal)
                Toast.makeText(requireContext(), "✅ ¡Compra realizada con éxito!", Toast.LENGTH_LONG).show()
            } else {
                // Si está vacío, mostrar mensaje de advertencia
                Toast.makeText(requireContext(), "⚠️ El carrito está vacío", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    // Método que actualiza visualmente los productos del carrito en el contenedor
    private fun actualizarCarrito(contenedorCarrito: LinearLayout, txtTotal: TextView) {
        // Limpiar el contenedor antes de agregar los productos
        contenedorCarrito.removeAllViews()
        var total = 0.0 // Acumulador del total

        // Iterar por cada producto y su cantidad en el carrito
        for ((producto, cantidad) in CarritoManager.obtenerProductos()) {
            // Inflar el layout individual para cada producto
            val itemView = layoutInflater.inflate(R.layout.item_carrito, null)

            // Referencias a los elementos de cada item
            val imgProducto: ImageView = itemView.findViewById(R.id.img_producto_carrito)
            val txtNombre: TextView = itemView.findViewById(R.id.txt_nombre_carrito)
            val txtCantidad: TextView = itemView.findViewById(R.id.txt_cantidad_carrito)
            val txtPrecio: TextView = itemView.findViewById(R.id.txt_precio_carrito)
            val txtSubtotal: TextView = itemView.findViewById(R.id.txt_subtotal_carrito)
            val btnEliminar: Button = itemView.findViewById(R.id.btn_eliminar)

            // Asignar la imagen y nombre del producto
            imgProducto.setImageResource(producto.imagen)
            txtNombre.text = producto.nombre
            txtCantidad.text = "Cantidad: $cantidad"

            val precioLimpio = producto.precio.replace("[^0-9.]".toRegex(), "").toDoubleOrNull() ?: 0.0
            txtPrecio.text = "Precio: $${String.format("%.2f", precioLimpio)}"

            // Calcular y mostrar el subtotal por producto
            val subtotal = precioLimpio * cantidad
            txtSubtotal.text = "Subtotal: $${String.format("%.2f", subtotal)}"
            total += subtotal // Acumular al total

            // Acción del botón "Eliminar"
            btnEliminar.setOnClickListener {
                CarritoManager.eliminarProducto(producto) // Eliminar el producto
                actualizarCarrito(contenedorCarrito, txtTotal) // Refrescar vista
            }

            // Agregar el item al contenedor visual del carrito
            contenedorCarrito.addView(itemView)
        }

        // Mostrar el total general del carrito
        txtTotal.text = "Total: $${String.format("%.2f", total)}"
    }
}

// Objeto que gestiona los productos en el carrito
object CarritoManager {

    private val carrito = mutableMapOf<Producto, Int>()

    // Agregar un producto al carrito (incrementa cantidad si ya existe)
    fun agregarProducto(producto: Producto) {
        carrito[producto] = (carrito[producto] ?: 0) + 1
    }

    // Eliminar un producto del carrito (decrementa cantidad o lo quita)
    fun eliminarProducto(producto: Producto) {
        carrito[producto]?.let {
            if (it > 1) carrito[producto] = it - 1 else carrito.remove(producto)
        }
    }

    // Obtener todos los productos en el carrito con sus cantidades
    fun obtenerProductos(): Map<Producto, Int> = carrito

    // Limpiar completamente el carrito
    fun limpiarCarrito() {
        carrito.clear()
    }
}
