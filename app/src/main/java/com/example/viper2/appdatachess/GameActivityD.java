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

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.ParseException;
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
    DatabaseReference pRef,rRef;

    int cont=0;

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
        pRef=database.getReference("Participante");
        rRef= database.getReference("Torneo").child("Rondas");//.child(String.valueOf(1));
        rRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cont= (int) dataSnapshot.getChildrenCount();//numero de mesas dinamico
                cont=cont/2+1;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { } });

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
        menu.add(0, 4, 0, "Ronda 5").setShortcut('4', 's');
        //menu.add(0, 5, 0, "Ronda 6").setShortcut('4', 's');
        DelaySegundos(2);
        onOptionsItemSelected(menu.findItem(0));//seleccionamos por defecto ronda 1
        //DelaySegundos(2);// se le da tiempo para buscar y descargar de la bd
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        final int id = item.getItemId();
        final Tabla tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla_list));
        final ArrayList<String> elementos = new ArrayList<String>();
        final ArrayList<GameFData> lista = new ArrayList<GameFData>();
         /*
        final ArrayList<FData> lista_2 = new ArrayList<FData>();
        final String [] prueba= new String[6];
        final int [] numbers= {'1','2','3','4','5','6'};
        final String[] box_0 = {""};
        final String box_1;
        final String box_2;

        pRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    lista_2.add(userSnapshot.getValue(FData.class));
                }
                DelaySegundos(2);// se le da tiempo para buscar y descargar de la bd
                for(int i = 0; i < cont; i++) {
                    prueba[i]=String.valueOf(lista_2.get(i).getNombre());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
        rRef.child(String.valueOf(id+1)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ArrayList<GameFData> lista = new ArrayList<GameFData>();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    lista.add(userSnapshot.getValue(GameFData.class));
                }
                DelaySegundos(2);// se le da tiempo para buscar y descargar de la bd

                tabla.eliminarAllTabla();
                tabla.agregarCabecera(R.array.cabecera_game_list);
                switch (id) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                        //  tabla.eliminarAllTabla();
                        // tabla.agregarCabecera(R.array.cabecera_game_list);
                        /*
                        for(int i = 0; i < cont; i++) {
                            numbers[i]=Integer.parseInt(lista.get(i).getId());
                        }
                        */
                        for(int i = 0; i < cont; i++) {
                            //ArrayList<String> elementos = new ArrayList<String>();
                            //elementos.add(prueba[i].toString());
                            //int k= Integer.valueOf(lista.get(i).getId());
                            int m= Integer.parseInt(lista.get(i).getId());
                            int b= Integer.parseInt(lista.get(i).getId_b());
                            int n= Integer.parseInt(lista.get(i).getId_n());
                            /*
                            int aux =0;
                                    while(aux != k && aux < k){
                                        aux++;
                                        Log.i("aux = ",String.valueOf(aux));
                                    }
                              */
                            //int fin= numbers[k];
                            //box_0[0] =String.valueOf(prueba[aux]);
                            //elementos.add(prueba[numbers[Integer.parseInt(lista.get(i).getId())]]);
                            elementos.add("Jugador " + b );
                            //elementos.add("R1 [" + numbers[aux] + ", 1]");
                            elementos.add("Jugador " + n );
                            elementos.add("Mesa " + m);
                            tabla.agregarFilaTabla(elementos);
                            elementos.clear();
                        }
                        //tabla.eliminarAllTabla();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                        for(int i = 0; i < cont; i++) {
                            int m= Integer.parseInt(lista.get(i).getId());
                            int b= Integer.parseInt(lista.get(i).getId_b());
                            int n= Integer.parseInt(lista.get(i).getId_n());

                            elementos.add("Jugador " + b );
                            elementos.add("Jugador " + n );
                            elementos.add("Mesa " + m);
                            tabla.agregarFilaTabla(elementos);
                            elementos.clear();
                        }
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                        for(int i = 0; i < cont; i++) {
                            int m= Integer.parseInt(lista.get(i).getId());
                            int b= Integer.parseInt(lista.get(i).getId_b());
                            int n= Integer.parseInt(lista.get(i).getId_n());

                            elementos.add("Jugador " + b );
                            elementos.add("Jugador " + n );
                            elementos.add("Mesa " + m);
                            tabla.agregarFilaTabla(elementos);
                            elementos.clear();
                        }
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                        for(int i = 0; i < cont; i++) {
                            int m= Integer.parseInt(lista.get(i).getId());
                            int b= Integer.parseInt(lista.get(i).getId_b());
                            int n= Integer.parseInt(lista.get(i).getId_n());

                            elementos.add("Jugador " + b );
                            elementos.add("Jugador " + n );
                            elementos.add("Mesa " + m);
                            tabla.agregarFilaTabla(elementos);
                            elementos.clear();
                        }
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
                        for(int i = 0; i < cont; i++) {
                            int m= Integer.parseInt(lista.get(i).getId());
                            int b= Integer.parseInt(lista.get(i).getId_b());
                            int n= Integer.parseInt(lista.get(i).getId_n());

                            elementos.add("Jugador " + b );
                            elementos.add("Jugador " + n );
                            elementos.add("Mesa " + m);
                            tabla.agregarFilaTabla(elementos);
                            elementos.clear();
                        }
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_SHORT).show();
                        for(int i = 0; i < cont; i++) {
                            int m= Integer.parseInt(lista.get(i).getId());
                            int b= Integer.parseInt(lista.get(i).getId_b());
                            int n= Integer.parseInt(lista.get(i).getId_n());

                            elementos.add("Jugador " + b );
                            elementos.add("Jugador " + n );
                            elementos.add("Mesa " + m);
                            tabla.agregarFilaTabla(elementos);
                            elementos.clear();
                        }
                        break;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }



        });

        //noinspection SimplifiableIfStatement
        /*
         switch (id) {
            case 0:
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                //  tabla.eliminarAllTabla();
                // tabla.agregarCabecera(R.array.cabecera_game_list);



        for(int i = 0; i < cont; i++) {
            //ArrayList<String> elementos = new ArrayList<String>();
            int leng = prueba.length;
            box_0.concat(prueba[i].toString());
            //elementos.add(box_0);
            elementos.add("R1 [" + leng + ", 0]");
            elementos.add("R1 [" + i + ", 1]");
            elementos.add("Mesa" + i);
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

           */


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
            /*case R.id.nav_config:

                intent = new Intent (GameActivityD.this, PerfilActivityD.class);
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
                Intent intent = new Intent(GameActivityD.this, LoginActivity.class);
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
