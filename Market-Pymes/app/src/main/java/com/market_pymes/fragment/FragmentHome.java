package com.market_pymes.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.market_pymes.Json.JsonParser;
import com.market_pymes.R;
import com.market_pymes.Single.Globals;
import com.market_pymes.helper.InternetStatus;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;


public class FragmentHome extends Fragment {
    private JsonParser jParser = new JsonParser();
    private TextView Vcont, Vcred;
    private String date, DB_name, id_company;
    private String[] xValues = {"Contado", "Credito"};
    private float[] yValues;
    private float Cred = 0;
    private float Cont = 0;
    PieChart mChart;

    public static  final int[] COLORS = {
            Color.rgb(84,124,101), Color.rgb(64,64,64)
    };

    public FragmentHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_home, container, false);
        mChart = (PieChart) rootView.findViewById(R.id.graf);
        mChart.setRotationEnabled(true);

        date = getdate();
        Globals DataBase = Globals.getInstance();
        DB_name = DataBase.getDB();
        id_company = DataBase.getId_company();
        Vcont = (TextView) rootView.findViewById(R.id.Vcontado);
        Vcred = (TextView) rootView.findViewById(R.id.Vcredito);

        if (InternetStatus.isOnline(getActivity())){
            new Ccontado().execute(date, DB_name);
            new Ccredito().execute(date, DB_name);
        } else {
            Toast toast = Toast.makeText(getActivity(), "Se perdió la conexión de datos", Toast.LENGTH_SHORT);
            toast.show();
        }
        return rootView;
    }


    public void setDataForPieChart(float[] yValues, String[] xValues) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < yValues.length; i++)
            yVals1.add(new Entry(yValues[i], i));

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < xValues.length; i++)
            xVals.add(xValues[i]);
        // create pieDataSet
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        // Added My Own colors
        for (int c : COLORS) colors.add(c);
        dataSet.setColors(colors);
        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(xVals, dataSet);
        //   data.setValueFormatter(new DefaultValueFormatter());
        //   data.setValueFormatter(new PercentFormatter());
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        // refresh/update pie chart
        mChart.invalidate();
        // animate piechart
        mChart.animateXY(1400, 1400);
        // Legends to show on bottom of the graph
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }


    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("######,##0");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value) + "";
        }
    }


    private String getdate() {
        Date d = new Date();
        CharSequence fecha  = DateFormat.format("yyyy/MM/dd", d.getTime());
        String Fecha = fecha.toString();
        return Fecha;
    }

    class Ccontado extends AsyncTask<String, String, String> {
        protected String doInBackground(String... args) {
            try {
                List param = new ArrayList();
                param.add(new BasicNameValuePair("DB_name", DB_name));
                param.add(new BasicNameValuePair("fecha", date));
                param.add(new BasicNameValuePair("tipo", "1"));
                param.add(new BasicNameValuePair("id_company", id_company));
                String url_home = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/home.php";
                JSONObject json = jParser.makeHttpRequest(url_home, "POST", param);
                String valor = json.getString("monto");
                return valor;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String valor) {
            if (valor != null){
                Cont = Float.parseFloat(valor);
                DecimalFormat formateador = new DecimalFormat("###,###,###.##");
                Vcont.setText("Monto total: " + formateador.format (Cont));
            }
        }
    }

    class Ccredito extends AsyncTask<String, String, String> {
        protected String doInBackground(String... args) {
            try {
                List param = new ArrayList();
                param.add(new BasicNameValuePair("DB_name", DB_name));
                param.add(new BasicNameValuePair("fecha", date));
                param.add(new BasicNameValuePair("tipo", "2"));
                param.add(new BasicNameValuePair("id_company", id_company));
                String url_home = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/home.php";
                JSONObject json = jParser.makeHttpRequest(url_home, "POST", param);
                String valor = json.getString("monto");
                return valor;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String valor) {
            if (valor != null){
                Cred = Float.parseFloat(valor);
                DecimalFormat formateador = new DecimalFormat("###,###,###.##");
                Vcred.setText("Monto total: " + formateador.format (Cred));
                float[] yValues = {Cont, Cred};
                setDataForPieChart(yValues, xValues);
            }
        }
    }
}
