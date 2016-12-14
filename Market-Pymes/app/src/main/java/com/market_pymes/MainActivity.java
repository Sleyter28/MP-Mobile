package com.market_pymes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.market_pymes.Json.JsonParser;
import com.market_pymes.Single.Globals;
import com.market_pymes.helper.InternetStatus;

import org.apache.http.message.BasicNameValuePair;
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
    private JsonParser jParser = new JsonParser();
    // url to get all products list
    private final String url_loginS1 = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/loginS1.php";
    private final String url_loginS2 = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/loginS2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmail = (EditText) findViewById(R.id.txtEmail);
                etPassword = (EditText) findViewById(R.id.txtPass);
                email = etEmail.getText().toString();
                email = email.toUpperCase();
                password = etPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()){
                    if (IntSts.isOnline(MainActivity.this)){
                        new AttemptLogin().execute(email);
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
            try {
                List params = new ArrayList();
                params.add(new BasicNameValuePair("email", email));
                JSONObject json = jParser.makeHttpRequest(url_loginS1, "POST", params);
                String DB_name = json.getString("DB_name");
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("DB_name", DB_name));
                JSONObject Json = jParser.makeHttpRequest(url_loginS2, "POST", params);
                String db_name = Json.getString("DB_name");

                Globals DataBase = Globals.getInstance();
                DataBase.setDB(DB_name);
                DataBase.setId_company(Json.getString("id_company"));
                DataBase.setId_user(Json.getString("id_user"));
                DataBase.setUser_name(Json.getString("user"));

                pDialog.dismiss();
                return db_name;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String db_name) {
            pDialog.dismiss();
            if (db_name != null){
                Intent home = new Intent(MainActivity.this,home.class);
                finish();
                startActivity(home);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "email o contraseña incorrectos", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
