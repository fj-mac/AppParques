package com.example.tuparquej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button aceptarAuto;
    private Button aceptarManual;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aceptarAuto=(Button) findViewById(R.id.button);
        aceptarManual=(Button) findViewById(R.id.button2);
        login=(Button) findViewById(R.id.buttonLogIn);

        aceptarAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        aceptarManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogIn();

            }
        });

    }

    public void openHome(){
        Intent intent =new Intent(this, Home.class);
        startActivity(intent);
    }
    public void openLogIn(){
        Intent intent =new Intent(this, Login.class);
        startActivity(intent);
    }
}
