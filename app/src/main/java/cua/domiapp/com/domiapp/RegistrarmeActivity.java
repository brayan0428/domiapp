package cua.domiapp.com.domiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import cua.domiapp.com.domiapp.POJOS.Usuario;
import cua.domiapp.com.domiapp.POJOS.Variables;
import cua.domiapp.com.domiapp.Services.Services;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarmeActivity extends AppCompatActivity {

    AutoCompleteTextView acNombreRegistro,acEmailRegistro,acClaveRegistro,acConfirmarClaveRegistro;
    Button btnGuardarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarme);

        acNombreRegistro = findViewById(R.id.acNombreRegistro);
        acEmailRegistro = findViewById(R.id.acEmailRegistro);
        acClaveRegistro = findViewById(R.id.acClaveRegistro);
        acConfirmarClaveRegistro = findViewById(R.id.acConfirmarClaveRegistro);
        btnGuardarUsuario = findViewById(R.id.btnGuardarUsuario);

        btnGuardarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = acNombreRegistro.getText().toString(),
                        email=acEmailRegistro.getText().toString(),
                        clave=acClaveRegistro.getText().toString(),
                        confirmarClave = acConfirmarClaveRegistro.getText().toString();
                if (nombre.equals("")){
                    Variables.mostrarMensaje(getApplicationContext(),"Debe ingresar el nombre");
                    return;
                }
                if (email.equals("")){
                    Variables.mostrarMensaje(getApplicationContext(),"Debe ingresar el email");
                    return;
                }
                if (clave.equals("")){
                    Variables.mostrarMensaje(getApplicationContext(),"Debe ingresar la clave");
                    return;
                }
                if (confirmarClave.equals("")){
                    Variables.mostrarMensaje(getApplicationContext(),"Debe confirmar la clave");
                    return;
                }
                if (!clave.equals(confirmarClave)){
                    Variables.mostrarMensaje(getApplicationContext(),"Las claves no coinciden");
                    return;
                }
                Usuario usuario = new Usuario(0,email,clave,nombre);
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
                Call<String> response = services.ingresarUsuario(usuario);
                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()){
                            if (response.body().equals("Ok")){
                                Variables.mostrarMensaje(getApplicationContext(),"Ingresado exitosamente");
                                Redireccionar();
                            }else{
                                Variables.mostrarMensaje(getApplicationContext(),response.body());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Variables.mostrarMensaje(getApplicationContext(),t.getMessage());
                    }
                });
            }
        });
    }

    public void Redireccionar(){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
