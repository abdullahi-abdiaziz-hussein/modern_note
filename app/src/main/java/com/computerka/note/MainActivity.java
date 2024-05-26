package com.computerka.note;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyAdapter myAdapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    MyDatabase myDatabase;
    SearchView searchView;
    ArrayList<NoteModel> allNotes;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        // Start loading the ad in the background.
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        setTitle("All Notes");

        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
        searchView = findViewById(R.id.searchView);

        searchView.clearFocus();


        myDatabase = new MyDatabase(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
            }
        });
/*
        ArrayList<NoteModel> notes = new ArrayList<>();
        notes.add(new NoteModel("He is good","How did you know that."));
        notes.add(new NoteModel("He is good","How did you know that."));
        notes.add(new NoteModel("He is good","How did you know that."));
        notes.add(new NoteModel("He is good","How did you know that.How did you know that.How did you know that.How did you know that.How did you know that."));
*/

        setDataOnRecyclerView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                recyclerView.setAdapter(myAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

    }

    private void setDataOnRecyclerView() {
        allNotes = myDatabase.getAllNotes();

        myAdapter = new MyAdapter(this, allNotes);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void filterList(String text) {
        List<NoteModel> filteredList = new ArrayList<>();
        for (NoteModel noteModel : allNotes) {
            if (noteModel.getTitle().toLowerCase().contains(text.toLowerCase())
                    || noteModel.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(noteModel);
            }
        }

//        Log.d("filtered",filteredList.toString());

//        Toast.makeText(this, "Notes: "+filteredList.toString(), Toast.LENGTH_LONG).show();

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Not Data Found", Toast.LENGTH_SHORT).show();
        } else {
            myAdapter.setFilteredList(filteredList);
            myAdapter = new MyAdapter(this, filteredList);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

    }

    @Override
    protected void onResume() {
        setDataOnRecyclerView();
        super.onResume();
    }
}