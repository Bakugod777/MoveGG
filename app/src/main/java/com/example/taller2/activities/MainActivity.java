package com.example.taller2.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.taller2.fragments.CarritoFragment;
import com.example.taller2.fragments.CategoriaFragment;
import com.example.taller2.fragments.EditarFragment;
import com.example.taller2.fragments.PerfilFragment;
import com.example.taller2.fragments.ProductoFragment;
import com.example.taller2.R;
import com.example.taller2.fragments.LoginFragment;


public class MainActivity extends AppCompatActivity {

    private ImageButton btnPerfil, btnVerProductos, btnVerCarrito, btnVerCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnPerfil = findViewById(R.id.button);
        btnVerProductos = findViewById(R.id.btn_ver_productos);
        btnVerCarrito = findViewById(R.id.btn_ver_carrito);
        btnVerCategorias = findViewById(R.id.btn_ver_categorias);

        btnVerProductos.setOnClickListener(v -> {
            abrirFragmento(new ProductoFragment());
            resaltarBotonActivo(btnVerProductos);
        });

        btnVerCategorias.setOnClickListener(v -> {
            abrirFragmento(new CategoriaFragment());
            resaltarBotonActivo(btnVerCategorias);
        });

        btnPerfil.setOnClickListener(v -> {
            if (haySesionActiva()) {
                abrirFragmento(new PerfilFragment());
            } else {
                abrirFragmento(new LoginFragment());
            }
            resaltarBotonActivo(btnPerfil);
        });

        btnVerCarrito.setOnClickListener(v -> {
            if (haySesionActiva()) {
                abrirFragmento(new CarritoFragment());
            } else {
                abrirFragmento(new LoginFragment());
            }
            resaltarBotonActivo(btnVerCarrito);
        });

        // Mostrar ProductoFragment al iniciar
        abrirFragmento(new ProductoFragment());
        resaltarBotonActivo(btnVerProductos);
    }


    public void abrirEditarFragment() {
        abrirFragmento(new EditarFragment());
    }

    public void abrirFragmento(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    // Método para aplicar efecto "oprimido"
    private void resaltarBotonActivo(ImageButton botonActivo) {
        float scaleNormal = 1f;
        float scalePressed = 0.85f;

        // Restaurar todos los botones
        btnPerfil.setScaleX(scaleNormal);
        btnPerfil.setScaleY(scaleNormal);

        btnVerProductos.setScaleX(scaleNormal);
        btnVerProductos.setScaleY(scaleNormal);

        btnVerCarrito.setScaleX(scaleNormal);
        btnVerCarrito.setScaleY(scaleNormal);

        btnVerCategorias.setScaleX(scaleNormal);
        btnVerCategorias.setScaleY(scaleNormal);

        // Aplicar escala al botón activo
        botonActivo.setScaleX(scalePressed);
        botonActivo.setScaleY(scalePressed);
    }

    private boolean haySesionActiva() {
        return getSharedPreferences("Sesion", MODE_PRIVATE)
                .getString("correo_activo", null) != null;
    }

}


