package com.eneskayan.sharedlibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UploadBook extends AppCompatActivity {

    //EditText kitapbaslikId;
    ImageView kitapgörseliId;
    EditText kitapTuru;
    FirebaseDatabase firebaseDatabase;
    static DatabaseReference myRef;
    private FirebaseAuth mAut;
    private StorageReference mStorageRef;
    Uri selectedImage;
    Spinner spinner1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_book);
        kitapgörseliId = findViewById(R.id.kitapgörseliId);
        kitapTuru = findViewById(R.id.bookType);

        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAut = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();





//        spinner1 = findViewById(R.id.spinner1);

        //Spinner settings :)
/*
        String[] kitap_türleri = new String[]{
                "Kitap Türünü Seçiniz...",
                "Roman",
                "Hikaye",
                "Şiir",
                "Dergi",
                "Deneme",
                "Eğitim",
                "Tarih",
                "Anı-Hatıra",
                "Öykü",
                "Çizgi Roman",
                "Diğer"
        };

        final List<String> kitap_türleriList = new ArrayList<>(Arrays.asList(kitap_türleri));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,kitap_türleriList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Spinnerın ilk satırı inaktif,
                    // Sadece yorum satırı olarak kalıcak
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView_spinner = (TextView) view;
                if (position == 0) {
                    // İlk satırı gri göstermek için
                    textView_spinner.setTextColor(Color.GRAY);
                } else {
                    textView_spinner.setTextColor(Color.BLACK);
                }
                return view;

            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(spinnerArrayAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), selectedItemText +"Seçildi.", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/


    }


    public void Paylas(View view){

        UUID uuid = UUID.randomUUID();
        final String imageName = "resim/"+uuid+".jpg";



        StorageReference storageReference = mStorageRef.child(imageName);
        storageReference.putFile(selectedImage).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //database e kaydedilecekler
                // download url
                StorageReference newReferance = FirebaseStorage.getInstance().getReference(imageName);
                newReferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadURL = uri.toString();
                        FirebaseUser user = mAut.getCurrentUser();
                        // username, başlık
                        String userEmail = user.getEmail();
                        //String baslik = kitapbaslikId.getText().toString();
                        String baslik1 = kitapTuru.getText().toString();
                        UUID uuid1 =UUID.randomUUID();
                        String uuidString = uuid1.toString();
                        HashMap<String, Object> postData = new HashMap<>();
                        postData.put("useremail",userEmail);
                        postData.put("baslik",baslik1);
                        postData.put("downloadurl",downloadURL);
                        //postData.put("date", FieldValue.serverTimestamp());

                        myRef.child("Posts").child(uuidString).child("useremail").setValue(userEmail);
                        myRef.child("Posts").child(uuidString).child("baslik").setValue(baslik1);
                        myRef.child("Posts").child(uuidString).child("downloadurl").setValue(downloadURL);


                        Toast.makeText(UploadBook.this, "Gönderi Paylaşıldı.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),Feed.class);
                        startActivity(intent);

                    }
                });

            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadBook.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
/*
    public static String getBookName(){
        boolean a = myRef.child("Posts").child().child().getDatabas;

        String baslik2 = kitapTuru.getText().toString();
        return baslik2;
    }
*/
    public void GörselEkle(View view){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else{
            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==2 && resultCode == RESULT_OK && data !=null){
            selectedImage =data.getData();
            try {
                Bitmap bitmap =MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                kitapgörseliId.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
