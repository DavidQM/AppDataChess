package com.example.viper2.appdatachess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.facebook.FacebookSdk;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;
    private int optLog;
    private LoginButton loginbutton;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG ="Firebase";


    //EditText eUsername,ePassword;
    //Button bIniciar,bRegistro;
    String username="",correo="",usuario;
    Spinner sOpciones;
    Intent intent;//para abirir nuevas actividades


    /*SharedPreferences prefs;//nombre de las preferencias
    SharedPreferences.Editor editor;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
       // signInButton.setSize(SignInButton.SIZE_STANDARD);//estilo boton

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn();

            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).
                enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Error en login", Toast.LENGTH_SHORT).show();

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    goMainActivityD();
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        if (AccessToken.getCurrentAccessToken() != null){
            goMainActivityD();
        }

        loginbutton = (LoginButton) findViewById(R.id.login_button);
        loginbutton.setReadPermissions(Arrays.asList("email"));

        callbackManager = CallbackManager.Factory.create();

        loginbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                optLog=2;
                handleFacebookAccesToken(loginResult.getAccessToken());
                //goMainActivityD();
            }

            @Override
            public void onCancel() {

                Toast.makeText(getApplicationContext(),"Login Cancelado",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Error en Login",Toast.LENGTH_SHORT).show();

            }
        });

        /*prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);//traer informacion
        editor = prefs.edit();//traemos el editor
        //inicializamos
        username = prefs.getString("nombre","");
        password = prefs.getString("pass","");
        correo = prefs.getString("mail","");

        Log.d("user",username);
        Log.d("login",String.valueOf(prefs.getInt("login",-1)));


        if(prefs.getInt("login",-1)==1){//1 hay alguien loggeado -1 no hay nadie loggeado
            intent = new Intent (LoginActivity.this, MainActivityD.class);
            intent.putExtra("username", username);
            intent.putExtra("correo", correo);
            startActivity(intent);
            setResult(RESULT_OK, intent);
            finish();
        }*/


        /*eUsername = (EditText) findViewById(R.id.eUsername);
        ePassword = (EditText) findViewById(R.id.ePassword);
        bIniciar = (Button) findViewById(R.id.bIniciar);
        bRegistro = (Button) findViewById(R.id.bRegistro);*/

        sOpciones = (Spinner) findViewById(R.id.spinner);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,R.array.opciones);
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.opciones, R.layout.spinner_item);
        sOpciones.setAdapter(adapter);

        sOpciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                if (i==0){
                    usuario ="uno";
                    Toast.makeText(getApplicationContext(), "Eres Participante", Toast.LENGTH_SHORT).show();
                }if(i==1){
                    usuario ="dos";
                    Toast.makeText(getApplicationContext(), "Eres Espectador", Toast.LENGTH_SHORT).show();
                }if(i==2){
                    usuario ="tres";
                    Toast.makeText(getApplicationContext(), "Eres Juez", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




       /* bIniciar.setOnClickListener(new View.OnClickListener() {
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

                        intent = new Intent (LoginActivity.this, MainActivityD.class);
                        intent.putExtra("username", username);
                        intent.putExtra("correo", correo);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        setResult(RESULT_OK, intent);
                        finish();

                        String text = sOpciones.getSelectedItem().toString();
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();


                    }else{
                        Toast.makeText(getApplicationContext(), "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        });

        bRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Registro", Toast.LENGTH_SHORT).show();
                intent = new Intent (LoginActivity.this, RegistroActivity.class);
                startActivityForResult(intent,1234);
            }

        });*/
    }

    private void singIn() {
        optLog=1;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleFacebookAccesToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Error en Login",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //metodos de override Ctrl + O (no es un cero)

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (optLog==1) {  //login Google
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                } else {
                    // Google Sign In failed, update UI appropriately
                    // ...
                }
            }


        }else {            // login facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

       /* if (requestCode==1234 && resultCode == RESULT_OK){

            username = data.getExtras().getString("username");
            password = data.getExtras().getString("password");
            correo = data.getExtras().getString("correo");

            //datos a almacenar
            editor.putString("nombre",username);//practica 5
            editor.putString("pass",password);//practica 5
            editor.putString("mail",correo);//practica 5
            editor.commit();//olbligatorio para que se almacene la informacion


            //Toast.makeText(this, "Registro problema", Toast.LENGTH_SHORT).show();

        }
        if (requestCode==1234 && resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Registro cancelado", Toast.LENGTH_SHORT).show();
        }
        */



    private void goMainActivityD(){


        /*  Profile perfil= com.facebook.Profile.getCurrentProfile();
        username= perfil.getName();
        correo = perfil.getId(); */

        Intent intent = new Intent(LoginActivity.this, MainActivityD.class);
        intent.putExtra("usuario", usuario);
        //intent.putExtra("username",username);
        // intent.putExtra("correo",correo);
        startActivity(intent);
        finish();

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}