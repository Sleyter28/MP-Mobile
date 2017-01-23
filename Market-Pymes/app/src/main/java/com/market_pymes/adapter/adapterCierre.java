package com.market_pymes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.market_pymes.R;
import com.market_pymes.model.cierreCaja;

import java.util.List;

/**
 * Created by sleyterangulo on 1/23/17.
 */
public class adapterCierre extends RecyclerView.Adapter<adapterCierre.CierreViewHolder> {
    private List<cierreCaja> items;

    public static class CierreViewHolder extends RecyclerView.ViewHolder{
        public TextView codProducto;
        public TextView categoria;
        public TextView descripcion;
        public TextView inventario;
        public TextView cantidad;
        public TextView precio;

        public CierreViewHolder(View v){
            super(v);
            codProducto = (TextView) v.findViewById(R.id.nomProducto); // monto de caja
            categoria = (TextView) v.findViewById(R.id.catProducto); // fact contado
            descripcion = (TextView) v.findViewById(R.id.serContado); // fact credito
            inventario = (TextView) v.findViewById(R.id.descProducto); // ser contado
            precio = (TextView) v.findViewById(R.id.precProducto); // ser credito
            cantidad = (TextView) v.findViewById(R.id.cantProducto); // gasto
        }
    }

    public adapterCierre(List<cierreCaja> items){this.items = items; }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public adapterCierre.CierreViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_cierre,viewGroup,false);
        return new CierreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CierreViewHolder holder, int position) {
        holder.codProducto.setText(String.valueOf(items.get(position).getCajaInicial()));
        holder.categoria.setText(String.valueOf(items.get(position).getFactContado()));
        holder.descripcion.setText(String.valueOf(items.get(position).getFactCredito()));
        holder.inventario.setText(String.valueOf(items.get(position).getServContado()));
        holder.precio.setText(String.valueOf(items.get(position).getServCredito()));
        holder.cantidad.setText(String.valueOf(items.get(position).getGastos()));
    }


}