package cua.domiapp.com.domiapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cua.domiapp.com.domiapp.Adapters.MisPedidosAdapter;
import cua.domiapp.com.domiapp.POJOS.MisPedidos;
import cua.domiapp.com.domiapp.POJOS.Negocios;
import cua.domiapp.com.domiapp.POJOS.Variables;
import cua.domiapp.com.domiapp.Services.Services;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MisPedidosActivity extends AppCompatActivity {
    ImageView imgBackMisPedido;
    RecyclerView rvMisPedidos;
    ArrayList<MisPedidos> misPedidos;
    MisPedidosAdapter misPedidosAdapter;
    ProgressBar pbCargandoMisPedidos;
    TextView tvNoTienePedido;

    int idusuario = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_pedidos);

        imgBackMisPedido = findViewById(R.id.imgBackMisPedido);
        rvMisPedidos = findViewById(R.id.rvMisPedidos);
        pbCargandoMisPedidos = findViewById(R.id.pbCargandoMisPedidos);
        tvNoTienePedido = findViewById(R.id.tvNoTienePedido);

        SharedPreferences sp = getSharedPreferences("datosUsuario",MODE_PRIVATE);
        idusuario = sp.getInt("IdUsuario",0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvMisPedidos.setLayoutManager(linearLayoutManager);
        misPedidos = new ArrayList<>();

        imgBackMisPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        new Peticion().execute();
    }

    public class Peticion extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Variables.URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Services services = retrofit.create(Services.class);
            Call<List<MisPedidos>> data = services.getMisPedidos(idusuario);
            try{
                for (MisPedidos pedidos : data.execute().body()){
                    misPedidos.add(pedidos);
                }
            }catch (Exception e){
                Log.e("doInBackground: ", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            misPedidosAdapter = new MisPedidosAdapter(misPedidos,getApplicationContext());
            rvMisPedidos.setAdapter(misPedidosAdapter);
            pbCargandoMisPedidos.setVisibility(View.GONE);
            if(misPedidos.size() <= 0){
                tvNoTienePedido.setVisibility(View.VISIBLE);
            }
        }
    }
}
