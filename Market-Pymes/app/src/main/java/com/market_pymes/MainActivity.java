package com.market_pymes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.market_pymes.Json.JsonParser;
import com.market_pymes.Single.Globals;
import com.market_pymes.helper.InternetStatus;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private String email;
    private String password;
    private InternetStatus IntSts = new InternetStatus();
    // Progress Dialog
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    JsonParser jParser = new JsonParser();
    // url to get all products list
    private final String url_loginS1 = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/loginS1.php";
    //private final String url_loginS1 = "http://192.168.0.28/Web-Service/loginS1.php";
    private final String url_loginS2 = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/loginS2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final Spinner dropdown = (Spinner)findViewById(R.id.listCountries);
        String[] paisesArray = {"Costa Rica","México","Guatemala","El Salvador","Honduras","Nicaragua","Panamá","República Dominicana","Colombia","Perú","Paraguay","Uruguay","Ecuador","Bolivia","Argentina"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, paisesArray);
        dropdown.setAdapter(adapter);


        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locacion = dropdown.getSelectedItem().toString();
                etEmail = (EditText) findViewById(R.id.email);
                etPassword = (EditText) findViewById(R.id.pass);
                email = etEmail.getText().toString();
                email = email.toUpperCase();
                password = etPassword.getText().toString();
                if (!locacion.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if (IntSts.isOnline(MainActivity.this)){
                        try {
                            new AttemptLogin().execute(email);
                        } catch (Exception e) {
                            Toast toast = Toast.makeText(getApplicationContext(), "email, contraseña o país incorrectos", Toast.LENGTH_SHORT);
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

    class AttemptLogin extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Conectando a su cuenta...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            String user = null;
            try {
                // Preparando parametros
                List params = new ArrayList();
                params.add(new BasicNameValuePair("email", email));
                // consulta al servidor
                JSONObject json = jParser.makeHttpRequest(url_loginS1, "POST", params);
                //String DB_name = json.getString("BD_name");
                String res = json.toString();
                String response[] = res.split("\"");
                String DB_name = response[7];
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("DB_name", DB_name));
                JSONObject Json = jParser.makeHttpRequest(url_loginS2, "POST", params);
                String id_user = Json.getString("cp_id_user_919819828828");
                return DB_name;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String DB_name) {
            pDialog.dismiss();
            if (DB_name != null){
                Globals DataBase = Globals.getInstance();
                DataBase.setDB(DB_name);
                Intent home = new Intent(MainActivity.this,home.class);
                finish();
                startActivity(home);
            }
        }
    }
}
