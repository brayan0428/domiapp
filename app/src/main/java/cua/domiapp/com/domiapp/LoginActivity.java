package cua.domiapp.com.domiapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cua.domiapp.com.domiapp.POJOS.Usuario;
import cua.domiapp.com.domiapp.POJOS.Variables;
import cua.domiapp.com.domiapp.Services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    TextView tvRegistrarme;
    Button btnLogin;
    AutoCompleteTextView acEmail,acClave;
    ProgressBar pbCargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("datosUsuario",MODE_PRIVATE);
        int idUsuario = sp.getInt("IdUsuario",0);
        if (idUsuario != 0){
            redireccionar();
        }
        btnLogin = findViewById(R.id.btnIniciarSesion);
        acEmail = findViewById(R.id.acEmail);
        acClave = findViewById(R.id.acClave);
        pbCargando = findViewById(R.id.pbCargando);
        tvRegistrarme = findViewById(R.id.tvRegistrarme);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Variables.URL_API)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Services services = retrofit.create(Services.class);
                String Email = acEmail.getText().toString().trim();
                String Clave = acClave.getText().toString().trim();
                if (Email.equals("") || Clave.equals("")){
                    Toast.makeText(getApplicationContext(),"Debe ingresar el usuario y la clave",Toast.LENGTH_LONG).show();
                    return;
                }
                pbCargando.setVisibility(View.VISIBLE);
                Usuario usuario  = new Usuario(0,Email,Clave,"");
                Call<List<Usuario>> response = services.getUsuario(usuario);
                response.enqueue(new Callback<List<Usuario>>() {
                    @Override
                    public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                        if (response.isSuccessful()){
                            if (response.body().size() <= 0){
                                Toast.makeText(getApplicationContext(),"Usuario o Clave invalidos",Toast.LENGTH_LONG).show();
                                pbCargando.setVisibility(View.GONE);
                            }else{
                                crearSharedPreferences(response.body().get(0).getId(),response.body().get(0).getEmail());
                                redireccionar();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Usuario>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        tvRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrarmeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void crearSharedPreferences(int id,String correo){
        SharedPreferences sharedPreferences = getSharedPreferences("datosUsuario",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("IdUsuario",id);
        editor.putString("emailUsuario",correo);
        editor.commit();
    }

    private void redireccionar(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
