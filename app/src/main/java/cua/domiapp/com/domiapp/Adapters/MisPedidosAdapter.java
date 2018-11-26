package cua.domiapp.com.domiapp.Adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cua.domiapp.com.domiapp.CargarMapa;
import cua.domiapp.com.domiapp.POJOS.MisPedidos;
import cua.domiapp.com.domiapp.POJOS.Variables;
import cua.domiapp.com.domiapp.R;

public class MisPedidosAdapter extends RecyclerView.Adapter<MisPedidosAdapter.ViewHolder>{
    ArrayList<MisPedidos> misPedidos;
    Context context;

    public MisPedidosAdapter(ArrayList<MisPedidos> misPedidos, Context context) {
        this.misPedidos = misPedidos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mipedido,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvNumPedido.setText("Pedido #" + misPedidos.get(position).getNumpedido() + " - " + misPedidos.get(position).getNombrenegocio());
        holder.tvValorTotalPedido.setText(Variables.formatearPrecio(misPedidos.get(position).getValortotal()));
        holder.tvEntregadoPedido.setText("Entregado: " + misPedidos.get(position).getEntregado());
        holder.tvFechaPedido.setText(misPedidos.get(position).getFecha().substring(0,10));
        holder.cvMisPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variables.lat_destino = misPedidos.get(position).getLatitud();
                Variables.long_destino = misPedidos.get(position).getLongitud();
                Intent intent = new Intent(context, CargarMapa.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return misPedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumPedido,tvValorTotalPedido,tvFechaPedido,tvEntregadoPedido;
        CardView cvMisPedidos;
        public ViewHolder(View v) {
            super(v);
            tvNumPedido = v.findViewById(R.id.tvNumPedido);
            tvValorTotalPedido = v.findViewById(R.id.tvValorTotalPedido);
            tvFechaPedido = v.findViewById(R.id.tvFechaPedido);
            tvEntregadoPedido = v.findViewById(R.id.tvEntregadoPedido);
            cvMisPedidos = v.findViewById(R.id.cvMisPedidos);
        }
    }
}
