package cua.domiapp.com.domiapp.POJOS;

import android.content.Context;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Variables {
    public static String URL_API = "https://domi-app.herokuapp.com";
    public static List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>();
    public static double lat_origen = 0,
                         long_origen = 0,
                         lat_destino = 0,
                         long_destino = 0;

    public static String formatearPrecio(double precioProducto){
        String precioFormateado = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                .format(precioProducto).replace(",",".");
        return precioFormateado.substring(0,precioFormateado.length() - 3);
    }

    public static void mostrarMensaje(Context context,String msn) {
        Toast.makeText(context,msn,Toast.LENGTH_SHORT).show();
    }
}
