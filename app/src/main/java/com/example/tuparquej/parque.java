package com.example.tuparquej;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class parque extends AppCompatActivity {

    private ImageButton reviews;
    private ImageButton ahora;
    private ImageButton details;
    private ImageButton back;
    private ImageButton goMap;
    private ImageView imagenParque;

    //Parque seleccionado
    private int pos;

    //Atributos del parque a mostrar
    private String nombre;
    private String imagen;
    private String barrio;
    private String detallesTab;
    private String reviewsTab;
    private String ahoraTab;

    //Componentes mostrados de parque

    private TextView nomb;
    private TextView barr;

    //Tabs de fragmentos
    private ImageView verdeDetails;
    private ImageView verdeReviews;
    private ImageView verdeAhora;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parque);


        //Para info del parque
        nomb=(TextView) findViewById(R.id.textView6);
        barr=(TextView) findViewById(R.id.textView8);

        reviews=(ImageButton) findViewById(R.id.imageButtonReviews);
        ahora=(ImageButton) findViewById(R.id.imageButtonAhora);
        details=(ImageButton) findViewById(R.id.imageButtonDetails);
        verdeDetails=(ImageView) findViewById(R.id.imageView8);
        verdeReviews=(ImageView) findViewById(R.id.imageView9);
        verdeAhora=(ImageView) findViewById(R.id.imageView10);
        back=(ImageButton) findViewById(R.id.imageButtonBack);
        goMap=(ImageButton) findViewById(R.id.imageButtonGo);
        imagenParque=(ImageView) findViewById(R.id.imageView6);

        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            pos=b.getInt("key");
        }
        final Entidad par=Home.listItems.get(pos);
        openDetails(par);
        nombre=par.getNombre();
        barrio=par.getBarrio();
        //edicion info
        nomb.setText(nombre);
        barr.setText(barrio);

        Picasso.get().load(par.getImagen()).fit().into(imagenParque);

        //Controla Fragmentos De Tabs
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetails2(par);
            }
        });
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReviews(par);
            }
        });
        ahora.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                openAhora(par);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                back();
            }
        });
        goMap.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                irAMapa(par);
            }
        });
    }

    public void openReviews(Entidad par){

        verdeDetails.setVisibility(View.INVISIBLE);
        verdeReviews.setVisibility(View.VISIBLE);
        verdeAhora.setVisibility(View.INVISIBLE);

        details.setImageResource(R.drawable.detailsgris);
        ahora.setImageResource(R.drawable.ahora);
        reviews.setImageResource(R.drawable.reviewsverde);
        fragment=new FragmentReviews();
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.frg,fragment);
        ft.commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void openAhora(Entidad par){
        verdeDetails.setVisibility(View.INVISIBLE);
        verdeReviews.setVisibility(View.INVISIBLE);
        verdeAhora.setVisibility(View.VISIBLE);
        details.setImageResource(R.drawable.detailsgris);
        ahora.setImageResource(R.drawable.ahoraverde);
        reviews.setImageResource(R.drawable.reviews);

        fragment=new FragmentAhora();
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.frg,fragment);
        ft.commit();
    }
    public void openDetails2(Entidad par){


        verdeDetails.setVisibility(View.VISIBLE);
        verdeReviews.setVisibility(View.INVISIBLE);
        verdeAhora.setVisibility(View.INVISIBLE);

        details.setImageResource(R.drawable.details);
        ahora.setImageResource(R.drawable.ahora);
        reviews.setImageResource(R.drawable.reviews);

        Bundle args=new Bundle();
        args.putString("key",par.getDetails());

        fragment=new FragmentDetails();
        fragment.setArguments(args);
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.frg,fragment);
        ft.commit();

    }
    public void openDetails(Entidad par){

        Bundle args=new Bundle();
        args.putString("key",par.getDetails());

        fragment=new FragmentDetails();
        fragment.setArguments(args);
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.frg,fragment);
        ft.commit();

    }
    public void back(){

       finish();
    }

    public void irAMapa(Entidad par){

        double longit=par.getLatitud();
        double latid=par.getLatitud();
        String lugar="google.streetview:cbll="+longit+", "+latid;
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("google.navigation:q=4.670199,-74.050318");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

}
