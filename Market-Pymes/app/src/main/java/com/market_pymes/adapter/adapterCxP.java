package com.market_pymes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.market_pymes.R;
import com.market_pymes.model.CxP;

import java.util.List;

/**
 * Created by sleyterangulo on 1/20/17.
 */

public class adapterCxP extends RecyclerView.Adapter<adapterCxP.CxPViewHolder> {
    private List<CxP> items;

    public static class CxPViewHolder extends RecyclerView.ViewHolder{
        public TextView numFactura;
        public TextView fecha;
        public TextView proveedor;
        public TextView deuda;
        public TextView couta;
        public TextView abono;
        public TextView saldo;

        public  CxPViewHolder (View v){
            super(v);
            numFactura = (TextView) v.findViewById(R.id.facxPagar);
            fecha = (TextView) v.findViewById(R.id.fecxPagar);
            proveedor = (TextView) v.findViewById(R.id.nomProveedor);
            deuda = (TextView) v.findViewById(R.id.deudaxPagar);
            couta = (TextView) v.findViewById(R.id.cuotaxPagar);
            abono = (TextView) v.findViewById(R.id.abonadoxPagar);
            saldo = (TextView) v.findViewById(R.id.saldoxPagar);
        }
    }

    public  adapterCxP(List<CxP> items){
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CxPViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_cxp, viewGroup,false);
        return new CxPViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CxPViewHolder holder, int position) {
        holder.numFactura.setText(String.valueOf(items.get(position).getIdFac()));
        holder.fecha.setText(String.valueOf(items.get(position).getFecPago()));
        holder.proveedor.setText(String.valueOf(items.get(position).getProveedor()));
        holder.deuda.setText(String.valueOf(items.get(position).getDeud()));
        holder.couta.setText(String.valueOf(items.get(position).getCuot()));
        holder.abono.setText(String.valueOf(items.get(position).getAbono()));
        holder.saldo.setText(String.valueOf(items.get(position).getSald()));
    }
}
