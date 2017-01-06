package com.market_pymes;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.market_pymes.Single.Globals;
import com.market_pymes.Volley.VolleyS;
import com.market_pymes.helper.InternetStatus;
import com.market_pymes.model.CxC;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CuentaXCobrar extends AppCompatActivity {
    private String valor;
    private EditText value;
    private TextView Res;
    public Button CxC;
    private RequestQueue fRequestQueue;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_xcobrar);

        VolleyS volley = VolleyS.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
        value = (EditText) findViewById(R.id.valueCxC);
        Res = (TextView) findViewById(R.id.res);
        CxC = (Button) findViewById(R.id.btnCxC);

        CxC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valor = value.getText().toString();
                valor = "%" + valor + "%";
                if (!valor.isEmpty()){
                    if (InternetStatus.isOnline(getApplicationContext())){
                        try {
                            pDialog = new ProgressDialog(getApplicationContext());
                            pDialog.setMessage("Conectando a su cuenta...");
                            pDialog.setIndeterminate(false);
                            pDialog.setCancelable(true);
                            pDialog.show();

                            Request(valor);
                            pDialog.dismiss();
                        } catch (Exception e) {
                            Toast toast = Toast.makeText(getApplicationContext(), "El valor no a generado resultados", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "conexión de datos fallida", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "El ingreso de los datos es requerido", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void  Request (final String valor) {
        final String url = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/CxC.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json = new JSONArray(response);
                            ArrayList<com.market_pymes.model.CxC> listCuentas = new ArrayList<>();
                            for (int i = 0; i < json.length(); i++) {
                                CxC cuenta = new CxC();
                                JSONObject row = json.getJSONObject(i);
                                cuenta.setIdFactura(Integer.parseInt(row.getString("id_factura")));
                                cuenta.setFechaPago(Integer.parseInt(row.getString("fechas_pago")));
                                cuenta.setName(row.getString("cliente"));
                                cuenta.setDeuda(Integer.parseInt(row.getString("deuda")));
                                cuenta.setCuota(Integer.parseInt(row.getString("cuota")));
                                cuenta.setAbonado(Integer.parseInt(row.getString("abonado")));
                                cuenta.setSaldo(Integer.parseInt(row.getString("saldo")));
                                listCuentas.add(cuenta);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String tmpList = response.replace("[", "");
                        tmpList = tmpList.replace("]", "");
                        tmpList = tmpList.replace("\"+\",", "");
                        tmpList = tmpList.replace(",\"+\"", "");
                        //List<String> myList = new ArrayList<String>(Arrays.asList(tmpList.split("#")));
                        Res.setText(tmpList);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "el valor no a generado resultados", Toast.LENGTH_SHORT);
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
