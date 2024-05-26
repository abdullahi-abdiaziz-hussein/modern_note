package com.computerka.note;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText editTextTitle2, editTextDescription2;
    MyAdapter myAdapter;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        setTitle("Update Note");

        editTextTitle2 = findViewById(R.id.editTextTextTitle2);
        editTextDescription2 = findViewById(R.id.editTextDescription2);

        Intent intent = getIntent();

       id = intent.getIntExtra("id",0);
       String title = intent.getStringExtra("title");
       String description = intent.getStringExtra("description");

       editTextDescription2.setText(description);
       editTextTitle2.setText(title);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        MyDatabase myDatabase = new MyDatabase(this);

        int updated = myDatabase.updateNote(editTextTitle2.getText().toString(), editTextDescription2.getText().toString(), id);

        if (updated > 0){
            startActivity(new Intent(UpdateActivity.this,MainActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}