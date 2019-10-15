package com.example.tuparquej;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AgregarReview extends AppCompatActivity {

    private static final String TAG = "AgregarReview";
    private static final String KEY_NOMBRE="nombre";
    private static final String KEY_REVIEW="review";

    private EditText editTextNombre;
    private EditText editTextReview;

    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_review);

        editTextNombre=findViewById(R.id.editText_nombre);
        editTextReview=findViewById(R.id.editText_Review);
    }

    public void saveReview(View v){
        String nombre=editTextNombre.getText().toString();
        String review=editTextReview.getText().toString();

        Map <String, Object> note=new HashMap<>();
        note.put(KEY_NOMBRE,nombre);
        note.put (KEY_REVIEW, review);

        db.collection("Reviews").document("My first Review").set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AgregarReview.this, "Note saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AgregarReview.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}
