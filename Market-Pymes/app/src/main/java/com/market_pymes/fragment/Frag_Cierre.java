package com.market_pymes.fragment;

import android.app.DownloadManager;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.market_pymes.Json.JsonParser;
import com.market_pymes.R;
import com.market_pymes.Single.Globals;
import com.market_pymes.Volley.VolleyS;
import com.market_pymes.adapter.adapterCierre;
import com.market_pymes.helper.InternetStatus;
import com.market_pymes.helper.JsonHelper;
import com.market_pymes.model.cierreCaja;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Frag_Cierre extends Fragment {

    private ProgressDialog pDialog;
    private RequestQueue requestQueue;
    private RecyclerView recCierre;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    public Frag_Cierre() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        VolleyS volleyS = VolleyS.getInstance(getActivity());
        requestQueue = volleyS.getRequestQueue();

        View viewRoot = inflater.inflate(R.layout.frag_cierre, container, false);

        recCierre = (RecyclerView) viewRoot.findViewById(R.id.reciclerCierre);
        layoutManager = new LinearLayoutManager(getActivity());

        Request();


        return viewRoot;
    }

    private void Request(){
        final String url = "http://demomp2015.yoogooo.com/demoMovil/Web-Service/cierreCaja.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            float facTempCon = 0;
                            float factTempCre = 0;
                            float servTempCon = 0;
                            float servTempCre = 0;
                            JSONObject json = new JSONObject(response);
                            ArrayList<cierreCaja> cuentas = new ArrayList<>();
                            cierreCaja cierreCaja = new cierreCaja();

                            String caja = json.getString("caja");
                            JSONObject caja1 = new JSONObject(caja);
                            cierreCaja.setCajaInicial(Float.parseFloat(caja1.getString("monto")));

                            //Obteniendo los valores de gastos
                            String gasto = json.getString("gastos");
                            JSONObject gasto1 = new JSONObject(gasto);
                            cierreCaja.setGastos(Float.parseFloat(gasto1.getString("monto")));

                            //Obteniendo los valores de las facturas a credito y contado
                            JSONArray facturas = new JSONArray(json.getString("facturas"));
                            for (int i = 0; i < facturas.length(); i++) {
                                JSONObject row = facturas.getJSONObject(i);
                                if (row.getString("tipo_factura").equals("1")){
                                    facTempCon += Float.parseFloat(row.getString("monto"));
                                } else {
                                    factTempCre += Float.parseFloat(row.getString("monto"));
                                }
                            }
                            cierreCaja.setFactContado(facTempCon);
                            cierreCaja.setFactCredito(factTempCre);

                            //Obteniendo los valores de los servicios a credito y contado
                            JSONArray servicios = new JSONArray(json.getString("Servicios"));
                            for (int j = 0; j < servicios.length(); j++) {
                                JSONObject row = servicios.getJSONObject(j);
                                if (row.getString("tipo_factura").equals("1")){
                                    servTempCon += Float.parseFloat(row.getString("monto"));
                                } else {
                                    servTempCre += Float.parseFloat(row.getString("monto"));
                                }
                            }
                            cierreCaja.setServContado(servTempCon);
                            cierreCaja.setServCredito(servTempCre);


                            //Pasar el objeto cierreCaja al ArrayList
                            cuentas.add(cierreCaja);

                            recCierre.setHasFixedSize(true);
                            recCierre.setLayoutManager(layoutManager);

                            adapter = new adapterCierre(cuentas);
                            recCierre.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getActivity(), "El valor introducido no ha generado resultados", Toast.LENGTH_SHORT);
                        toast.show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Globals DataBase = Globals.getInstance();
                Map<String,String> params = new HashMap<>();
                params.put("id_company",DataBase.getId_company());
                params.put("DB_name",DataBase.getDB());
                return params;
            }
        };
        requestQueue.add(postRequest);
    }


}
