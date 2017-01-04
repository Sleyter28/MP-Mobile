package com.market_pymes.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.market_pymes.MainActivity;
import com.market_pymes.R;
import com.market_pymes.Single.Globals;
import com.market_pymes.Volley.VolleyS;
import com.market_pymes.helper.Dialog;
import com.market_pymes.helper.InternetStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentCxC extends Fragment {
    private String valor;
    private EditText value;
    private TextView Res;
    public Button CxC;
    private RequestQueue fRequestQueue;
    //private Dialog dialog = new Dialog(getActivity());
    // Progress Dialog
    private ProgressDialog pDialog;

    public FragmentCxC() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        VolleyS volley = VolleyS.getInstance(getActivity());
        fRequestQueue = volley.getRequestQueue();
        View viewRoot = inflater.inflate(R.layout.frag_cxc, container, false);
        value = (EditText) viewRoot.findViewById(R.id.valueCxC);
        Res = (TextView) viewRoot.findViewById(R.id.res);
        CxC = (Button) viewRoot.findViewById(R.id.btnCxC);
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
                        String tmpList = response.replace("[", "");
                        tmpList = tmpList.replace("]", "");
                        tmpList = tmpList.replace(",\"+\",", "#");
                        tmpList = tmpList.replace(",\"+\"", "");
                        List<String> myList = new ArrayList<String>(Arrays.asList(tmpList.split("#")));
                        Res.setText(tmpList);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getActivity(), "el valor no a generado resultados", Toast.LENGTH_SHORT);
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
