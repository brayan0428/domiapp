package cua.domiapp.com.domiapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cua.domiapp.com.domiapp.POJOS.Variables;

public class MostrarRuta extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double[][] MyPosition = new double[1][2];
    double lat,lng;
    RequestQueue request;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ruta);
        request= Volley.newRequestQueue(getApplicationContext());
        MyPosition[0][0] = 10.9753325;
        MyPosition[0][1] = -74.7932709;
        lat = 10.9885932;
        lng = -74.7894007;
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        fragment.getMapAsync(this);
        Toast.makeText(getApplicationContext(), "Latitud Origen: " + Variables.lat_origen + ", Longitud Origen: " + Variables.long_origen,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Latitud Destino: " + Variables.lat_destino + ", Longitud Destino: " + Variables.long_destino,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        //    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        /////////////
        LatLng center = null;
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;

        // setUpMapIfNeeded();

        // recorriendo todas las rutas
        for(int i=0;i<Variables.routes.size();i++){
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Obteniendo el detalle de la ruta
            List<HashMap<String, String>> path = Variables.routes.get(i);

            // Obteniendo todos los puntos y/o coordenadas de la ruta
            for(int j=0;j<path.size();j++){
                HashMap<String,String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                if (center == null) {
                    //Obtengo la 1ra coordenada para centrar el mapa en la misma.
                    center = new LatLng(lat, lng);
                }
                points.add(position);
            }

            // Agregamos todos los puntos en la ruta al objeto LineOptions
            lineOptions.addAll(points);
            //Definimos el grosor de las Polilíneas
            lineOptions.width(10);
            //Definimos el color de la Polilíneas
            lineOptions.color(Color.BLUE);
        }

        // Dibujamos las Polilineas en el Google Map para cada ruta
        mMap.addPolyline(lineOptions);

        LatLng origen = new LatLng(MyPosition[0][0], MyPosition[0][1]);
        mMap.addMarker(new MarkerOptions().position(origen).title("Lat: "+MyPosition[0][0]+" - Long: "+MyPosition[0][1]));

        LatLng destino = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(destino).title("Lat: "+lat+" - Long: "+lng));

        /*
        * LatLng origen = new LatLng(Variables.lat_origen, Variables.long_origen);
        mMap.addMarker(new MarkerOptions().position(origen).title("Lat: "+Variables.lat_origen+" - Long: "+Variables.long_origen));

        LatLng destino = new LatLng(Variables.lat_destino, Variables.long_destino);
        mMap.addMarker(new MarkerOptions().position(destino).title("Lat: "+Variables.lat_destino+" - Long: "+Variables.long_destino));
        * */
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 15));
        /////////////

    }
}
