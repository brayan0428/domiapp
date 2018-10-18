package cua.domiapp.com.domiapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

import cua.domiapp.com.domiapp.MenuActivity;
import cua.domiapp.com.domiapp.POJOS.CarritoCompras;
import cua.domiapp.com.domiapp.POJOS.Menu;
import cua.domiapp.com.domiapp.POJOS.Variables;
import cua.domiapp.com.domiapp.R;
import cua.domiapp.com.domiapp.VerPedidoActivity;

public class VerPedidoAdapter extends RecyclerView.Adapter<VerPedidoAdapter.ViewHolder> {
    Context context;
    public static ArrayList<CarritoCompras> carritoCompras;

    public VerPedidoAdapter(Context context, ArrayList<CarritoCompras> carritoCompras) {
        this.context = context;
        this.carritoCompras = carritoCompras;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvNombreProductoPedido.setText(carritoCompras.get(position).getNombreProducto());
        holder.tvCantidadProductoPedido.setText(carritoCompras.get(position).getCantidad() + " x " + Variables.formatearPrecio(carritoCompras.get(position).getValor()));
        holder.tvTotalProductoPedido.setText(Variables.formatearPrecio(carritoCompras.get(position).getCantidad() * carritoCompras.get(position).getValor()));
        Picasso.get()
                .load(carritoCompras.get(position).getImagenProducto())
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgProductoPedido);
        holder.btnEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator<CarritoCompras> iterator =  MenuExpandableLVAdapter.carritoCompras.iterator();
                while (iterator.hasNext()){
                    CarritoCompras c = iterator.next();
                    if (c.getCodigoProducto() == carritoCompras.get(position).getCodigoProducto()){
                        iterator.remove();
                    }
                }
                carritoCompras.remove(position);
                notifyDataSetChanged();
                if (carritoCompras.size() == 0){
                    MenuActivity.procesarCompra.setVisibility(View.GONE);
                    ((Activity)context).finish();
                }else{
                    int TotalCarrito = 0;
                    for (CarritoCompras carrito:
                            carritoCompras) {
                        TotalCarrito += carrito.getValor();
                    }
                    MenuActivity.procesarCompra.setText("Ver pedido - " + Variables.formatearPrecio(TotalCarrito));
                    VerPedidoActivity.sumatoriaTotal(carritoCompras);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return carritoCompras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProductoPedido;
        TextView tvNombreProductoPedido;
        TextView tvCantidadProductoPedido;
        TextView tvTotalProductoPedido;
        Button btnEliminarProducto;
        public ViewHolder(View itemView) {
            super(itemView);
            imgProductoPedido = itemView.findViewById(R.id.imgProductoPedido);
            tvNombreProductoPedido = itemView.findViewById(R.id.tvNombreProductoPedido);
            tvCantidadProductoPedido = itemView.findViewById(R.id.tvCantidadPedido);
            tvTotalProductoPedido = itemView.findViewById(R.id.tvTotalPedido);
            btnEliminarProducto = itemView.findViewById(R.id.btnEliminarProducto);
        }
    }
}
