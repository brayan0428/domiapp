package cua.domiapp.com.domiapp.Services;

import java.util.List;

import cua.domiapp.com.domiapp.POJOS.Menu;
import cua.domiapp.com.domiapp.POJOS.Negocios;
import cua.domiapp.com.domiapp.POJOS.Producto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Services {
    @GET("negocios")
    Call<List<Negocios>> getNegocios();

    @GET("menu/{codigo}")
    Call<List<Menu>> getMenu(@Path("codigo") String codigo);

    @GET("productos/{codigo}")
    Call<List<Producto>> getProductos(@Path("codigo") String codigo);
}
