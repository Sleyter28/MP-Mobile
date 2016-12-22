package com.market_pymes.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.market_pymes.R;
import com.market_pymes.Single.Globals;
import com.market_pymes.helper.InternetStatus;
import com.market_pymes.helper.JsonHelper;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class FragmentCxP extends Fragment {
    private String valor, db_name, id_company;
    private EditText value;
    private TextView Resp;
    public Button CxP;
    // Progress Dialog
    private ProgressDialog pDialog;

    public FragmentCxP() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.frag_cxp, container, false);
        value = (EditText) viewRoot.findViewById(R.id.valueInvent);
        Resp = (TextView) viewRoot.findViewById(R.id.Resp);
        final Globals DataBase = Globals.getInstance();
        CxP = (Button) viewRoot.findViewById(R.id.btnCxP);
        CxP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valor = value.getText().toString();
                valor = "%" + valor + "%";
                db_name = DataBase.getDB();
                id_company = DataBase.getId_company();
                if (!valor.isEmpty() && !db_name.isEmpty()){
                    if (InternetStatus.isOnline(getActivity())){
                        try {
                            new FragmentCxP.CuentasXPagar().execute(db_name, valor);
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

    class CuentasXPagar extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Obteniendo datos...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            JsonHelper JsonHelper = new JsonHelper();
            try {
                List param = new ArrayList();
                param.add(new BasicNameValuePair("DB_name", db_name));
                param.add(new BasicNameValuePair("valor", valor));
                param.add(new BasicNameValuePair("id_company", id_company));
                String url_home = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/CxP.php";
                String json = JsonHelper.HttpRequest(url_home, param);
                pDialog.dismiss();
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String json) {
            if (json != null){
                Resp.setText(json);
            } else {
                Toast toast = Toast.makeText(getActivity(), "El valor no a generado resultados", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

}
