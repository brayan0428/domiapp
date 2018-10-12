package cua.domiapp.com.domiapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cua.domiapp.com.domiapp.MenuActivity;
import cua.domiapp.com.domiapp.POJOS.Negocios;
import cua.domiapp.com.domiapp.R;

public class NegociosAdapter extends RecyclerView.Adapter<NegociosAdapter.ViewHolder> {
    Context context;
    List<Negocios> negociosList;

    public NegociosAdapter(Context context, List<Negocios> negociosList) {
        this.context = context;
        this.negociosList = negociosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_negocios,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvNombreNegocio.setText(negociosList.get(position).getNombre());
        holder.tvCategoriaNegocio.setText(negociosList.get(position).getNombre_categoria());
        holder.tvCostoEnvioNegocio.setText(negociosList.get(position).getCostoenvio());
        holder.tvTiempoEntregaNegocio.setText(negociosList.get(position).getTiempoentrega());
        Picasso.get()
                .load(negociosList.get(position).getLogo())
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgNegocio);
        holder.cvNegocios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("pCodigo",negociosList.get(position).getCodigo());
                intent.putExtra("pLogo",negociosList.get(position).getLogo());
                intent.putExtra("pNombre",negociosList.get(position).getNombre());
                intent.putExtra("pCategoria",negociosList.get(position).getNombre_categoria());
                intent.putExtra("pCostoEnvio",negociosList.get(position).getCostoenvio());
                intent.putExtra("pTiempoEntrega",negociosList.get(position).getTiempoentrega());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return negociosList.size();
    }

    public void filtrarLista(ArrayList<Negocios> listaFiltrada){
        negociosList = listaFiltrada;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cvNegocios;
        TextView tvNombreNegocio,tvCategoriaNegocio,tvTiempoEntregaNegocio,tvCostoEnvioNegocio;
        ImageView imgNegocio;
        public ViewHolder(View v) {
            super(v);
            cvNegocios = v.findViewById(R.id.cvNegocios);
            tvNombreNegocio = v.findViewById(R.id.tvNombreNegocio);
            tvCategoriaNegocio = v.findViewById(R.id.tvCategoriaNegocio);
            tvTiempoEntregaNegocio = v.findViewById(R.id.tvTiempoEntregaNegocio);
            tvCostoEnvioNegocio = v.findViewById(R.id.tvCostoEnvioNegocio);
            imgNegocio = v.findViewById(R.id.imgNegocio);
        }
    }
}
