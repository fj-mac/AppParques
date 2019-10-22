package com.example.tuparquej;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

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

    @Override
    protected void onStart() {
        super.onStart();
        if(Login.user!=null)
        {
            login.setVisibility(View.INVISIBLE);
        }
    }

    public void openHome(){


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

        if(Login.user==null)
        {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                Intent intent =new Intent(this, Login.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Para realizar el LogIn debe tener conexion a internet. Verifique e intente mas tarde", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void getCurrentLocation(){


    }

    @Override
    public void onLocationChanged(Location location) {

        double longitude = location.getLongitude();
        double latitude = location.getLatitude();


        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            String ciudad=addresses.get(0).getLocality();
            Toast.makeText(this, "La ciudad actual es: "+ciudad, Toast.LENGTH_SHORT).show();
            Log.d("YA CAMBIO", "La ciudad es: "+ ciudad);

        }
        else {
            // do your stuff
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
