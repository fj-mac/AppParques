package com.example.tuparquej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class menu extends AppCompatActivity {

    private ImageButton volver;
    private ImageButton sugerir;
    private ImageButton favoritos;
    private ImageButton encontrarParques;

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference parques=db.collection("Parques");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        volver=findViewById(R.id.imageButtonVolver);
        sugerir=findViewById(R.id.imageButtonSugerir);
        favoritos=findViewById(R.id.imageButton4);
        encontrarParques=findViewById(R.id.imageView11);


        volver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                volver();
            }
        });
        sugerir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sugerir();
            }
        });
        favoritos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                verFavoritos();
            }
        });
        encontrarParques.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                verParques();
            }
        });
    }
    private void volver(){
        finish();
    }
    private void sugerir(){
        Intent intent =new Intent(this, AgregarReview.class);
        startActivity(intent);
        finish();

    }
    private void verFavoritos(){
        //Cargar solo Favoritos
        parques.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e !=null){
                    Toast.makeText(menu.this, "Error entrando a base de datos", Toast.LENGTH_SHORT).show();
                    return;
                }
                Home.listItems=new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots){
                    Entidad entid=documentSnapshots.toObject(Entidad.class);
                    if(Login.listaFavoritos.contains(entid.getId()))
                    {
                        Home.listItems.add(entid);
                    }
                }
                //ver favoritos
                Intent intent =new Intent(menu.this, Home.class);
                intent.putExtra("VieneDe","favoritos");
                startActivity(intent);
                finish();
            }
        });


    }
    private void verParques(){
        Intent intent =new Intent(menu.this, Home.class);
        intent.putExtra("VieneDe","home");
        startActivity(intent);
        finish();
    }

}
