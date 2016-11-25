package com.market_pymes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    private EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Get Reference to variables
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.pass);
        final TextView titu = (TextView) findViewById(R.id.tituloMain);
        final TextView res = (TextView) findViewById(R.id.lbEmail);

        Spinner dropdown = (Spinner)findViewById(R.id.listCountries);
        String[] paisesArray = {"México","Guatemala","El Salvador","Honduras","Costa Rica","Nicaragua","Panamá","República Dominicana","Colombia","Perú","Paraguay","Uruguay","Ecuador","Bolivia","Argentina"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, paisesArray);
        dropdown.setAdapter(adapter);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopService(service);
                /*Intent newLog = new Intent(MainActivity.this,home.class);
                startActivity(newLog);*/
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    //your codes here
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        // Operaciones http
                        titu.setText("coneccion correcta");
                        URL url = null;
                        try {
                            url = new URL("http://192.168.0.103/Web-App/get_user.php?email=demo@email.com");
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                            urlConnection.disconnect();
                            BufferedReader r = new BufferedReader(new InputStreamReader(in));
                            StringBuilder total = new StringBuilder();
                            String line;
                            while ((line = r.readLine()) != null) {
                                total.append(line).append('\n');
                            }
                            String result = total.toString();
                            res.setText(result);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        titu.setText("fallo");
                    }

                }

            }
        });
    }


}
