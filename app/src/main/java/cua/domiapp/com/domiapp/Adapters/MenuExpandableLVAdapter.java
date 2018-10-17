package cua.domiapp.com.domiapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import cua.domiapp.com.domiapp.POJOS.CarritoCompras;
import cua.domiapp.com.domiapp.POJOS.Menu;
import cua.domiapp.com.domiapp.POJOS.Producto;
import cua.domiapp.com.domiapp.R;
import cua.domiapp.com.domiapp.VerPedidoActivity;

public class MenuExpandableLVAdapter extends BaseExpandableListAdapter {

    Context context;
    ArrayList<String> listMenu;
    Map<String,ArrayList<String>> mapProductos;
    Map<String,ArrayList<Producto>> mapProductosList;
    ArrayList<Menu> listMenuAll;
    Button procesarCompra;
    public static ArrayList<CarritoCompras> carritoCompras;
    public MenuExpandableLVAdapter(final Context context, ArrayList<String> listMenu, Map<String, ArrayList<String>> mapProductos,
                                   Map<String,ArrayList<Producto>> mapProductosList, ArrayList<Menu> listMenuAll, Button procesarCompra) {
        this.context = context;
        this.listMenu = listMenu;
        this.mapProductos = mapProductos;
        this.mapProductosList = mapProductosList;
        this.listMenuAll = listMenuAll;
        this.procesarCompra = procesarCompra;
        this.carritoCompras = new ArrayList<>();

        procesarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VerPedidoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getGroupCount() {
        return listMenu.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mapProductos.get(this.listMenu.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listMenu.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mapProductos.get(listMenu.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String tituloMenu = "";
        for (Menu menu: listMenuAll) {
            if (menu.getCodigo().equals((String) getGroup(groupPosition))){
                tituloMenu = menu.getNombre();
            }
        }
        convertView = LayoutInflater.from(context).inflate(R.layout.item_group_menu,null);
        TextView tvNombreMenu = convertView.findViewById(R.id.tvNombreMenu);
        tvNombreMenu.setText(tituloMenu);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String nombreProducto = "",
               descripcionProducto = "",
               codigoProducto = "",
               imagenProducto = "";
        double precioProducto = 0;
        ArrayList<Producto> productosTemp = mapProductosList.get((String) getGroup(groupPosition));
        for (Producto producto: productosTemp) {
            if (producto.getCodigo().equals((String) getChild(groupPosition,childPosition))){
                nombreProducto = producto.getNombre();
                descripcionProducto = producto.getDescripcion();
                codigoProducto = producto.getCodigo();
                imagenProducto = producto.getImagen();
                precioProducto = producto.getPrecio();
            }
        }
        convertView = LayoutInflater.from(context).inflate(R.layout.item_child_menu,null);
        final EditText etCantidadProducto = convertView.findViewById(R.id.cantidadProducto);
        TextView tvNombreProducto = convertView.findViewById(R.id.tvNombreProducto);
        TextView tvDescripcionProducto = convertView.findViewById(R.id.tvDescripcionProducto);
        Button addProductCart = convertView.findViewById(R.id.addProductCart);
        ImageView imgProducto = convertView.findViewById(R.id.imgProducto);
        tvNombreProducto.setText(nombreProducto + " - " + formatearPrecio(precioProducto));
        tvDescripcionProducto.setText(descripcionProducto);
        final String finalCodigoProducto = codigoProducto;

        final String finalImagenProducto = imagenProducto;
        final double finalPrecioProducto = precioProducto;
        final String finalNombreProducto = nombreProducto;
        addProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cantidad = etCantidadProducto.getText().toString();
                double precioTotal = finalPrecioProducto *  Integer.parseInt(cantidad);

                CarritoCompras carro = new CarritoCompras(finalImagenProducto,(String) getGroup(groupPosition),finalCodigoProducto, finalNombreProducto,Integer.parseInt(cantidad),precioTotal);
                carritoCompras.add(carro);
                int TotalCarrito = 0;
                for (CarritoCompras carrito:
                     carritoCompras) {
                    TotalCarrito += carrito.getValor();
                }
                procesarCompra.setText("Ver pedido - " + formatearPrecio(TotalCarrito));
                procesarCompra.setVisibility(View.VISIBLE);
            }
        });
        Picasso.get()
                .load(imagenProducto)
                .error(R.drawable.ic_launcher_background)
                .into(imgProducto);
        return convertView;


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public String formatearPrecio(double precioProducto){
        String precioFormateado = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                .format(precioProducto).replace(",",".");
       return precioFormateado.substring(0,precioFormateado.length() - 3);
    }
}
