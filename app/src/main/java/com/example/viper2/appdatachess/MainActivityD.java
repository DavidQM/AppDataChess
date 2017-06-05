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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivityD extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    SharedPreferences prefs;//nombre de las preferencias
    SharedPreferences.Editor editor;
    String username,correo,usuario,TAG;

    DatabaseReference myRef,pRef;
    FData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_d);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);//traer informacion
        editor = prefs.edit();//traemos el editor

        Bundle box =getIntent().getExtras();
        username = String.valueOf(box.getString("username"));
        correo= String.valueOf(box.getString("correo"));
        usuario= String.valueOf(box.getString("usuario"));

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         /*
         user = new FData(String.valueOf(4),"nombre","asdas@asda","abc123","Marinillos","12323");
         myRef = database.getReference("Participante").child(String.valueOf(4));
         myRef.setValue(user);
         */
        /*
        // pruba funcional 05 -06-2017
        pRef= database.getReference("Participante");
        pRef.child(String.valueOf(4)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FData usera = dataSnapshot.getValue(FData.class);
                Log.i("Prueba",dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
        pRef= database.getReference("Participante");
       // pRef.child(String.valueOf(4)).addValueEventListener(new ValueEventListener() {
        pRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FData> lista = new ArrayList<FData>();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    lista.add(userSnapshot.getValue(FData.class));
                }
                if (dataSnapshot.child(String.valueOf(4)).exists()){
                    FData usera = dataSnapshot.child(String.valueOf(4)).getValue(FData.class);
                    Log.i("Prueba",dataSnapshot.toString());

                    Toast.makeText(getApplicationContext(),usera.getNombre(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), usera.getCorreo(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), usera.getElo(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), usera.getClub(), Toast.LENGTH_SHORT).show();
                }
                /*

               */
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
        getMenuInflater().inflate(R.menu.main_activity_d, menu);
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
                //Toast.makeText(getApplicationContext(), usuario, Toast.LENGTH_SHORT).show();
                intent = new Intent (MainActivityD.this, MainActivityD.class);
                intent.putExtra("username", username);
                intent.putExtra("correo", correo);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_game:
                intent = new Intent (MainActivityD.this, GameActivityD.class);
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
                    intent = new Intent(MainActivityD.this, TablaActivityD.class);
                    intent.putExtra("username", username);
                    intent.putExtra("correo", correo);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }
                return true;
            case R.id.nav_list:
                if (usuario=="uno") {
                    intent = new Intent(MainActivityD.this, ListActivityD.class);
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
                    intent = new Intent(MainActivityD.this, JuezActivityD.class);
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
                intent = new Intent (MainActivityD.this, PerfilActivityD.class);
                intent.putExtra("username", username);
                intent.putExtra("correo", correo);
                startActivity(intent);
                finish();
                */
                return true;

            case R.id.nav_logout:
                intent = new Intent (MainActivityD.this, LoginActivity.class);
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
