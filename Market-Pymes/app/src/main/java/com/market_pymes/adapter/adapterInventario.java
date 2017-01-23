package com.market_pymes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.market_pymes.R;
import com.market_pymes.model.Inventario;

import java.util.List;

/**
 * Created by sleyterangulo on 1/20/17.
 */

public class adapterInventario extends RecyclerView.Adapter<adapterInventario.InventarioViewHolder> {
    private List<Inventario> items;

    public static class InventarioViewHolder extends RecyclerView.ViewHolder{
        public TextView nomProducto;
        public TextView categoria;
        public TextView despcricion;
        public TextView precio;
        public TextView cantidad;

        public InventarioViewHolder(View v) {
            super(v);
            nomProducto = (TextView) v.findViewById(R.id.nomProducto);
            categoria = (TextView) v.findViewById(R.id.catProducto);
            despcricion = (TextView) v.findViewById(R.id.descProducto);
            precio = (TextView) v.findViewById(R.id.precProducto);
            cantidad = (TextView) v.findViewById(R.id.cantProducto);
        }
    }

    public adapterInventario(List<Inventario> items){this.items = items;}

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public InventarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_inventarios,viewGroup,false);
        return new InventarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(InventarioViewHolder holder, int position) {
        holder.nomProducto.setText(items.get(position).getNomProducto());
        holder.categoria.setText(items.get(position).getCatProducto());
        holder.despcricion.setText(items.get(position).getDesProducto());
        holder.precio.setText(String.valueOf(items.get(position).getPrecProducto()));
        holder.precio.setText(String.valueOf(items.get(position).getCantProducto()));
    }
}
