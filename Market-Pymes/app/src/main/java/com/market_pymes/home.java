package com.market_pymes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.market_pymes.Single.Globals;
import com.market_pymes.adapter.SlidingMenuAdapter;
import com.market_pymes.fragment.Frag_Cierre;
import com.market_pymes.fragment.FragmentHome;
import com.market_pymes.fragment.Fragment2;
import com.market_pymes.fragment.FragmentCxC;
import com.market_pymes.fragment.FragmentCxP;
import com.market_pymes.fragment.Fragment_Inventarios;
import com.market_pymes.model.ItemSlideMenu;

import java.util.ArrayList;
import java.util.List;

public class home extends ActionBarActivity {

    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //Init component
        listViewSliding = (ListView) findViewById(R.id.lv_sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        listSliding = new ArrayList<>();

        //Add item for sliding list
        listSliding.add(new ItemSlideMenu(R.drawable.home_icon, "   Home"));
        //listSliding.add(new ItemSlideMenu(R.drawable.facturacion, "  Facturación"));
        listSliding.add(new ItemSlideMenu(R.drawable.cxc, "  Cuentas por Cobrar"));
        listSliding.add(new ItemSlideMenu(R.drawable.cxp, "  Cuentas por Pagar"));
        listSliding.add(new ItemSlideMenu(R.drawable.inventario, "  Inventarios"));
        listSliding.add(new ItemSlideMenu(R.drawable.cierre_caja, "  Cierre de Cajas"));

        adapter = new SlidingMenuAdapter(this, listSliding);
        listViewSliding.setAdapter(adapter);
        listViewSliding.setDividerHeight(16);

        //Display icon to open/ close sliding list
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set title
        setTitle(listSliding.get(0).getTitle());
        //item selected
        listViewSliding.setItemChecked(0, true);
        //Close menu
        drawerLayout.closeDrawer(listViewSliding);

        //Display fragment 1 when start
        replaceFragment(0);
        //Handle on item click

        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Set title
                setTitle(listSliding.get(position).getTitle());
                //item selected
                listViewSliding.setItemChecked(position, true);
                //Replace fragment
                replaceFragment(position);
                //Close menu
                drawerLayout.closeDrawer(listViewSliding);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logout) {
            Globals DataBase = Globals.getInstance();
            DataBase.logOut();
            Intent Main = new Intent(home.this,MainActivity.class);
            finish();
            startActivity(Main);
        }

        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //Create method replace fragment

    private void replaceFragment(int pos) {
        Fragment fragment;
        switch (pos) {
            case 0:
                fragment = new FragmentHome();
                break;
            /*case 1:
                fragment = new Fragment2();
                break;*/
            case 1:
                fragment = new FragmentCxC();
                break;
            case 2:
                fragment = new FragmentCxP();
                break;
            case 3:
                fragment = new Fragment_Inventarios();
                break;
            case 4:
                fragment = new Frag_Cierre();
                break;
            default:
                fragment = new FragmentHome();
                break;
        }

        if(null!=fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}

