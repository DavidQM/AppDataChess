package com.example.viper2.appdatachess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    EditText eRegusername, eRegcontraseña, eRegconfcontraseña, eCorreo;
    Button bRegistrarse, bRegcancelar;
    String username, password, repPassword, correo;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        eRegusername = (EditText) findViewById(R.id.eRegusername);
        eRegusername = (EditText) findViewById(R.id.eRegusername);
        eRegcontraseña = (EditText) findViewById(R.id.eRegcontraseña);
        eCorreo = (EditText) findViewById(R.id.eCorreo);
        bRegistrarse = (Button) findViewById(R.id.bRegistrarse);
        bRegcancelar = (Button) findViewById(R.id.bRegcancelar);

        bRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eRegusername.getText().toString().equals("") || eRegcontraseña.getText().toString().equals("")|| eRegconfcontraseña.getText().toString().equals("")|| eCorreo.getText().toString().equals("")){
                    // validar que todos los campos esten llenos
                    Toast.makeText(getApplicationContext(),"Aun hay campos sin llenar", Toast.LENGTH_SHORT).show();
                }else {
                    if(!eRegcontraseña.getText().toString().equals(eRegconfcontraseña.getText().toString())){
                        //validar que el password y repPassword sean iguales
                        Toast.makeText(getApplicationContext(),"Exite diferencia en las contraseñas", Toast.LENGTH_SHORT).show();
                        ((EditText) findViewById(R.id.eRegcontraseña)).setText("");
                        ((EditText) findViewById(R.id.eRegconfcontraseña)).setText("");
                    }else{
                        if(eCorreo.getText().toString().indexOf("@")<0){
                            //validar que el email
                            Toast.makeText(getApplicationContext(),"Email Erroneo", Toast.LENGTH_SHORT).show();
                            ((EditText) findViewById(R.id.eCorreo)).setText("");
                        }else {
                            username = eRegusername.getText().toString();
                            password = eRegcontraseña.getText().toString();
                            repPassword = eRegconfcontraseña.getText().toString();
                            correo = eCorreo.getText().toString();

                            intent = new Intent();
                            intent.putExtra("username", username);
                            intent.putExtra("password", password);
                            intent.putExtra("correo", correo);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
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

