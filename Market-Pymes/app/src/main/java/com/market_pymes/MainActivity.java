package com.market_pymes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.market_pymes.Json.JsonParser;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //declaraciones generales variables e instancias
    private EditText etEmail;
    private EditText etPassword;
    private String email;
    private String password;

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JsonParser jParser = new JsonParser();


    // url to get all products list
    private static String url_login = "http://192.168.0.28/Web/log.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Spinner dropdown = (Spinner)findViewById(R.id.listCountries);
        String[] paisesArray = {"México","Guatemala","El Salvador","Honduras","Costa Rica","Nicaragua","Panamá","República Dominicana","Colombia","Perú","Paraguay","Uruguay","Ecuador","Bolivia","Argentina"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, paisesArray);
        dropdown.setAdapter(adapter);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmail = (EditText) findViewById(R.id.email);
                etPassword = (EditText) findViewById(R.id.pass);
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                new AttemptLogin().execute(email);
            }
        });


    }

    class AttemptLogin extends AsyncTask<String, String, String> {
        // Antes de empezar el background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            int success;
            String success2;

            // Building Parameters
            List params = new ArrayList();
            System.out.println("Email obtenido: "+ email);
            params.add(new BasicNameValuePair("email", email));
            //params.add(new BasicNameValuePair("password", password));

            Log.d("request!", "starting");
            // getting product details by making HTTP request
            JSONObject json = jParser.makeHttpRequest(url_login, "POST", params);

            // check your log for json response
            Log.d("Login attempt", json.toString());
            System.out.println("Login attempt: " + json.toString());


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(MainActivity.this,file_url,Toast.LENGTH_LONG).show();
            }
        }
    }
}
