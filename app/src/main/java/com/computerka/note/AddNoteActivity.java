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

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private MyDatabase myDatabase;
    private String title, description;
    NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        setTitle("Add New Note");

        editTextTitle = findViewById(R.id.editTextTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        title = editTextTitle.getText().toString();
        description = editTextDescription.getText().toString();

        myDatabase = new MyDatabase(this);

        if (!(title.isEmpty() || description.isEmpty())) {

            noteModel = new NoteModel(title, description);
            long inseted = myDatabase.addNote(noteModel);

            if (inseted != -1) {
//                Toast.makeText(AddNoteActivity.this, "Inserted with id of "+inseted, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddNoteActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(AddNoteActivity.this, "Failed " + inseted, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please provide data", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

}