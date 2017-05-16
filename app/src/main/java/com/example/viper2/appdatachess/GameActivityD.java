package com.example.viper2.appdatachess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivityD extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    SharedPreferences prefs;//nombre de las preferencias
    SharedPreferences.Editor editor;
    String username,correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_d);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);//traer informacion
        editor = prefs.edit();//traemos el editor

        Bundle box =getIntent().getExtras();
        username = String.valueOf(box.getString("username"));
        correo= String.valueOf(box.getString("correo"));
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ///empiza tabla

        Tabla tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla_list));
        tabla.agregarCabecera(R.array.cabecera_tabla_list);
        for(int i = 0; i < 16; i++)
        {
            ArrayList<String> elementos = new ArrayList<String>();
            elementos.add(Integer.toString(i));
            elementos.add("Casilla [" + i + ", 0]");
            elementos.add("Casilla [" + i + ", 1]");
            // elementos.add("Casilla [" + i + ", 2]");
            // elementos.add("Casilla [" + i + ", 3]");
            // elementos.add("Casilla [" + i + ", 4]");
            tabla.agregarFilaTabla(elementos);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.game_activity_d, menu);

        super.onCreateOptionsMenu(menu);

        menu.add(0, 0, 0, "Option1").setShortcut('3', 'c');
        menu.add(0, 1, 0, "Option2").setShortcut('3', 'c');
        menu.add(0, 2, 0, "Option3").setShortcut('3', 'c');
        menu.add(0, 3, 0, "Option4").setShortcut('3', 'c');
        menu.add(0, 4, 0, "Option5").setShortcut('4', 's');
       /*
        SubMenu sMenu = menu.addSubMenu(0, 3, 0, "SubMenu"); //If you want to add submenu
        sMenu.add(0, 4, 0, "SubOption1").setShortcut('5', 'z');
        sMenu.add(0, 5, 0, "SubOption2").setShortcut('5', 'z');
        */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                return true;
            case 2:
                Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                return true;
            case 3:
                Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                return true;
            case 4:
                Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        /*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        */
        return true;

    }
}
