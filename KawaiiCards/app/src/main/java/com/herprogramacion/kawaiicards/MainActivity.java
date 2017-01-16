package com.herprogramacion.kawaiicards;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    /*
    Declarar instancias globales
     */
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Animes
        List<Anime> items = new ArrayList<>();

        items.add(new Anime(R.drawable.angel, "Angel Beats", 230,"Prueba1"));
        items.add(new Anime(R.drawable.death, "Death Note", 456,"Prueba2"));
        items.add(new Anime(R.drawable.fate, "Fate Stay Night", 342,"Prueba3"));
        items.add(new Anime(R.drawable.nhk, "Welcome to the NHK", 645,"Prueba4"));
        items.add(new Anime(R.drawable.suzumiya, "Suzumiya Haruhi", 459,"Prueba5"));

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new AnimeAdapter(items);
        recycler.setAdapter(adapter);
    }
}
