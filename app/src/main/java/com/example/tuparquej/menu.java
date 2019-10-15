package com.example.tuparquej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class menu extends AppCompatActivity {

    private ImageButton volver;
    private ImageButton sugerir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        volver=findViewById(R.id.imageButtonVolver);
        sugerir=findViewById(R.id.imageButtonSugerir);


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
    }
    private void volver(){
        finish();
    }
    private void sugerir(){
        Intent intent =new Intent(this, AgregarReview.class);
        startActivity(intent);
        finish();
    }

}
