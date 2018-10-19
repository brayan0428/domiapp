package cua.domiapp.com.domiapp.POJOS;

import android.content.Context;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class Variables {
    public static String URL_API = "https://domi-app.herokuapp.com";

    public static String formatearPrecio(double precioProducto){
        String precioFormateado = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                .format(precioProducto).replace(",",".");
        return precioFormateado.substring(0,precioFormateado.length() - 3);
    }

    public static void mostrarMensaje(Context context,String msn) {
        Toast.makeText(context,msn,Toast.LENGTH_SHORT).show();
    }
}
