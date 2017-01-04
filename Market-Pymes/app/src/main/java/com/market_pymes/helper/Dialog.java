package com.market_pymes.helper;


import android.app.ProgressDialog;
import android.content.Context;

public class Dialog {
    Context context;
    private ProgressDialog pDialog;

    public Dialog(Context context) {
        this.context = context;
    }

    public void init() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Conectando a su cuenta...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void close() {
        pDialog.dismiss();
    }
}
