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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.viper2.appdatachess.R.string.correo;
import static com.example.viper2.appdatachess.R.string.username;

public class JuezActivityD extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    SharedPreferences prefs;//nombre de las preferencias
    SharedPreferences.Editor editor;
    String username,correo,usuario;
    EditText eRb1,eRb2,eRb3,eRn1,eRn2,eRn3;

    DatabaseReference rRef;

    Spinner sRondas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juez_d);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eRb1 = (EditText) findViewById(R.id.eRb1);
        eRb2 = (EditText) findViewById(R.id.eRb2);
        eRb3 = (EditText) findViewById(R.id.eRb3);
        eRn1 = (EditText) findViewById(R.id.eRn1);
        eRn2 = (EditText) findViewById(R.id.eRn2);
        eRn3 = (EditText) findViewById(R.id.eRn3);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);//traer informacion
        //editor = prefs.edit();//traemos el editor

        Bundle box = getIntent().getExtras();
        //username = String.valueOf(box.getString("username"));
        //correo= String.valueOf(box.getString("correo"));
        usuario = String.valueOf(box.getString("usuario"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sRondas = (Spinner) findViewById(R.id.spinner);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,R.array.opciones);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rondas, R.layout.spinner_item);
        sRondas.setAdapter(adapter);

        sRondas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                switch (i) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "Ronda 1", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Ronda 2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Ronda 3", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Ronda 4", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Ronda 5", Toast.LENGTH_SHORT).show();
                        break;
                }
                /*
                int resut1=0,resut2=0,resut3=0,var1b=0,var1n=0,var2b,var2n,var3b,var3n;

                var1b=Integer.parseInt(eRb1.getText().toString());
                var1n=Integer.parseInt(eRn1.getText().toString());
                var2b=Integer.parseInt(eRb2.getText().toString());
                var2n=Integer.parseInt(eRb2.getText().toString());
                var3b=Integer.parseInt(eRb3.getText().toString());
                var3n=Integer.parseInt(eRb3.getText().toString());
                /*
                if(eRb1.getText().toString().equals("") ||eRn1.getText().toString().equals("") ||eRb2.getText().toString().equals("") ||eRn2.getText().toString().equals("") ||eRb3.getText().toString().equals("") ||eRn3.getText().toString().equals("") )
                {Toast.makeText(getApplicationContext(), "Alguna de las casillas vacia", Toast.LENGTH_SHORT).show();}
                else {

                    if (var1b > var1n) {
                        resut1 = 1;
                    }//Gana b
                    if (var1b < var1n) {
                        resut1 = 2;
                    }//Gana n
                    if (var1b == var1n) {
                        resut1 = 3;
                    }//empate

                    if (var2b > var2n) {
                        resut2 = 1;
                    }//Gana b
                    if (var2b < var2n) {
                        resut2 = 2;
                    }//Gana n
                    if (var2b == var2n) {
                        resut2 = 3;
                    }//empate

                    if (var3b > var3n) {
                        resut3 = 1;
                    }//Gana b
                    if (var3b < var3n) {
                        resut3 = 2;
                    }//Gana n
                    if (var3b == var3n) {
                        resut3 = 3;
                    }//empate


                    Map<String, Object> upDateResult = new HashMap<>();
                    upDateResult.put("result", resut1);
                    rRef.child(String.valueOf(id + 1)).child(String.valueOf(1)).updateChildren(upDateResult);

                    upDateResult.put("result", resut2);
                    rRef.child(String.valueOf(id + 1)).child(String.valueOf(2)).updateChildren(upDateResult);

                    upDateResult.put("result", resut3);
                    rRef.child(String.valueOf(id + 1)).child(String.valueOf(3)).updateChildren(upDateResult);

                }
                */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            getMenuInflater().inflate(R.menu.juez_activity_d, menu);
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
                Intent intent = new Intent(JuezActivityD.this, MainActivityD.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_game:

                intent = new Intent (JuezActivityD.this, GameActivityD.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();

                return true;
            case R.id.nav_table:
                if (usuario=="tres"){
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }else {
                    intent = new Intent(JuezActivityD.this, TablaActivityD.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }
                return true;
            case R.id.nav_list:
                if (usuario=="uno") {
                    intent = new Intent(JuezActivityD.this, ListActivityD.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.nav_juez:
                if (usuario=="tres") {
                    intent = new Intent(JuezActivityD.this, JuezActivityD.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Campo no habilitado", Toast.LENGTH_SHORT).show();
                }
                return true;
           /* case R.id.nav_config:

                intent = new Intent (MainActivityD.this, PerfilActivityD.class);
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
                intent = new Intent(JuezActivityD.this, LoginActivity.class);
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
        return true;*/
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
