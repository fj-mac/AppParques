package com.example.tuparquej;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorReviews extends BaseAdapter {
    private Context context;
    private ArrayList<Review> lista;
    private Review item;

    public AdaptadorReviews(Context context, ArrayList<Review> listItems) {
        this.context = context;
        this.lista = listItems;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        item=(Review) getItem(position);
        convertView= LayoutInflater.from(context).inflate(R.layout.review, null);
        TextView nombre=convertView.findViewById(R.id.textViewNombreP);
        TextView review=convertView.findViewById(R.id.textViewReview);

        nombre.setText(item.getNombre());
        review.setText(item.getReview());
        Log.d("reviews", "Llego al adaptador sin problemas");
        return convertView;
    }
}
