package com.example.viper2.appdatachess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText eUsername,ePassword;
    Button bIniciar,bRegistrarse;
    String username="",password="",correo="";
    Intent intent;//para abirir nuevas actividades

    SharedPreferences prefs;//nombre de las preferencias
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);//traer informacion
        editor = prefs.edit();//traemos el editor
        //inicializamos
        username = prefs.getString("nombre","");
        password = prefs.getString("pass","");
        correo = prefs.getString("mail","");

        Log.d("user",username);
        Log.d("login",String.valueOf(prefs.getInt("login",-1)));


        if(prefs.getInt("login",-1)==1){//1 hay alguien loggeado -1 no hay nadie loggeado
            intent = new Intent (LoginActivity.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("correo", correo);
            startActivity(intent);
            setResult(RESULT_OK, intent);
            finish();
        }

        eUsername = (EditText) findViewById(R.id.eUsername);
        ePassword = (EditText) findViewById(R.id.ePassword);
        bIniciar = (Button) findViewById(R.id.bIniciar);
        bRegistrarse = (Button) findViewById(R.id.bRegistrarse);

        bIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validan que los dos campos esten diligenciados
                //validar que esten llenas las casillas (problema entrega pasada solucionado)
                if(eUsername.getText().toString().equals("") || ePassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Error \n Alguno o ambos espacios estan vacios", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Validar los datos digitados con los del registro
                    if (eUsername.getText().toString().equals(username) && ePassword.getText().toString().equals(password)){
                        Toast.makeText(getApplicationContext(), "Nombre de usuario o contraseña correctos", Toast.LENGTH_SHORT).show();

                        editor.putInt("login",1);//sobre escribimos con 1 (alguien ya esta loggeado)//practica 5
                        editor.commit();//practica 5

                        intent = new Intent (LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("correo", correo);
                        startActivity(intent);
                        setResult(RESULT_OK, intent);
                        finish();


                    }else{
                        Toast.makeText(getApplicationContext(), "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        });

        bRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Registro", Toast.LENGTH_SHORT).show();
                intent = new Intent (LoginActivity.this, RegistroActivity.class);
                startActivityForResult(intent,1234);
            }

        });
    }

    //metodos de override Ctrl + O (no es un cero)

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1234 && resultCode == RESULT_OK){
            username = data.getExtras().getString("username");
            password = data.getExtras().getString("password");
            correo = data.getExtras().getString("correo");

            //datos a almacenar
            editor.putString("nombre",username);//practica 5
            editor.putString("pass",password);//practica 5
            editor.putString("mail",correo);//practica 5
            editor.commit();//olbligatorio para que se almacene la informacion


        }
        if (requestCode==1234 && resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Registro cancelado", Toast.LENGTH_SHORT).show();
        }


    }
}
