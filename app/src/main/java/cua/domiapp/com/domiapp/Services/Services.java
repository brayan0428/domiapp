package cua.domiapp.com.domiapp.Services;

import java.util.List;

import cua.domiapp.com.domiapp.POJOS.Menu;
import cua.domiapp.com.domiapp.POJOS.Negocios;
import cua.domiapp.com.domiapp.POJOS.Pedido_Det;
import cua.domiapp.com.domiapp.POJOS.Pedido_Enc;
import cua.domiapp.com.domiapp.POJOS.Producto;
import cua.domiapp.com.domiapp.POJOS.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Services {
    @GET("negocios")
    Call<List<Negocios>> getNegocios();

    @GET("menu/{codigo}")
    Call<List<Menu>> getMenu(@Path("codigo") String codigo);

    @GET("productos/{codigo}")
    Call<List<Producto>> getProductos(@Path("codigo") String codigo);

    @POST("/usuarios")
    Call<List<Usuario>> getUsuario(@Body Usuario usuario);

    @POST("/guardarPedido")
    Call<String> ingresarPedidoEnc(@Body Pedido_Enc pedido_enc);

    @POST("guardarPedidoDet")
    Call<String> ingresarPedidoDet(@Body Pedido_Det pedido_det);
}
