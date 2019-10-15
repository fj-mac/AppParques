package com.example.tuparquej;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        Button btnFoto= (Button)convertView.findViewById(R.id.buttonParque);
        TextView nombre=convertView.findViewById(R.id.textViewNombre);
        TextView barrio=convertView.findViewById(R.id.textViewBarrio);
        ImageView estrellas=convertView.findViewById(R.id.imageViewEstrellas);
        TextView distancia=convertView.findViewById(R.id.textViewDistancia);

        //btnFoto.setBackground(item.getImgFoto());
        nombre.setText(item.getNombre());
        barrio.setText(item.getBarrio());
<<<<<<< HEAD

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

        distancia.setText("80");
=======
        estrellas.setText(item.getEstrellas()+"");
        distancia.setText(item.getDistancia()+"");
>>>>>>> 34ac7b5b13a62b05f9e148fe9f72effcc6fdde2b
        btnFoto.setTag(position);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent(context, parque.class);
                Bundle b =new Bundle();
<<<<<<< HEAD
                b.putInt("key",(Integer) v.getTag());
=======
                b.putString("key",(Integer) v.getTag()+"");
>>>>>>> 34ac7b5b13a62b05f9e148fe9f72effcc6fdde2b
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });



        return convertView;
    }


}
