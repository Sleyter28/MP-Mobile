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

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.market_pymes.R;
        import com.market_pymes.Single.Globals;
        import com.market_pymes.Volley.VolleyS;
        import com.market_pymes.adapter.adapterInventario;
import com.market_pymes.adapter.adapterMin;
import com.market_pymes.helper.InternetStatus;
        import com.market_pymes.model.Inventario;
import com.market_pymes.model.Min;

import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class Fragment_Inventarios extends Fragment {
    private String valor;
    private EditText value;
    private ProgressDialog pDialog;
    private RequestQueue requestQueue;
    public ImageButton buscar;
    private RecyclerView recyclerViewInventario;
    private RecyclerView.LayoutManager layoutManagerInventario;
    private RecyclerView.Adapter adapterInventario;


    private RecyclerView recycler2;
    private RecyclerView.LayoutManager lmanager;
    private RecyclerView.Adapter adapter2;

    public Fragment_Inventarios() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        VolleyS volley = VolleyS.getInstance(getActivity());

        requestQueue = volley.getRequestQueue();
        View viewRoot = inflater.inflate(R.layout.fragment_inventarios, container, false);

        value = (EditText) viewRoot.findViewById(R.id.valueSearch);

        buscar = (ImageButton) viewRoot.findViewById(R.id.btnInventario);

        recyclerViewInventario = (RecyclerView) viewRoot.findViewById(R.id.reciclerSearch);

        layoutManagerInventario = new LinearLayoutManager(getActivity());

        recycler2 = (RecyclerView) viewRoot.findViewById(R.id.reciclerMinimo);
        lmanager = new LinearLayoutManager(getActivity());

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valor = value.getText().toString();
                valor = "%" + valor + "%";

                if (!valor.isEmpty()) {
                    if (InternetStatus.isOnline(getActivity())) try {
                        pDialog = new ProgressDialog(getActivity());
                        pDialog.setMessage("Conectando a su cuenta");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(true);
                        pDialog.show();

                        Request(valor);
                        Request2();

                        pDialog.dismiss();
                    } catch (Exception e){
                        Toast toast = Toast.makeText(getActivity(),"El valor no ha generado resultados", Toast.LENGTH_SHORT);
                        toast.show();
                    } else{
                        Toast toast = Toast.makeText(getActivity(),"La conexión de datos falló", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(),"El ingreso de los datos es requerido", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return viewRoot;
    }

    private void Request(final String valor){
        final String url = "http://demomp2015.yoogooo.com/demoMovil/Web-Service/inventario.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            response = response.replace("\"+\",", "");
                            response = response.replace(",\"+\"", "");
                            JSONArray json = new JSONArray(response);
                            ArrayList<Inventario> listInventario = new ArrayList<>();
                            for (int i=0; i<json.length(); i++){
                                Inventario inventario = new Inventario();
                                JSONObject row = json.getJSONObject(i);
                                inventario.setNomProducto(row.getString("producto_nombre"));
                                inventario.setCatProducto(row.getString("categoria"));
                                inventario.setDesProducto(row.getString("producto_descripcion"));
                                inventario.setPrecProducto(Float.parseFloat(row.getString("precio")));
                                inventario.setCantProducto(Integer.parseInt(row.getString("inventario")));

                                listInventario.add(inventario);

                                recyclerViewInventario.setHasFixedSize(true);
                                recyclerViewInventario.setLayoutManager(layoutManagerInventario);

                                adapterInventario = new adapterInventario(listInventario);
                                recyclerViewInventario.setAdapter(adapterInventario);
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
        requestQueue.add(postRequest);
    }

    //Obtiene los productos con existencias mínimas
    private void Request2(){
        final String url = "http://demomp2015.yoogooo.com/demoMovil/Web-Service/existenciaMin.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            response = response.replace("\"+\",", "");
                            response = response.replace(",\"+\"", "");
                            JSONArray json = new JSONArray(response);
                            ArrayList<Min> listMinimos = new ArrayList<>();
                            for (int i=0; i<json.length(); i++){
                                Min minimos = new Min();
                                JSONObject row = json.getJSONObject(i);
                                minimos.setCodProducto(row.getString("Cod"));
                                minimos.setCatProducto(row.getString("categoria"));
                                minimos.setDespProducto(row.getString("producto_descripcion"));
                                minimos.setLimProducto(Integer.parseInt(row.getString("limite")));
                                minimos.setCantProducto(Integer.parseInt(row.getString("inventario")));

                                listMinimos.add(minimos);

                                recycler2.setHasFixedSize(true);
                                recycler2.setLayoutManager(lmanager);

                                adapter2 = new adapterMin(listMinimos);
                                recycler2.setAdapter(adapter2);
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
                params.put("DB_name",DataBase.getDB());
                params.put("id_company",DataBase.getId_company());
                return params;
            }
        };
        requestQueue.add(postRequest);
    }

}
