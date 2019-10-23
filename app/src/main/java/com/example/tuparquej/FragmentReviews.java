package com.example.tuparquej;


import android.app.Fragment;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentReviews extends Fragment implements View.OnClickListener {


    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private AdaptadorReviews adaptador;
    private ListView lista;
    private ListView lvReviews;
    private int index;
    public ArrayList<Review> revs;
    private EditText editTextReview;
    private Button enviarReview;
    public FragmentReviews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        index = args.getInt("id", 0);
        lvReviews=lvReviews= getActivity().findViewById(R.id.lvReview);
        CollectionReference reviews=db.collection("Parques").document(index+"").collection("Reviews");
        Log.d("review","El index es: "+ index);
        View view=inflater.inflate(R.layout.fragment_fragment_reviews, container, false);
        lista=(ListView) view.findViewById(R.id.lvReview);
        editTextReview=(EditText) view.findViewById(R.id.editTextEscribirReview);
        enviarReview=(Button) view.findViewById(R.id.buttonEnviarReview);

        enviarReview.setOnClickListener(this);
        reviews.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e !=null){
                    return;
                }
                revs=new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots){
                    Review entid=documentSnapshots.toObject(Review.class);
                    revs.add(entid);
                }
                adaptador=new AdaptadorReviews(getActivity(), getArrayList());
                lista.setAdapter(adaptador);

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void onClick(View v) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            if(Login.user!=null)
            {

                Map<String, Object> reviewNuevo = new HashMap<>();
                reviewNuevo.put("nombre", Login.user.getDisplayName());
                reviewNuevo.put("review", editTextReview.getText().toString());
                db.collection("Parques").document(index+"").collection("Reviews").add(reviewNuevo);
                Toast.makeText(getActivity(), "Se ha agregado su review", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), "Debe iniciar sesión para hacer un review", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getActivity(), " Verifique su conexión a internet", Toast.LENGTH_LONG).show();
        }

    }

    private ArrayList<Review> getArrayList(){
        return revs;
    }


}
