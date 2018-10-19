package cua.domiapp.com.domiapp.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNumPedido.setText("Pedido #" + misPedidos.get(position).getNumpedido() + " - " + misPedidos.get(position).getNombrenegocio());
        holder.tvValorTotalPedido.setText(Variables.formatearPrecio(misPedidos.get(position).getValortotal()));
        holder.tvEntregadoPedido.setText("Entregado: " + misPedidos.get(position).getEntregado());
        holder.tvFechaPedido.setText(misPedidos.get(position).getFecha().substring(0,10));
    }

    @Override
    public int getItemCount() {
        return misPedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumPedido,tvValorTotalPedido,tvFechaPedido,tvEntregadoPedido;
        public ViewHolder(View v) {
            super(v);
            tvNumPedido = v.findViewById(R.id.tvNumPedido);
            tvValorTotalPedido = v.findViewById(R.id.tvValorTotalPedido);
            tvFechaPedido = v.findViewById(R.id.tvFechaPedido);
            tvEntregadoPedido = v.findViewById(R.id.tvEntregadoPedido);
        }
    }
}
