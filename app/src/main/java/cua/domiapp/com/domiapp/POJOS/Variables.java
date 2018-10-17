package cua.domiapp.com.domiapp.POJOS;

import java.text.NumberFormat;
import java.util.Locale;

public class Variables {
    public static String URL_API = "https://domi-app.herokuapp.com";

    public static String formatearPrecio(double precioProducto){
        String precioFormateado = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                .format(precioProducto).replace(",",".");
        return precioFormateado.substring(0,precioFormateado.length() - 3);
    }
}
