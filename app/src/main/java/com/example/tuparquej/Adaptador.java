package com.example.tuparquej;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    private Context context;
    private ArrayList<Entidad> listItems;
    private Entidad item;

    public Adaptador(Context context, ArrayList<Entidad> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        //Numero de datos a cargar (en este caso 10 mas cercanos)
        Log.d("numero", "Se ha cargado con este numero de parques: "+ listItems.size());
        return listItems.size();

    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        item=(Entidad) getItem(position);

        convertView= LayoutInflater.from(context).inflate(R.layout.item, null);

        ImageButton btnFoto= (ImageButton)convertView.findViewById(R.id.buttonParque);
        TextView nombre=convertView.findViewById(R.id.textViewNombre);
        TextView barrio=convertView.findViewById(R.id.textViewBarrio);
        ImageView estrellas=convertView.findViewById(R.id.imageViewEstrellas);
        TextView distancia=convertView.findViewById(R.id.textViewDistancia);


        if(item.getImagen()!=null)
        {
            btnFoto.setBackground(null);
            Picasso.get().load(item.getImagen()).fit().into(btnFoto);
        }


        nombre.setText(item.getNombre());
        barrio.setText(item.getBarrio());

        double e=item.getEstrellas();
        if(e==0)
        {
            estrellas.setImageResource(R.drawable.eceroestrellas);
        }
        else if(e<=1)
        {
            estrellas.setImageResource(R.drawable.eunaestrella);
        }
        else if(e<=2)
        {
            estrellas.setImageResource(R.drawable.edosestrellas);
        }
        else if(e<=3)
        {
            estrellas.setImageResource(R.drawable.etresestrellas);
        }
        else if(e<=4)
        {
            estrellas.setImageResource(R.drawable.ecuatroestrellas);
        }
        else{
            estrellas.setImageResource(R.drawable.ecincoestrellas);
        }


        distancia.setText(calcularDistancia());
        btnFoto.setTag(position);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent(context, parque.class);
                Bundle b =new Bundle();
                b.putInt("key",(Integer) v.getTag());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });



        return convertView;
    }
    private String calcularDistancia(){

        return distance(MainActivity.latitude,item.getLatitud(), MainActivity.longitude, item.getLongitud())+"";
    }
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2) {
        if(lat1==0)
        {
            lat1=4.602834;
            lon1=-74.064783;
        }
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;

    }


}
