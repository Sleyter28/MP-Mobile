package com.market_pymes.adapter;

/**
 * Created by sleyterangulo on 1/10/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.market_pymes.R;
import com.market_pymes.model.CxC;

import java.util.List;

public class adapterCxC extends RecyclerView.Adapter<adapterCxC.CxCViewHolder>{
    private List<CxC> items;

    public static class CxCViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView numFact;
        public TextView fecha;
        public TextView cliente;
        public TextView deuda;
        public TextView cuota;
        public TextView abono;
        public TextView saldo;


        public CxCViewHolder(View v) {
            super(v);
            numFact = (TextView) v.findViewById(R.id.factNum);
            fecha = (TextView) v.findViewById(R.id.fec);
            cliente = (TextView) v.findViewById(R.id.card_cliente);
            deuda = (TextView) v.findViewById(R.id.card_deuda);
            cuota = (TextView) v.findViewById(R.id.card_cuota);
            abono = (TextView) v.findViewById(R.id.card_abonado);
            saldo = (TextView) v.findViewById(R.id.card_saldo);
        }
    }

    public adapterCxC(List<CxC> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CxCViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_cxc, viewGroup, false);
        return new CxCViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CxCViewHolder holder, int position) {
        holder.numFact.setText(String.valueOf(items.get(position).getIdFactura()));
        holder.fecha.setText(String.valueOf(items.get(position).getFechaPago()));
        holder.deuda.setText(Float.toString(items.get(position).getDeuda()));
        holder.cuota.setText(Float.toString(items.get(position).getCuota()));
        holder.abono.setText(Float.toString(items.get(position).getAbonado()));
        holder.saldo.setText(Float.toString(items.get(position).getSaldo()));
    }
}