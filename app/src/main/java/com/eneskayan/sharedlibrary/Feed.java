package com.eneskayan.sharedlibrary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Feed extends AppCompatActivity {

//    ListView listView;
    PostClass adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    private ArrayList<String> mBaslik = new ArrayList<>();
    private ArrayList<String> mFotoUrl = new ArrayList<>();



    //kitap ekleme menüsü
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_post,menu);

        return super.onCreateOptionsMenu(menu);
    }
    //kitap ekleme menüsünden intentle diğer UploadBook sayfasına gitme
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.add_post){
            Intent intent = new Intent(getApplicationContext(), UploadBook.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //floating action button settings
        final FloatingActionButton floatingActionButton = findViewById(R.id.fab_1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==floatingActionButton){
                    Intent intent = new Intent(getApplicationContext(), UploadBook.class);
                    startActivity(intent);
                }
                else{

                }
            }
        });

//        listView = findViewById(R.id.listView);

     //   mBaslik = new ArrayList<String>();
    //    mFotoUrl = new ArrayList<String>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

//        adapter = new PostClass(userbaslikFromFB,userimageFromFB,useremailFromFB,this);
//        listView.setAdapter(adapter);
        getDataFromDatabase();
    }

// başlığı ve image url'ini database'e gönderme
    public void getDataFromDatabase(){
        final DatabaseReference newReferance = firebaseDatabase.getReference("Posts");
        newReferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

// RecyclerView in çağrıldması
/*
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();

                    if(hashMap.get("baslik").equals("Roman")){
                        initRecyclerView();
                        mBaslik.add(hashMap.get("baslik"));
                        mFotoUrl.add(hashMap.get("downloadurl"));
                        //adapter.notifyDataSetChanged();
                    }
                    else if(hashMap.get("baslik").equals("Hikaye")){
                        initRecyclerView2();
                        mBaslik.add(hashMap.get("baslik"));
                        mFotoUrl.add(hashMap.get("downloadurl"));
                        //adapter.notifyDataSetChanged();
                    }
                        */
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();

                            mBaslik.add(hashMap.get("baslik"));
                            mFotoUrl.add(hashMap.get("downloadurl"));

                            if("Roman".equals(hashMap.get("baslik"))){
                                initRecyclerView();
                            }
                            else if("Hikaye".equals(hashMap.get("baslik"))){
                                initRecyclerView2();

                            }

                        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Feed.this, "İptal edildi.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void initRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        PostClass adapter = new PostClass(this, mBaslik,mFotoUrl);
        recyclerView.setAdapter(adapter);
    }

    private  void initRecyclerView2(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(layoutManager);
        PostClass adapter2 = new PostClass(this, mBaslik,mFotoUrl);
        recyclerView2.setAdapter(adapter2);

    }


}