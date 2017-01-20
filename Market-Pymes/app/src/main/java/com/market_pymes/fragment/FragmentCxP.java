package com.market_pymes.fragment;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.market_pymes.R;
import com.market_pymes.Single.Globals;
import com.market_pymes.Volley.VolleyS;
import com.market_pymes.adapter.adapterCxP;
import com.market_pymes.helper.InternetStatus;
import com.market_pymes.helper.JsonHelper;
import com.market_pymes.model.CxP;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.market_pymes.R.id.reciclerxPagar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL;

public class FragmentCxP extends Fragment {
    private String valor;
    private EditText value;
    public ImageButton CxP;
    private RequestQueue fRequestQueue;
    // Progress Dialog
    private ProgressDialog pDialog;
    private RecyclerView recyclerCxP;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    public FragmentCxP() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        VolleyS volley = VolleyS.getInstance(getActivity());
        fRequestQueue = volley.getRequestQueue();
        View viewRoot = inflater.inflate(R.layout.frag_cxp, container, false);

        value = (EditText) viewRoot.findViewById(R.id.valueCxP);
        CxP = (ImageButton) viewRoot.findViewById(R.id.btnCxP);
        recyclerCxP = (RecyclerView) viewRoot.findViewById(reciclerxPagar);

        layoutManager = new LinearLayoutManager(getActivity());


        CxP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valor = value.getText().toString();
                valor = "%" + valor + "%";

                if (!valor.isEmpty()){
                    if (InternetStatus.isOnline(getActivity())) try {
                        pDialog = new ProgressDialog(getActivity());
                        pDialog.setMessage("Conectando a su cuenta...");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(true);
                        pDialog.show();

                        Request(valor);

                        pDialog.dismiss();
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getActivity(), "El valor no ha generado resultados", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        Toast toast = Toast.makeText(getActivity(), "La conexión de datos falló", Toast.LENGTH_SHORT);
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

    private void Request (final String valor){
        final String url = "http://demomp2015.yoogooo.com/demoMovil/Web-Service/CxP.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            response = response.replace("\"+\",", "");
                            response = response.replace(",\"+\"", "");
                            JSONArray json = new JSONArray(response);
                            ArrayList<CxP> listCuentas = new ArrayList<>();
                            for (int i=0; i < json.length(); i++){
                                CxP cuenta = new CxP();
                                JSONObject row = json.getJSONObject(i);
                                cuenta.setIdFac(Integer.parseInt(row.getString("id_factura")));
                                cuenta.setFecPago(Integer.parseInt(row.getString("fechas_pago")));
                                cuenta.setProveedor(row.getString("proveedor"));
                                cuenta.setDeud(Float.parseFloat(row.getString("deuda")));
                                cuenta.setCuot(Float.parseFloat(row.getString("cuota")));
                                cuenta.setAbono(Float.parseFloat(row.getString("abonado")));
                                cuenta.setSald(Float.parseFloat(row.getString("saldo")));
                                listCuentas.add(cuenta);

                                recyclerCxP.setHasFixedSize(true);
                                recyclerCxP.setLayoutManager(layoutManager);

                                adapter = new adapterCxP(listCuentas);
                                recyclerCxP.setAdapter(adapter);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
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
                params.put("valor",valor);
                return params;
            }
        };
        fRequestQueue.add(postRequest);
    }

}
