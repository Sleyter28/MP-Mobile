package com.market_pymes.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.market_pymes.R;
import com.market_pymes.model.CxC;

import java.util.ArrayList;

public class RecyclerViewCxC extends RecyclerView.Adapter<RecyclerViewCxC.ViewHolder>{
    private Context context;
    private ArrayList<CxC> listCuentas;
    LayoutInflater mInflater;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView factura;
        public TextView fecha;
        public TextView nombre;
        public TextView deuda;
        public TextView cuota;
        public TextView abonado;
        public TextView saldo;

        public ViewHolder(View itemView) {
            super(itemView);
            factura = (TextView) itemView.findViewById(R.id.card_nun_fact);
            factura.setOnClickListener(this);
            fecha = (TextView) itemView.findViewById(R.id.card_fecha);
            factura.setOnClickListener(this);
            //faltan
        }


        @Override
        public void onClick(View view) {
            if (view == factura) {

            }

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


/*
    @Override
    public void onBindViewHolder(RecyclerViewCxC.ViewHolder holder, int position) {
        String name = listCuentas.get(position).getName();
        holder._nameMedicine.setText(name);
    }*/
/*
    @Override
    public int getItemCount() {
        return listCuentas.size();
    }*/
}
