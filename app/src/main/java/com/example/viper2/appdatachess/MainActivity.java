package com.example.viper2.appdatachess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    SharedPreferences prefs;//nombre de las preferencias
    SharedPreferences.Editor editor;
    Button bOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bOut = (Button) findViewById(R.id.bout);


       // intent = new Intent (MainActivity.this, SplashActivity.class);
        //startActivity(intent);
        //finish();
       // editor.putInt("login",-1);//sobre escribimos con -1 (desloggeamos)
        //editor.commit();//practica 5

        bOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validan que los dos campos esten diligenciados
                //validar que esten llenas las casillas (problema entrega pasada solucionado)

                intent = new Intent (MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                editor.putInt("login",-1);//sobre escribimos con -1 (desloggeamos)
                editor.commit();//practica 5
            }

        });

    }




}
