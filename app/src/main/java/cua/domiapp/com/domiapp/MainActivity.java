package cua.domiapp.com.domiapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import cua.domiapp.com.domiapp.Adapters.NegociosAdapter;
import cua.domiapp.com.domiapp.POJOS.Negocios;
import cua.domiapp.com.domiapp.POJOS.Variables;
import cua.domiapp.com.domiapp.Services.Services;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    RecyclerView rvNegocios;
    ArrayList<Negocios> listNegocios;
    NegociosAdapter negociosAdapter;
    EditText filtroNegocio;
    ProgressBar progressBar;
    ImageView imgCerrarSesion,imgVerPedidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializando Variables
        rvNegocios = findViewById(R.id.rvNegocios);
        filtroNegocio = findViewById(R.id.filtroNegocio);
        progressBar = findViewById(R.id.progressBar);
        imgCerrarSesion = findViewById(R.id.imgCerrarSesion);
        imgVerPedidos = findViewById(R.id.imgVerPedidos);

        //Creamos el LinearLayoutManager para manejar el RecylerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvNegocios.setLayoutManager(linearLayoutManager);
        listNegocios = new ArrayList<>();

        //Ejecutamos la tarea
        new Peticion(this).execute();


        //Eventos
        filtroNegocio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Filter(s.toString());
            }
        });

        imgCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setCancelable(true)
                       .setTitle("Confirmación")
                       .setMessage("¿Esta seguro que desea cerrrar la sesión?")
                       .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                crearSharedPreferences(0,"");
                                redireccionar();
                            }
                        })
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });

        imgVerPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MisPedidosActivity.class);
                startActivity(intent);
            }
        });
        //Animaciones
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        filtroNegocio.startAnimation(animation);
    }

    public void Filter(String text) {
        ArrayList<Negocios> listaFiltrada = new ArrayList<>();
        for(Negocios negocio: listNegocios){
            if (negocio.getNombre().toLowerCase().contains(text.toLowerCase())){
                listaFiltrada.add(negocio);
            }
        }
        negociosAdapter.filtrarLista(listaFiltrada);
    }

    public class Peticion extends AsyncTask<Void,Void,Void> {
        Context context;
        public Peticion(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Variables.URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Services services = retrofit.create(Services.class);
            Call<List<Negocios>> data = services.getNegocios();
            try{
                for (Negocios negocio : data.execute().body()){
                    listNegocios.add(negocio);
                }
            }catch (Exception e){
                Log.e("doInBackground: ", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            negociosAdapter = new NegociosAdapter(this.context,listNegocios);
            rvNegocios.setAdapter(negociosAdapter);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void crearSharedPreferences(int id,String correo){
        SharedPreferences sharedPreferences = getSharedPreferences("datosUsuario",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("IdUsuario",id);
        editor.putString("emailUsuario",correo);
        editor.commit();
    }

    private void redireccionar(){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
