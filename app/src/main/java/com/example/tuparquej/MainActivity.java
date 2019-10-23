package com.example.tuparquej;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    private Button aceptarAuto;
    private Button aceptarManual;
    private Button login;
    private FusedLocationProviderClient fusedLocationClient;
    private String ciudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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

    @Override
    protected void onStart() {
        super.onStart();
        if(Login.user!=null)
        {
            login.setVisibility(View.INVISIBLE);
        }
    }

    public void openHome(){

        actualizarLocation();
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Intent intent =new Intent(this, Home.class);
            intent.putExtra("VieneDe","home");
            startActivity(intent);
        }
        else{
            if(Home.listItems==null)
            {
                Toast.makeText(this, "Actuelmente no dispone del servicio de red. Por favor Intente mas tarde", Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent =new Intent(this, Home.class);
                intent.putExtra("VieneDe","home");
                startActivity(intent);
            }
        }




    }
    public void openLogIn(){
        Toast.makeText(this, "La ciudad es: "+ciudad, Toast.LENGTH_SHORT).show();
        if(Login.user==null)
        {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                Intent intent =new Intent(this, Login.class);
                startActivity(intent);
            }
            else{
                //Snackbar snackbar = new Snackbar.make(findViewById(android.R.id.content), "Para realizar el LogIn debe tener conexion a internet. Verifique e intente mas tarde", Snackbar.LENGTH_INDEFINITE);
                //Snackbar snackbar =new Snackbar.make(this,"a",Snackbar.LENGTH_LONG);
                Toast.makeText(this, "Para realizar el LogIn debe tener conexion a internet. Verifique e intente mas tarde", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void getCurrentLocation(Location location){
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

    }
    public void actualizarLocation()
    {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            getCiudad(location);
                        }
                        else
                        {
                            Log.d("YACAMBIO", "No sirvio");
                        }
                    }
                });
    }

    public void getCiudad(Location location){
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            ciudad=addresses.get(0).getLocality();
            Toast.makeText(this, "La ciudad actual es: "+ciudad, Toast.LENGTH_SHORT).show();
            Log.d("YACAMBIO", "La ciudad es: "+ ciudad);

        }
        else {
            // do your stuff
            Log.d("YACAMBIO", "else no srivio");
        }
    }

}
