package com.market_pymes.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.market_pymes.Json.JsonParser;
import com.market_pymes.R;
import com.market_pymes.Single.Globals;
import com.market_pymes.helper.InternetStatus;
import com.market_pymes.helper.JsonHelper;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class Frag_Cierre extends Fragment {
    private JsonParser jParser = new JsonParser();
    private String DB_name, id_company;
    private ProgressDialog pDialog;
    private TextView respu;

    public Frag_Cierre() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.frag_cierre, container, false);
        respu = (TextView) viewRoot.findViewById(R.id.respu);
        final Globals DataBase = Globals.getInstance();
        DB_name = DataBase.getDB();
        id_company = DataBase.getId_company();
        if (!DB_name.isEmpty()){
            if (InternetStatus.isOnline(getActivity())){
                try {
                    new CierreCaja().execute(DB_name, id_company);
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

        return viewRoot;
    }

    class CierreCaja extends AsyncTask<String, String, String> {
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
                param.add(new BasicNameValuePair("DB_name", DB_name));
                param.add(new BasicNameValuePair("id_company", id_company));
                String url_home = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/cierreCaja.php";
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
                respu.setText(json);
            } else {
                Toast toast = Toast.makeText(getActivity(), "El valor no a generado resultados", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

}
