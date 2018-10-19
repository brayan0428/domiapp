package cua.domiapp.com.domiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import cua.domiapp.com.domiapp.Adapters.VerPedidoAdapter;
import cua.domiapp.com.domiapp.POJOS.CarritoCompras;
import cua.domiapp.com.domiapp.POJOS.Pedido_Det;
import cua.domiapp.com.domiapp.POJOS.Pedido_Enc;
import cua.domiapp.com.domiapp.POJOS.Variables;

import cua.domiapp.com.domiapp.Services.Services;
import okhttp3.OkHttpClient;
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

    ProgressBar pbCargandoEnvio;

    Button btnConfirmarPedido;
    int CodigoUsu = 0;
    double pedidoTotal = 0;
    String codigoPedido = "";

    ArrayList<Pedido_Det> pedidoDet;

    int cont = 0;
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
        pbCargandoEnvio = findViewById(R.id.pbCargandoEnvio);

        carrito = VerPedidoAdapter.carritoCompras;
        sumatoriaTotal();

        //Obtengo id del usuario
        SharedPreferences sp = getSharedPreferences("datosUsuario",MODE_PRIVATE);
        CodigoUsu = sp.getInt("IdUsuario",0);

        //Obtengo datos de envio
        etDireccionEnvio.setText(sp.getString("direccionEnvio",""));
        etBarrioEnvio.setText(sp.getString("barrioEnvio",""));
        etTelefonoEnvio.setText(sp.getString("telefonoEnvio",""));

        btnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String direccionEnvio = etDireccionEnvio.getText().toString();
                String barrioEnvio = etBarrioEnvio.getText().toString();
                String telefonoEnvio = etTelefonoEnvio.getText().toString();
                if (direccionEnvio.equals("")){
                    Variables.mostrarMensaje(getApplicationContext(),"Debe ingresar la direcciòn de envio");
                    return;
                }
                if (barrioEnvio.equals("")){
                    Variables.mostrarMensaje(getApplicationContext(),"Debe ingresar el barrio");
                    return;
                }
                if (telefonoEnvio.equals("")){
                    Variables.mostrarMensaje(getApplicationContext(),"Debe ingresar el telefono");
                    return;
                }
                crearSharedPreferences("direccionEnvio",direccionEnvio);
                crearSharedPreferences("barrioEnvio",barrioEnvio);
                crearSharedPreferences("telefonoEnvio",telefonoEnvio);

                pbCargandoEnvio.setVisibility(View.VISIBLE);
                final Pedido_Enc pedido_enc = new Pedido_Enc(0,CodigoUsu,carrito.get(0).getCodigoNegocio(),pedidoTotal,etDireccionEnvio.getText().toString(),"01","");
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(60,TimeUnit.SECONDS)
                        .writeTimeout(60,TimeUnit.SECONDS)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Variables.URL_API)
                        .client(okHttpClient)
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
                                            pbCargandoEnvio.setVisibility(View.GONE);
                                            Variables.mostrarMensaje(getApplicationContext(),response.message());
                                            return;
                                        }else{
                                            cont++;
                                            if (cont == pedidoDet.size()){
                                                Variables.mostrarMensaje(getApplicationContext(),"Pedido #" + codigoPedido + " guardado exitosamente");
                                                Redireccionar();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Variables.mostrarMensaje(getApplicationContext(),t.getMessage());
                                        pbCargandoEnvio.setVisibility(View.GONE);
                                        return;
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        pbCargandoEnvio.setVisibility(View.GONE);
                        Variables.mostrarMensaje(getApplicationContext(),t.getMessage());
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

    public void Redireccionar(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void crearSharedPreferences(String campo,String valor){
        SharedPreferences sharedPreferences = getSharedPreferences("datosUsuario",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(campo,valor);
        editor.commit();
    }
}
