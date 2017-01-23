package com.market_pymes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.market_pymes.R;
import com.market_pymes.model.Min;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by sleyterangulo on 1/23/17.
 */

public class adapterMin extends RecyclerView.Adapter<adapterMin.MinViewHolder> {
    private List<Min> items;

    public static class MinViewHolder extends RecyclerView.ViewHolder{
        public TextView codProducto;
        public TextView categoria;
        public TextView descripcion;
        public TextView inventario;
        public TextView cantidad;

        public MinViewHolder(View v){
            super(v);
            codProducto = (TextView) v.findViewById(R.id.nomProducto);
            categoria = (TextView) v.findViewById(R.id.catProducto);
            descripcion = (TextView) v.findViewById(R.id.descProducto);
            inventario = (TextView) v.findViewById(R.id.precProducto);
            cantidad = (TextView) v.findViewById(R.id.cantProducto);
        }
    }

    public adapterMin(List<Min> items){this.items = items; }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MinViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_min,viewGroup,false);
        return new MinViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MinViewHolder holder, int position) {
        holder.codProducto.setText(items.get(position).getCodProducto());
        holder.categoria.setText(items.get(position).getCatProducto());
        holder.descripcion.setText(items.get(position).getDespProducto());
        holder.inventario.setText(String.valueOf(items.get(position).getLimProducto()));
        holder.cantidad.setText(String.valueOf(items.get(position).getCantProducto()));
    }
}
