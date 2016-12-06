package com.market_pymes.fragment;

import android.app.Fragment;
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

public class FragmentCxC extends Fragment {
    private String valor, db_name;
    private EditText value;
    private TextView Res;
    private Button CxC;
    private InternetStatus IntSts = new InternetStatus();

    public FragmentCxC() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.frag_cxc, container, false);
        value = (EditText) viewRoot.findViewById(R.id.valueCxC);
        Res = (TextView) viewRoot.findViewById(R.id.res);
        final Globals DataBase = Globals.getInstance();
        CxC = (Button) viewRoot.findViewById(R.id.btnCxC);
        CxC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valor = value.getText().toString();
                valor = "%" + valor + "%";
                db_name = DataBase.getDB();
                if (!valor.isEmpty() && !db_name.isEmpty()){
                    if (IntSts.isOnline(getActivity())){
                        try {
                           new CuentasXCobrar().execute(db_name, valor);
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

    class CuentasXCobrar extends AsyncTask<String, String, String> {
        protected String doInBackground(String... args) {
            JsonHelper JsonHelper = new JsonHelper();
            String json = "";
            try {
                List param = new ArrayList();
                param.add(new BasicNameValuePair("DB_name", db_name));
                param.add(new BasicNameValuePair("valor", valor));
                String url_home = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/CxC.php";
                json = JsonHelper.HttpRequest(url_home, param);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String json) {
            if (json != null){
                //Vcont.setText("Monto total: " + json);
                Res.setText(json);
            }
        }
    }


}
