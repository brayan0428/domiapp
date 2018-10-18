package cua.domiapp.com.domiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import cua.domiapp.com.domiapp.Adapters.VerPedidoAdapter;
import cua.domiapp.com.domiapp.POJOS.CarritoCompras;
import cua.domiapp.com.domiapp.POJOS.Variables;

public class DatosEnvioActivity extends AppCompatActivity {
    ArrayList<CarritoCompras> carrito;
    TextView tvTotal_Pedido,
            tvTotalEnvio,
            tvSubtotalPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_envio);
        tvSubtotalPedido = findViewById(R.id.tvSubtotalPedido);
        tvTotalEnvio = findViewById(R.id.tvValorEnvio);
        tvTotal_Pedido = findViewById(R.id.tvTotal_Pedido);
        carrito = VerPedidoAdapter.carritoCompras;
        sumatoriaTotal();
    }

    public void sumatoriaTotal(){
        double valor_Total = 0;
        for (CarritoCompras carrito: carrito) {
            valor_Total += carrito.getValor();
        }
        tvSubtotalPedido.setText(Variables.formatearPrecio(valor_Total));
        tvTotalEnvio.setText(Variables.formatearPrecio(5000));
        tvTotal_Pedido.setText(Variables.formatearPrecio(valor_Total + 5000));
    }
}
