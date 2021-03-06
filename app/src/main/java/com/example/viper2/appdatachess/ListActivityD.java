package com.example.viper2.appdatachess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivityD extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    SharedPreferences prefs;//nombre de las preferencias
    SharedPreferences.Editor editor;
    String username,correo,usuario;


    //firebase
    DatabaseReference pRef,rRef;

    int cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_d);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);//traer informacion
        editor = prefs.edit();//traemos el editor*/

        Bundle box =getIntent().getExtras();
        //username = String.valueOf(box.getString("username"));
        //correo= String.valueOf(box.getString("correo"));
        usuario= String.valueOf(box.getString("usuario"));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        pRef=database.getReference("Participante");
        rRef= database.getReference("Torneo").child("Rondas");//.child(String.valueOf(1));

        final Tabla tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla_list));
        //tabla.agregarCabecera(R.array.cabecera_tabla_list);

        pRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cont= (int) dataSnapshot.getChildrenCount();//numero de usuarios dinamico
               // Log.i("cont = ",String.valueOf(cont));
               // Log.i("key = ",dataSnapshot.getKey());
                ArrayList<FData> lista = new ArrayList<FData>();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    lista.add(userSnapshot.getValue(FData.class));
                }

                DelaySegundos(2);// se le da tiempo para buscar y descargar de la bd

                ArrayList<String> elementos = new ArrayList<String>();
                tabla.agregarCabecera(R.array.cabecera_tabla_list);
                for(int i = 0; i < cont; i++)
                {
                    //elementos.add(Integer.toString(i));
                    elementos.add("Jugador "+lista.get(i).getId());
                    elementos.add(lista.get(i).getNombre());
                    elementos.add(lista.get(i).getClub());
                    elementos.add(lista.get(i).getElo());

                    tabla.agregarFilaTabla(elementos);
                    elementos.clear();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
     /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_activity_d, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(ListActivityD.this, LoginActivity.class);
            startActivity(intent);
            finish();
            editor.putInt("login",-1);//sobre escribimos con -1 (desloggeamos)
            editor.commit();//practica 5
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
         switch (item.getItemId()) {
            case R.id.nav_main:
                intent = new Intent (ListActivityD.this, MainActivityD.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_game:

                intent = new Intent (ListActivityD.this, GameActivityD.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();

                return true;
            case R.id.nav_table:
                if (usuario=="tres"){
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }else {
                    intent = new Intent(ListActivityD.this, TablaActivityD.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }
                return true;
            case R.id.nav_list:
                if (usuario=="uno") {
                    intent = new Intent(ListActivityD.this, ListActivityD.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }
                return true;
             case R.id.nav_juez:
                 if (usuario=="tres") {
                     intent = new Intent(ListActivityD.this, JuezActivityD.class);
                     intent.putExtra("usuario", usuario);
                     startActivity(intent);
                     finish();
                 }else{
                     Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                 }
                 return true;
            /*case R.id.nav_config:

                intent = new Intent (ListActivityD.this, PerfilActivityD.class);
                intent.putExtra("username", username);
                intent.putExtra("correo", correo);
                startActivity(intent);
                finish();

                return true;*/
            case R.id.nav_logout:
                /*intent = new Intent (MainActivityD.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Sesión cerrada", Toast.LENGTH_SHORT).show();
                finish();*/
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ListActivityD.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Sesión cerrada", Toast.LENGTH_SHORT).show();
                finish();
                //editor.putInt("login",-1);//sobre escribimos con -1 (desloggeamos)
                //editor.commit();//practica 5
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
    void DelaySegundos (int seg){
        int time= seg*1000;

        try {

            Thread.sleep(time);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

