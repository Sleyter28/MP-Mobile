package com.market_pymes.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.market_pymes.R;
import com.market_pymes.Single.Globals;
import com.market_pymes.Volley.VolleyS;
import com.market_pymes.adapter.adapterCxC;
import com.market_pymes.helper.InternetStatus;
import com.market_pymes.model.CxC;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.market_pymes.R.id.reciclador;
import static com.market_pymes.R.id.recicler;


public class FragmentCxC extends Fragment {
    private String valor;
    private EditText value;
    public ImageButton CxC;
    private RequestQueue fRequestQueue;
    private ProgressDialog pDialog;

    private RecyclerView recyclerCxC;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    public FragmentCxC() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        VolleyS volley = VolleyS.getInstance(getActivity());
        fRequestQueue = volley.getRequestQueue();
        View viewRoot = inflater.inflate(R.layout.frag_cxc, container, false);

        value = (EditText) viewRoot.findViewById(R.id.valueCxC);
        CxC = (ImageButton) viewRoot.findViewById(R.id.btnCxC);
        recyclerCxC = (RecyclerView) viewRoot.findViewById(recicler);

        layoutManager = new LinearLayoutManager(getActivity());


        CxC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valor = value.getText().toString();
                valor = "%" + valor + "%";
                if (!valor.isEmpty()){
                    if (InternetStatus.isOnline(getActivity())){
                        try {
                            pDialog = new ProgressDialog(getActivity());
                            pDialog.setMessage("Conectando a su cuenta...");
                            pDialog.setIndeterminate(false);
                            pDialog.setCancelable(true);
                            pDialog.show();

                            Request(valor);

                            pDialog.dismiss();
                        } catch (Exception e) {
                            Toast toast = Toast.makeText(getActivity(), "El valor no a generado resultados", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(getActivity(), "conexión de datos fallida", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(), "El ingreso de los datos es requerido", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return viewRoot;
    }

    private void  Request (final String valor) {
        final String url = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/CxC.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            response = response.replace("\"+\",", "");
                            response = response.replace(",\"+\"", "");
                            JSONArray json = new JSONArray(response);
                            ArrayList<CxC> listCuentas = new ArrayList<>();
                            for (int i = 0; i < json.length(); i++) {
                                CxC cuenta = new CxC();
                                JSONObject row = json.getJSONObject(i);
                                cuenta.setIdFactura(Integer.parseInt(row.getString("id_factura")));
                                cuenta.setFechaPago(Integer.parseInt(row.getString("fechas_pago")));
                                cuenta.setName(row.getString("cliente"));
                                cuenta.setDeuda(Float.parseFloat(row.getString("deuda")));
                                cuenta.setCuota(Float.parseFloat(row.getString("cuota")));
                                cuenta.setAbonado(Float.parseFloat(row.getString("abonado")));
                                cuenta.setSaldo(Float.parseFloat(row.getString("saldo")));
                                listCuentas.add(cuenta);

                                List<CxC> prueba = new ArrayList<>();
                                CxC cuen = new CxC();
                                cuen.setIdFactura(12);
                                cuen.setFechaPago(10);
                                cuen.setName("Sleyter Angulo");
                                cuen.setDeuda((float) 12000.00);
                                cuen.setCuota((float) 1200.0);
                                cuen.setAbonado((float) 0.0);
                                cuen.setSaldo((float) 0.0);


                                prueba.add(cuen);

                                Log.d("Lista es: ",prueba.toString());

                                recyclerCxC.setHasFixedSize(true);
                                recyclerCxC.setLayoutManager(layoutManager);

                                adapter = new adapterCxC(listCuentas);
                                recyclerCxC.setAdapter(adapter);

                                //Log.d("List",listCuentas.toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getActivity(), "El valor introducido no ha generado resultados", Toast.LENGTH_SHORT);
                        toast.show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Globals DataBase = Globals.getInstance();
                Map<String, String> params = new HashMap<>();
                params.put("DB_name", DataBase.getDB());
                params.put("valor", valor);
                params.put("id_company", DataBase.getId_company());
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }
}
