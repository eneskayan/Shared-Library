package com.eneskayan.sharedlibrary;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Giris extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText emailText;
    EditText parolaText;

    //sadece Log-in işlemlerini içermektedir.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailText);
        parolaText = findViewById(R.id.parolaText);

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(getApplicationContext(),Feed.class);
            startActivity(intent);
        }


    }

    public void GirisYap(View view){

        mAuth.signInWithEmailAndPassword(emailText.getText().toString(),parolaText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),Feed.class);
                            startActivity(intent);
                        }

                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Giris.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void KayitOl(View view){
        mAuth.createUserWithEmailAndPassword(emailText.getText().toString(),parolaText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Giris.this, "Tebrikler, Üyelik Başarıyla Oluşturuldu!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Feed.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Giris.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}