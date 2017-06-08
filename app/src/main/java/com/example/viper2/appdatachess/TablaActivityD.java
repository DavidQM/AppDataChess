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

public class TablaActivityD extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    SharedPreferences prefs;//nombre de las preferencias
    SharedPreferences.Editor editor;
    String username,correo,usuario;


    //firebase
    DatabaseReference pRef,rRef,tRef;

    int cont=0;
    int p=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_d);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);//traer informacion
        editor = prefs.edit();//traemos el editor
        */

        Bundle box =getIntent().getExtras();
        //username = String.valueOf(box.getString("username"));
        //correo= String.valueOf(box.getString("correo"));
        usuario= String.valueOf(box.getString("usuario"));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        pRef=database.getReference("Participante");
        rRef= database.getReference("Torneo").child("Rondas");//.child(String.valueOf(1));
        tRef= database.getReference("Torneo").child("Puntos");//
        final  ArrayList<FData> lista_1 = new ArrayList<FData>();
        final ArrayList<PData> lista_2 = new ArrayList<PData>();
        final ArrayList<GameFData> lista_3 = new ArrayList<GameFData>();
        final Tabla tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla));
        final Double [] box_1= new Double[6];
        final Double [] box_2= new Double[6];
        final Double [] box_3= new Double[6];
        final Double [] box_4= new Double[6];
        final Double [] box_5= new Double[6];
        final Double [] box_6= new Double[6];

        //tabla.agregarCabecera(R.array.cabecera_tabla_list);

        pRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cont= (int) dataSnapshot.getChildrenCount();//numero de usuarios dinamico
                // Log.i("cont = ",String.valueOf(cont));
                // Log.i("key = ",dataSnapshot.getKey());
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    lista_1.add(userSnapshot.getValue(FData.class));
                }
                DelaySegundos(1);// se le da tiempo para buscar y descargar de la bd

                tRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                            lista_2.add(userSnapshot.getValue(PData.class));
                        }
                        //aqui voy
                        /*
                        for(p = 0; p < cont-2; p++) {
                            rRef.child(String.valueOf(p+1)).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                                        lista_3.add(userSnapshot.getValue(GameFData.class));
                                    }
                                    */
                                    DelaySegundos(1);// se le da tiempo para buscar y descargar de la bd

                                    tabla.agregarCabecera(R.array.cabecera_tabla);

                                    for(int i = 0; i < cont; i++)
                                    {
                                        ArrayList<String> elementos = new ArrayList<String>();
                                        elementos.add("Jugador "+ lista_2.get(i).getId());
                                        //elementos.add(lista_1.get(Integer.parseInt(lista_3.get(i).getId())).getNombre());
                                        switch (Integer.parseInt(lista_2.get(i).getId()))
                                        {
                                            case 1:
                                                elementos.add(lista_1.get(0).getNombre());
                                                elementos.add(lista_1.get(0).getClub());
                                                break;
                                            case 2:
                                                elementos.add(lista_1.get(1).getNombre());
                                                elementos.add(lista_1.get(1).getClub());
                                                break;
                                            case 3:
                                                elementos.add(lista_1.get(2).getNombre());
                                                elementos.add(lista_1.get(2).getClub());
                                                break;
                                            case 4:
                                                elementos.add(lista_1.get(3).getNombre());
                                                elementos.add(lista_1.get(3).getClub());
                                                break;
                                            case 5:
                                                elementos.add(lista_1.get(4).getNombre());
                                                elementos.add(lista_1.get(4).getClub());
                                                break;
                                            case 6:
                                                elementos.add(lista_1.get(5).getNombre());
                                                elementos.add(lista_1.get(5).getClub());
                                                break;

                                        }
                                        /*
                                        for(int j = 0; j < cont/2; j++) {

                                            switch (Integer.parseInt(lista_3.get(j).getResult()))
                                            {
                                                case 1://gana blancas
                                                    //lista_3.get(j).getId_n();
                                                    switch (Integer.parseInt(lista_3.get(j).getId_b())){
                                                        case 1:
                                                            box_1[p]=1.0;
                                                            break;
                                                        case 2:
                                                            box_2[p]=1.0;
                                                            break;
                                                        case 3:
                                                            box_3[p]=1.0;
                                                            break;
                                                        case 4:
                                                            box_4[p]=1.0;
                                                            break;
                                                        case 5:
                                                            box_5[p]=1.0;
                                                            break;

                                                    }
                                                    switch (Integer.parseInt(lista_3.get(p).getId_n())){
                                                        case 1:
                                                            box_1[p]=0.0;
                                                            break;
                                                        case 2:
                                                            box_2[p]=0.0;
                                                            break;
                                                        case 3:
                                                            box_3[p]=0.0;
                                                            break;
                                                        case 4:
                                                            box_4[p]=0.0;
                                                            break;
                                                        case 5:
                                                            box_5[p]=0.0;
                                                            break;

                                                    }

                                                    break;
                                                case 2://gana negras
                                                    switch (Integer.parseInt(lista_3.get(j).getId_n())){
                                                        case 1:
                                                            box_1[p]=1.0;
                                                            break;
                                                        case 2:
                                                            box_2[p]=1.0;
                                                            break;
                                                        case 3:
                                                            box_3[p]=1.0;
                                                            break;
                                                        case 4:
                                                            box_4[p]=1.0;
                                                            break;
                                                        case 5:
                                                            box_5[p]=1.0;
                                                            break;

                                                    }
                                                    switch (Integer.parseInt(lista_3.get(j).getId_b())){
                                                        case 1:
                                                            box_1[p]=0.0;
                                                            break;
                                                        case 2:
                                                            box_2[p]=0.0;
                                                            break;
                                                        case 3:
                                                            box_3[p]=0.0;
                                                            break;
                                                        case 4:
                                                            box_4[p]=0.0;
                                                            break;
                                                        case 5:
                                                            box_5[p]=0.0;
                                                            break;

                                                    }
                                                    break;
                                                case 3://empate
                                                    switch (Integer.parseInt(lista_3.get(j).getId_n())){
                                                        case 1:
                                                            box_1[p]=0.5;
                                                            break;
                                                        case 2:
                                                            box_2[p]=0.5;
                                                            break;
                                                        case 3:
                                                            box_3[p]=0.5;
                                                            break;
                                                        case 4:
                                                            box_4[p]=0.5;
                                                            break;
                                                        case 5:
                                                            box_5[p]=0.5;
                                                            break;
                                                    }
                                                    switch (Integer.parseInt(lista_3.get(j).getId_b())){
                                                        case 1:
                                                            box_1[p]=0.5;
                                                            break;
                                                        case 2:
                                                            box_2[p]=0.5;
                                                            break;
                                                        case 3:
                                                            box_3[p]=0.5;
                                                            break;
                                                        case 4:
                                                            box_4[p]=0.5;
                                                            break;
                                                        case 5:
                                                            box_5[p]=0.5;
                                                            break;
                                                    }
                                                    break;
                                                case 0://no se ha jugado
                                                    switch (Integer.parseInt(lista_3.get(j).getId_n())){
                                                        case 1:
                                                            box_1[p]=0.0;
                                                            break;
                                                        case 2:
                                                            box_2[p]=0.0;
                                                            break;
                                                        case 3:
                                                            box_3[p]=0.0;
                                                            break;
                                                        case 4:
                                                            box_4[p]=0.0;
                                                            break;
                                                        case 5:
                                                            box_5[p]=0.0;
                                                            break;
                                                    }
                                                    switch (Integer.parseInt(lista_3.get(j).getId_b())){
                                                        case 1:
                                                            box_1[p]=0.0;
                                                            break;
                                                        case 2:
                                                            box_2[p]=0.0;
                                                            break;
                                                        case 3:
                                                            box_3[p]=0.0;
                                                            break;
                                                        case 4:
                                                            box_4[p]=0.0;
                                                            break;
                                                        case 5:
                                                            box_5[p]=0.0;
                                                            break;
                                                    }
                                                    break;
                                            }//fin sw grande
                                        }
                                        Log.i("contador=", String.valueOf(cont));
                                        Log.i("p =", String.valueOf(p));

                                        switch (i){
                                            case 0:
                                                elementos.add(String.valueOf(box_1[0]));
                                                elementos.add(String.valueOf(box_1[1]));
                                                elementos.add(String.valueOf(box_1[2]));
                                                elementos.add(String.valueOf(box_1[3]));
                                                elementos.add(String.valueOf(box_1[4]));
                                                elementos.add(String.valueOf(box_1[4]));
                                                break;
                                            case 1:
                                                elementos.add(String.valueOf(box_2[0]));
                                                elementos.add(String.valueOf(box_2[1]));
                                                elementos.add(String.valueOf(box_2[2]));
                                                elementos.add(String.valueOf(box_2[3]));
                                                elementos.add(String.valueOf(box_2[4]));
                                                elementos.add(String.valueOf(box_2[4]));
                                                break;
                                            case 2:
                                                elementos.add(String.valueOf(box_3[0]));
                                                elementos.add(String.valueOf(box_3[1]));
                                                elementos.add(String.valueOf(box_3[2]));
                                                elementos.add(String.valueOf(box_3[3]));
                                                elementos.add(String.valueOf(box_3[4]));
                                                elementos.add(String.valueOf(box_3[4]));
                                                break;
                                            case 3:
                                                elementos.add(String.valueOf(box_4[0]));
                                                elementos.add(String.valueOf(box_4[1]));
                                                elementos.add(String.valueOf(box_4[2]));
                                                elementos.add(String.valueOf(box_4[3]));
                                                elementos.add(String.valueOf(box_4[4]));
                                                elementos.add(String.valueOf(box_4[4]));
                                                break;
                                            case 4:
                                                elementos.add(String.valueOf(box_5[0]));
                                                elementos.add(String.valueOf(box_5[1]));
                                                elementos.add(String.valueOf(box_5[2]));
                                                elementos.add(String.valueOf(box_5[3]));
                                                elementos.add(String.valueOf(box_5[4]));
                                                elementos.add(String.valueOf(box_5[4]));
                                                break;
                                        }
                                        */
                                        elementos.add("R1 [" + i + ", 1]");
                                        elementos.add("R2 [" + i + ", 2]");
                                        elementos.add("R3 [" + i + ", 3]");
                                        elementos.add("R4 [" + i + ", 4]");
                                        elementos.add("R5 [" + i + ", 5]");
                                        elementos.add("R6 [" + i + ", 6]");

                                        tabla.agregarFilaTabla(elementos);
                                    }
                                /*
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });

                        }
                        */
                       }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


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
        /*
        Tabla tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla);
        for(int i = 0; i < 16; i++)
        {
            ArrayList<String> elementos = new ArrayList<String>();
            elementos.add(Integer.toString(i));
            elementos.add("Casilla [" + i + ", 0]");
            elementos.add("Casilla [" + i + ", 1]");
            elementos.add("Casilla [" + i + ", 2]");
            elementos.add("Casilla [" + i + ", 3]");
            elementos.add("Casilla [" + i + ", 4]");
            tabla.agregarFilaTabla(elementos);
        }
        */
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
        getMenuInflater().inflate(R.menu.tabla_activity_d, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       /*
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
                intent = new Intent (TablaActivityD.this, MainActivityD.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_game:

                intent = new Intent (TablaActivityD.this, GameActivityD.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();

                return true;
            case R.id.nav_table:
                if (usuario=="tres"){
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }else {
                    intent = new Intent(TablaActivityD.this, TablaActivityD.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }
                return true;
            case R.id.nav_list:
                if (usuario=="uno") {
                    intent = new Intent(TablaActivityD.this, ListActivityD.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.nav_juez:
                if (usuario=="tres") {
                    intent = new Intent(TablaActivityD.this, JuezActivityD.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }
                return true;
           /* case R.id.nav_config:

                intent = new Intent (TablaActivityD.this, PerfilActivityD.class);
                intent.putExtra("username", username);
                intent.putExtra("correo", correo);
                startActivity(intent);
                finish();

                return true; */

            case R.id.nav_logout:
                /*intent = new Intent (MainActivityD.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Sesión cerrada", Toast.LENGTH_SHORT).show();
                finish();*/
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TablaActivityD.this, LoginActivity.class);
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
