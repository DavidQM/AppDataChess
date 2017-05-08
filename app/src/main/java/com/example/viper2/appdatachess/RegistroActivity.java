package com.example.viper2.appdatachess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    EditText eUsernamer, ePasswordr, eRepasswordr, eCorreor;
    Button bRegistrarse,  bRegcancelar;
    String username, password, repPassword, correo;
    Intent intent;

    SharedPreferences prefs;//nombre de las preferencias
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);//traer informacion
        editor = prefs.edit();//traemos el editor

        eUsernamer = (EditText) findViewById(R.id.eUsernamer);
        ePasswordr= (EditText) findViewById(R.id.ePasswordr);
        eRepasswordr = (EditText) findViewById(R.id.eRepasswordr);
        eCorreor = (EditText) findViewById(R.id.eCorreor);
        bRegistrarse = (Button) findViewById(R.id.bRegistrarse);
        bRegcancelar = (Button) findViewById(R.id.bRegcancelar);

        bRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eUsernamer.getText().toString().equals("") || ePasswordr.getText().toString().equals("")|| eRepasswordr.getText().toString().equals("")|| eCorreor.getText().toString().equals("")){
                    // validar que todos los campos esten llenos
                    Toast.makeText(getApplicationContext(),"Aun hay campos sin llenar", Toast.LENGTH_SHORT).show();
                }else {
                    if(!ePasswordr.getText().toString().equals(eRepasswordr.getText().toString())){
                        //validar que el password y repPassword sean iguales
                        Toast.makeText(getApplicationContext(),"Exite diferencia en las contraseñas", Toast.LENGTH_SHORT).show();
                        ((EditText) findViewById(R.id.ePasswordr)).setText("");
                        ((EditText) findViewById(R.id.eRepasswordr)).setText("");
                    }else{
                        if(eCorreor.getText().toString().indexOf("@")<0){
                            //validar que el email
                            Toast.makeText(getApplicationContext(),"Email Erroneo", Toast.LENGTH_SHORT).show();
                           ((EditText) findViewById(R.id.eCorreor)).setText("");
                        }else {
                            //Toast.makeText(getApplicationContext(),"prueba", Toast.LENGTH_SHORT).show();

                            username = eUsernamer.getText().toString();
                            password = ePasswordr.getText().toString();
                            repPassword = eRepasswordr.getText().toString();
                            correo = eCorreor.getText().toString();

                            intent = new Intent();
                            intent.putExtra("username", username);
                            intent.putExtra("password", password);
                            intent.putExtra("correo", correo);
                            setResult(RESULT_OK, intent);
                            finish();

                        }
                    }
                    editor.putInt("login",-1);
                    editor.commit();
                }
            }
        });

        bRegcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });

    }
}

