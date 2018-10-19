package cua.domiapp.com.domiapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import cua.domiapp.com.domiapp.Adapters.VerPedidoAdapter;
import cua.domiapp.com.domiapp.POJOS.CarritoCompras;
import cua.domiapp.com.domiapp.POJOS.Pedido_Det;
import cua.domiapp.com.domiapp.POJOS.Pedido_Enc;
import cua.domiapp.com.domiapp.POJOS.Variables;

import cua.domiapp.com.domiapp.Services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class DatosEnvioActivity extends AppCompatActivity {
    ArrayList<CarritoCompras> carrito;
    TextView tvTotal_Pedido,
             tvTotalEnvio,
             tvSubtotalPedido;

    EditText etDireccionEnvio,
             etBarrioEnvio,
             etTelefonoEnvio;

    Button btnConfirmarPedido;
    int CodigoUsu = 0;
    double pedidoTotal = 0;
    String codigoPedido = "";

    ArrayList<Pedido_Det> pedidoDet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_envio);

        tvSubtotalPedido = findViewById(R.id.tvSubtotalPedido);
        tvTotalEnvio = findViewById(R.id.tvValorEnvio);
        tvTotal_Pedido = findViewById(R.id.tvTotal_Pedido);
        etDireccionEnvio = findViewById(R.id.etDireccionEnvio);
        etBarrioEnvio = findViewById(R.id.etBarrioEnvio);
        etTelefonoEnvio = findViewById(R.id.etTelefonoEnvio);
        btnConfirmarPedido = findViewById(R.id.btnConfirmarPedido);
        carrito = VerPedidoAdapter.carritoCompras;
        sumatoriaTotal();

        //Obtengo id del usuario
        SharedPreferences sp = getSharedPreferences("datosUsuario",MODE_PRIVATE);
        CodigoUsu = sp.getInt("IdUsuario",0);

        btnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etDireccionEnvio.getText().equals("")){
                    Variables.mostrarMensaje(getApplicationContext(),"Debe ingresar la direcciòn de envio");
                    return;
                }
                if (etBarrioEnvio.getText().equals("")){
                    Variables.mostrarMensaje(getApplicationContext(),"Debe ingresar el barrio");
                    return;
                }
                if (etTelefonoEnvio.getText().equals("")){
                    Variables.mostrarMensaje(getApplicationContext(),"Debe ingresar el telefono");
                    return;
                }
                Pedido_Enc pedido_enc = new Pedido_Enc(0,CodigoUsu,carrito.get(0).getCodigoNegocio(),pedidoTotal,etDireccionEnvio.getText().toString(),"01","");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Variables.URL_API)
                            .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final Services services = retrofit.create(Services.class);
                Call<String> response = services.ingresarPedidoEnc(pedido_enc);
                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()){
                            codigoPedido = response.body();
                            llenarPedidoDet();
                            Call<String> r;
                            for (Pedido_Det pedido:
                                 pedidoDet) {
                                r = services.ingresarPedidoDet(pedido);
                                r.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (!response.isSuccessful()) {
                                            Variables.mostrarMensaje(getApplicationContext(),response.message());
                                            return;
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Variables.mostrarMensaje(getApplicationContext(),t.getMessage());
                                        return;
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
    }

    public void sumatoriaTotal(){
        for (CarritoCompras carrito: carrito) {
            pedidoTotal += carrito.getValor();
        }
        tvSubtotalPedido.setText(Variables.formatearPrecio(pedidoTotal));
        tvTotalEnvio.setText(Variables.formatearPrecio(5000));
        tvTotal_Pedido.setText(Variables.formatearPrecio(pedidoTotal + 5000));
    }

    public void llenarPedidoDet(){
        pedidoDet = new ArrayList<>();
        for (CarritoCompras c:
             carrito) {
            Pedido_Det pedido = new Pedido_Det(Integer.parseInt(codigoPedido),Integer.parseInt(c.getCodigoProducto()),c.getValor(),c.getCantidad());
            pedidoDet.add(pedido);
        }
    }
}
