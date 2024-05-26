package com.computerka.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ViewNote extends AppCompatActivity {

    private TextView textViewTitle2, textViewDescription2;
    private MyDatabase myDatabase;
    private int id;
    private String title;
    private String description;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        setTitle("View Note");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        // Start loading the ad in the background.
        adView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        textViewTitle2 = findViewById(R.id.textview_title2);
        textViewDescription2 = findViewById(R.id.textview_description2);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");

        textViewTitle2.setText(title);
        textViewDescription2.setText(description);




    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete_item) {
            deleteNote();
        } else {
            updateNote();
            finish();
//            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

    private void updateNote() {
        Intent intent = new Intent(ViewNote.this, UpdateActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        startActivity(intent);
    }

    private void deleteNote() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewNote.this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDatabase = new MyDatabase(ViewNote.this);
                boolean deleteNote = myDatabase.deleteNote(id);
                Intent intent = new Intent(ViewNote.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do not add anything here
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_menu, menu);
        inflater.inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}