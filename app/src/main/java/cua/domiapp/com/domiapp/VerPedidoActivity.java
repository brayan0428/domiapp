package cua.domiapp.com.domiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import cua.domiapp.com.domiapp.Adapters.MenuExpandableLVAdapter;
import cua.domiapp.com.domiapp.Adapters.VerPedidoAdapter;
import cua.domiapp.com.domiapp.POJOS.CarritoCompras;
import cua.domiapp.com.domiapp.POJOS.Variables;

public class VerPedidoActivity extends AppCompatActivity {
    RecyclerView rvVerPedido;
    ImageView imgBackPedido;
    ArrayList<CarritoCompras> carritoCompras;
    ArrayList<CarritoCompras> carritoCompras_New;
    VerPedidoAdapter verPedidoAdapter;
    Button btnContinuarPedido;

    static TextView tvTotal_Pedido,
             tvTotalEnvio,
             tvSubtotalPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedido);

        rvVerPedido = findViewById(R.id.rvVerPedido);
        tvSubtotalPedido = findViewById(R.id.tvSubtotalPedido);
        tvTotalEnvio = findViewById(R.id.tvValorEnvio);
        tvTotal_Pedido = findViewById(R.id.tvTotal_Pedido);
        imgBackPedido = findViewById(R.id.imgBackPedido);
        btnContinuarPedido = findViewById(R.id.btnContinuarPedido);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this,LinearLayout.VERTICAL,false);
        rvVerPedido.setLayoutManager(linearLayout);
        carritoCompras = MenuExpandableLVAdapter.carritoCompras;
        agruparPedido();
        verPedidoAdapter = new VerPedidoAdapter(this,carritoCompras_New);
        rvVerPedido.setAdapter(verPedidoAdapter);

        imgBackPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnContinuarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DatosEnvioActivity.class);
                startActivity(intent);
            }
        });
    }

    public void agruparPedido(){
        carritoCompras_New = new ArrayList<>();
        Collections.sort(carritoCompras);
        String codigoProducto = carritoCompras.get(0).getCodigoProducto(),
               nombreProducto = "",
               imgProducto = "",
               codigoNegocio = carritoCompras.get(0).getCodigoNegocio();
        int cantidad = 0;
        double valor = 0;
        double valor_Total = 0;
        for (CarritoCompras carrito: carritoCompras) {
            if (codigoProducto == carrito.getCodigoProducto()){
                cantidad += carrito.getCantidad();
                valor = carrito.getValor();
                nombreProducto = carrito.getNombreProducto();
                imgProducto = carrito.getImagenProducto();
            }else{
                CarritoCompras newCar = new CarritoCompras(imgProducto,carrito.getCodigoNegocio(),codigoProducto,nombreProducto,cantidad,valor);
                carritoCompras_New.add(newCar);
                cantidad = carrito.getCantidad();
                valor = carrito.getValor();
                nombreProducto = carrito.getNombreProducto();
                imgProducto = carrito.getImagenProducto();
            }
            codigoProducto = carrito.getCodigoProducto();
            valor_Total += carrito.getValor();
        }
        CarritoCompras newCar = new CarritoCompras(imgProducto,codigoNegocio,codigoProducto,nombreProducto,cantidad,valor);
        carritoCompras_New.add(newCar);
        tvSubtotalPedido.setText(Variables.formatearPrecio(valor_Total));
        tvTotalEnvio.setText(Variables.formatearPrecio(5000));
        tvTotal_Pedido.setText(Variables.formatearPrecio(valor_Total + 5000));

    }

    public static void sumatoriaTotal(ArrayList<CarritoCompras> carritoCompras){
        double valor_Total = 0;
        for (CarritoCompras carrito: carritoCompras) {
            valor_Total += carrito.getValor();
        }
        tvSubtotalPedido.setText(Variables.formatearPrecio(valor_Total));
        tvTotalEnvio.setText(Variables.formatearPrecio(5000));
        tvTotal_Pedido.setText(Variables.formatearPrecio(valor_Total + 5000));
    }
}
