package cua.domiapp.com.domiapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cua.domiapp.com.domiapp.Adapters.MenuExpandableLVAdapter;
import cua.domiapp.com.domiapp.POJOS.CarritoCompras;
import cua.domiapp.com.domiapp.POJOS.Menu;
import cua.domiapp.com.domiapp.POJOS.Negocios;
import cua.domiapp.com.domiapp.POJOS.Producto;
import cua.domiapp.com.domiapp.POJOS.Variables;
import cua.domiapp.com.domiapp.Services.Services;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {
    TextView nombre,categoria,costoenvio,tiempoentrega;
    ImageView back,logo;
    String codigoNegocio = "";
    ProgressBar progressBarMenu;
    Button procesarCompra;

    ArrayList<Menu> listMenu;
    ArrayList<Producto> listProducto;
    ArrayList<String> listStringProduct;
    ArrayList<String> listStringMenu;

    ExpandableListView elvMenu;
    MenuExpandableLVAdapter elvAdapter;
    Map<String,ArrayList<String>> mapProducto;
    Map<String,ArrayList<Producto>> mapProductoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Inicializo los componentes
        nombre = findViewById(R.id.tvMenu_Nombre);
        categoria = findViewById(R.id.tvMenu_Categoria);
        costoenvio = findViewById(R.id.tvMenu_Envio);
        tiempoentrega = findViewById(R.id.tvMenu_Entrega);
        logo = findViewById(R.id.imgMenu_Logo);
        back = findViewById(R.id.imgMenu_Back);
        listMenu = new ArrayList<>();
        mapProducto = new HashMap<>();
        mapProductoList = new HashMap<>();
        elvMenu = findViewById(R.id.elvMenu);
        listStringMenu = new ArrayList<>();
        progressBarMenu = findViewById(R.id.progressBarMenu);
        procesarCompra = findViewById(R.id.carritoCompras);

        //Obtengo parametros
        Bundle datos = this.getIntent().getExtras();
        if (datos != null){
            Picasso.get()
                    .load(datos.getString("pLogo"))
                    .error(R.drawable.ic_launcher_background)
                    .into(logo);
            nombre.setText(datos.getString("pNombre"));
            categoria.setText(datos.getString("pCategoria"));
            costoenvio.setText(datos.getString("pCostoEnvio"));
            tiempoentrega.setText(datos.getString("pTiempoEntrega"));
            codigoNegocio = datos.getString("pCodigo");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Ejecutamos la tarea
        new Informacion(getApplicationContext()).execute();
    }

    @Override
    public void onBackPressed() {
        List<CarritoCompras> carritoCompras;
        carritoCompras = MenuExpandableLVAdapter.carritoCompras;
        if (carritoCompras.size() > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true)
                    .setTitle("Confirmación")
                    .setMessage("Si sales de esta pantalla perderas los productos del carrito ¿Deseas continuar?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MenuActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            builder.create();
            builder.show();
        }
    }

    public class Informacion extends AsyncTask<Void,Void,Void>{
        Context context;

        public Informacion(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Variables.URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Services services = retrofit.create(Services.class);
            Call<List<Menu>> data = services.getMenu(codigoNegocio);
            try{
                for (Menu menu : data.execute().body()){
                    listProducto = new ArrayList<>();
                    listStringProduct = new ArrayList<>();
                    listMenu.add(menu);
                    listStringMenu.add(menu.getCodigo());
                    Call<List<Producto>> product = services.getProductos(menu.getCodigo());
                    for (Producto producto : product.execute().body()){
                        listProducto.add(producto);
                        listStringProduct.add(producto.getCodigo());
                    }
                    mapProducto.put(menu.getCodigo(),listStringProduct);
                    mapProductoList.put(menu.getCodigo(),listProducto);
                }
            }catch (Exception e) {
                Log.e("doInBackground: ", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            elvAdapter = new MenuExpandableLVAdapter(this.context,listStringMenu,mapProducto,mapProductoList,listMenu,procesarCompra);
            elvMenu.setAdapter(elvAdapter);
            progressBarMenu.setVisibility(View.GONE);
        }
    }
}
