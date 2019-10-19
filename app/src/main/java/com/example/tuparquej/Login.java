package com.example.tuparquej;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton login;
    private Button login2;
    private Button signIn;
    private FirebaseAuth mAuth;
    private EditText correo;
    private EditText clave;
    private EditText clave2;
    public static ArrayList<Integer> listaFavoritos;
    public static FirebaseUser user=null;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.googleSignIn);
        login2=findViewById(R.id.button3);
        signIn=findViewById(R.id.buttonSignUp);
        correo=findViewById(R.id.editText3);
        clave=findViewById(R.id.editText2);
        clave2=findViewById(R.id.editText4);



        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                googleLogin();
            }
        });
        login2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                normalLogin();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clave2.setVisibility(View.VISIBLE);
                normalSignUp();
            }
        });



        mAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
    }
    private void googleLogin(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void normalSignUp(){
        String email=correo.getText().toString();
        String password=clave.getText().toString();
        while (verificar()==false)
        {

        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                             user = mAuth.getCurrentUser();
                            //updateUI(user);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "No sirvio el login, intente con google o mas tarde", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }
    private void normalLogin() {
        String email=correo.getText().toString();
        String password=clave.getText().toString();
        while (verificarSignUp()==false)
        {

        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            user = mAuth.getCurrentUser();
                            new Thread(new Runnable() {
                                public void run() {
                                    verificarFavoritos();
                                }
                            }).start();
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Ha ingresado un usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            user = mAuth.getCurrentUser();

                            new Thread(new Runnable() {
                                public void run() {
                                    verificarFavoritos();
                                }
                            }).start();
                            Toast.makeText(Login.this, "Se ha iniciado con el correo: "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Falla el login", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean verificar(){
        return true;
    }
    private boolean verificarSignUp(){
        return true;
    }
    public void verificarFavoritos(){
        final DocumentReference favoritos=db.collection("Usuarios").document(user.getUid());
        favoritos.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot ds, @Nullable FirebaseFirestoreException e) {
                if(e !=null){
                    return;
                }
                Map<String, Object> map = ds.getData();
                listaFavoritos=new ArrayList<>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                        int parque=Integer.parseInt(entry.getValue().toString());
                       listaFavoritos.add(parque);
                        Log.d("Guardando","Lo que esta guardando es: "+parque);

                }
            }
        });
    }
}
