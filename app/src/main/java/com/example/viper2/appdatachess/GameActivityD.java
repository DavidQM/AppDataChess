package com.example.viper2.appdatachess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//import java.security.cert.PKIXRevocationChecker;

public class GameActivityD extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    SharedPreferences prefs;//nombre de las preferencias
    SharedPreferences.Editor editor;
    String username,correo,usuario;
    MenuItem Op1;

    //firebase
    DatabaseReference myRef,rRef;
    FData user;

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
        usuario= String.valueOf(box.getString("usuario"));
        */
        //firebase gameactivity
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        rRef= database.getReference("Torneo").child("Rondas");//.child(String.valueOf(1));
        rRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> lista = new ArrayList<String>();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                   lista.add(userSnapshot.getValue(String.class));
                }
                Log.i("cont = ",String.valueOf(lista.size()));
            }
                        @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Op1.getTitle("Option1");
        //Op1.toString("Option1");
        //Op1.getActionProvider();
        //int i=1;
        //Op1.setTitle("Option1");
        //onOptionsItemSelected(Op1);

        ///empieza tabla

        Tabla tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla_list));
        tabla.agregarCabecera(R.array.cabecera_game_list);
        for(int i = 0; i < 12; i++)
        {
            ArrayList<String> elementos = new ArrayList<String>();
            elementos.add("R1 [" + i + ", 0]");
            elementos.add("R1 [" + i + ", 1]");
            elementos.add("Mesa" + tabla.getFilas());
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

        menu.add(0, 0, 0, "Ronda 1").setShortcut('3', 'c');
        menu.add(0, 1, 0, "Ronda 2").setShortcut('3', 'c');
        menu.add(0, 2, 0, "Ronda 3").setShortcut('3', 'c');
        menu.add(0, 3, 0, "Ronda 4").setShortcut('3', 'c');
        menu.add(0, 3, 0, "Ronda 5").setShortcut('3', 'c');
        menu.add(0, 4, 0, "Ronda 6").setShortcut('4', 's');


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int cont=6;

       // Toast.makeText(getApplicationContext(),String.valueOf(item), Toast.LENGTH_SHORT).show();
        Tabla tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla_list));
        tabla.eliminarAllTabla();
        tabla.agregarCabecera(R.array.cabecera_game_list);
        ArrayList<String> elementos = new ArrayList<String>();


        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
               // tabla.eliminarAllTabla();
               // tabla.agregarCabecera(R.array.cabecera_game_list);
                for(int i = 0; i < cont; i++)
                {
                    rRef.child(String.valueOf(item.getItemId()+1)).child(getResources().getStringArray(R.array.mesas_array)[i]).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            ArrayList<String> lista = new ArrayList<String>();
                            for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                                lista.add(userSnapshot.getValue(String.class));
                            }
                             Log.i("Prueba",dataSnapshot.toString());
                             Log.i("0 = ",lista.get(0).toString());
                            Log.i("1 = ",lista.get(1).toString());
                            Log.i("2 = ",lista.get(2).toString());
                            Log.i("3 = ",lista.get(3).toString());
                            lista.clear();
                            //Toast.makeText(getApplicationContext(), lista.get(0).toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), lista.get(1).toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), lista.get(2).toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), lista.get(3).toString(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                   //ArrayList<String> elementos = new ArrayList<String>();
                    elementos.add("R1 [" + i + ", 0]");
                    elementos.add("R1 [" + i + ", 1]");
                    elementos.add("Mesa" + tabla.getFilas());
                    tabla.agregarFilaTabla(elementos);
                    elementos.clear();
                }
                //tabla.eliminarAllTabla();
                return true;
            case 1:
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                //  tabla.eliminarAllTabla();
                // tabla.agregarCabecera(R.array.cabecera_game_list);
                for(int i = 0; i < cont; i++)
                {
                    //ArrayList<String> elementos = new ArrayList<String>();
                    elementos.add("R2 [" + i + ", 0]");
                    elementos.add("R2 [" + i + ", 1]");
                    elementos.add("Mesa" + i);
                    tabla.agregarFilaTabla(elementos);
                    elementos.clear();
                }

                return true;
            case 2:
                Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                //  tabla.eliminarAllTabla();
                // tabla.agregarCabecera(R.array.cabecera_game_list);
                for(int i = 0; i < cont; i++)
                {

                    //ArrayList<String> elementos = new ArrayList<String>();
                    elementos.add("R3 [" + i + ", 0]");
                    elementos.add("R3 [" + i + ", 1]");
                    elementos.add("Mesa" + i);
                    tabla.agregarFilaTabla(elementos);
                    elementos.clear();
                }

                return true;
            case 3:
                Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                //  tabla.eliminarAllTabla();
                // tabla.agregarCabecera(R.array.cabecera_game_list);
                for(int i = 0; i < cont; i++)
                {
                   // ArrayList<String> elementos = new ArrayList<String>();
                    elementos.add("R4 [" + i + ", 0]");
                    elementos.add("R4 [" + i + ", 1]");
                    elementos.add("Mesa" + i);
                    tabla.agregarFilaTabla(elementos);
                    elementos.clear();
                }

                return true;
            case 4:
                Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
                //  tabla.eliminarAllTabla();
                // tabla.agregarCabecera(R.array.cabecera_game_list);
                for(int i = 0; i < cont; i++)
                {
                    //ArrayList<String> elementos = new ArrayList<String>();
                    elementos.add("R5 [" + i + ", 0]");
                    elementos.add("R5 [" + i + ", 1]");
                    elementos.add("Mesa" + i);
                    tabla.agregarFilaTabla(elementos);
                    elementos.clear();
                }
                return true;
            case 5:
                Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_SHORT).show();
                //  tabla.eliminarAllTabla();
                // tabla.agregarCabecera(R.array.cabecera_game_list);
                for(int i = 0; i < cont; i++)
                {
                   // ArrayList<String> elementos = new ArrayList<String>();
                    elementos.add("R6 [" + i + ", 0]");
                    elementos.add("R6 [" + i + ", 1]");
                    elementos.add("Mesa" + i);
                    tabla.agregarFilaTabla(elementos);
                    elementos.clear();
                }

                return true;
        }
       // elementos.clear();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.nav_main:
                intent = new Intent (GameActivityD.this, MainActivityD.class);
                intent.putExtra("username", username);
                intent.putExtra("correo", correo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_game:

                intent = new Intent (GameActivityD.this, GameActivityD.class);
                intent.putExtra("username", username);
                intent.putExtra("correo", correo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();

                return true;
            case R.id.nav_table:
                if (usuario=="tres"){
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }else {
                    intent = new Intent(GameActivityD.this, TablaActivityD.class);
                    intent.putExtra("username", username);
                    intent.putExtra("correo", correo);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }
                return true;
            case R.id.nav_list:
                if (usuario=="uno") {
                    intent = new Intent(GameActivityD.this, ListActivityD.class);
                    intent.putExtra("username", username);
                    intent.putExtra("correo", correo);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.nav_juez:
                if (usuario=="tres") {
                    intent = new Intent(GameActivityD.this, JuezActivityD.class);
                    intent.putExtra("username", username);
                    intent.putExtra("correo", correo);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.nav_config:
                /*
                intent = new Intent (GameActivityD.this, PerfilActivityD.class);
                intent.putExtra("username", username);
                intent.putExtra("correo", correo);
                startActivity(intent);
                finish();
                */
                return true;

            case R.id.nav_logout:
                intent = new Intent (GameActivityD.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Sesión cerrada", Toast.LENGTH_SHORT).show();
                finish();
                editor.putInt("login",-1);//sobre escribimos con -1 (desloggeamos)
                editor.commit();//practica 5
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        */
        //return true;

    }
}
