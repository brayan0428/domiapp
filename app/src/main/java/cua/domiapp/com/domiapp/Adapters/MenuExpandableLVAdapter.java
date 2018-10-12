package cua.domiapp.com.domiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import cua.domiapp.com.domiapp.POJOS.Menu;
import cua.domiapp.com.domiapp.POJOS.Producto;
import cua.domiapp.com.domiapp.R;

public class MenuExpandableLVAdapter extends BaseExpandableListAdapter {

    Context context;
    ArrayList<String> listMenu;
    Map<String,ArrayList<String>> mapProductos;

    public MenuExpandableLVAdapter(Context context, ArrayList<String> listMenu, Map<String, ArrayList<String>> mapProductos) {
        this.context = context;
        this.listMenu = listMenu;
        this.mapProductos = mapProductos;
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
        String tituloMenu = (String) getGroup(groupPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_group_menu,null);
        TextView tvNombreMenu = convertView.findViewById(R.id.tvNombreMenu);
        tvNombreMenu.setText(tituloMenu);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String nombreProducto = (String) getChild(groupPosition,childPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_child_menu,null);
        TextView tvNombreProducto = convertView.findViewById(R.id.tvNombreProducto);
        tvNombreProducto.setText(nombreProducto);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
