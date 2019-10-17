package com.example.tuparquej;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private ListView lvItems;
    private Adaptador adaptador;
    private ImageButton menu;
    public static ArrayList<Entidad> listItems;

    private static final String TAG = "Home";
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference parques=db.collection("Parques");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        menu=findViewById(R.id.imageButtonIrAMenu);
        lvItems=findViewById(R.id.lvItems);


        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                irAMenu();
            }
        });
        //mes
    }

    @Override
    protected void onStart() {
        super.onStart();

        parques.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e !=null){
                    Toast.makeText(Home.this, "Error entrando a base de datos", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return;
                }
                listItems=new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots){
                    Entidad entid=documentSnapshots.toObject(Entidad.class);
                    listItems.add(entid);
                    Toast.makeText(Home.this, "Se ha agregado", Toast.LENGTH_SHORT).show();
                }
                adaptador=new Adaptador(Home.this,getArrayList() );
                lvItems.setAdapter(adaptador);
            }
        });

    }



    //Metodo de carga de parques

    private ArrayList<Entidad> getArrayList(){

        //Reemplazar esto por lectura desde firebase
        ArrayList rev=new ArrayList();
        rev.add("Muy chevere");
        rev.add("Bonito");
        rev.add("Amplio");
        //De prueba
        //listItems.add( new Entidad(String imagen, String nombre, String barrio, String details, int estrellas, double latitud, double longitud, ArrayList<String> reviews));
        //listItems.add( new Entidad("","Parque del Japon", "Cabrera", "Parque muy bonito recien remodelado bla bla bla", 5, 4.670100, -74.050342));
        //listItems.add( new Entidad("","a", "Cabrera", "Parque muy bonito recien remodelado bla bla bla", 4, 4.670100, -74.050342));
        //listItems.add( new Entidad("","b", "Cabrera", "Parque muy bonito recien remodelado bla bla bla", 3, 4.670100, -74.050342));
        //listItems.add( new Entidad("","c", "Cabrera", "Parque muy bonito recien remodelado bla bla bla", 2, 4.670100, -74.050342,  rev));
        //listItems.add( new Entidad("","d", "Cabrera", "Parque muy bonito recien remodelado bla bla bla", 1, 4.670100, -74.050342,  rev));
        //listItems.add( new Entidad("","e", "Cabrera", "Parque muy bonito recien remodelado bla bla bla", 0, 4.670100, -74.050342,  rev));
        return listItems;
    }
    private ArrayList<Entidad> getEntidades(){
        return listItems;
    }
    private void irAMenu(){
        Intent intent =new Intent(this, menu.class);
        startActivity(intent);
    }
    public void cargarParques(){

        parques.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots){
                            Entidad entid=documentSnapshots.toObject(Entidad.class);
                            listItems.add(entid);
                            Toast.makeText(Home.this, "Se ha agregado", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Home.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

    }

}
