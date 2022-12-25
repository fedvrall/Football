package com.example.football;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.List;

public class Edit extends AppCompatActivity {
    EditText input_goalh,input_goalg;
    Button add_button;
    Spinner input_h, input_g;
    DBClass database = new DBClass(this);
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        input_h = findViewById(R.id.teamHome);
        input_g = findViewById(R.id.teamGuest);
        input_goalh = findViewById(R.id.etHomePoints);
        input_goalg = findViewById(R.id.etGuestPoints);
        backButton = findViewById(R.id.backbutton);
        add_button = findViewById(R.id.buttonEdit);

        loadSpinnerData();

        //SQLiteDatabase db = database.getReadableDatabase();

        //database.fillSpinners(/*input_g, input_h*/);

        // он падает на курсоре:
        //Cursor curs = db.query(database.Table_Team, new String[]{"ID_Team", "Column_TeamName"}, null, null, null, null, null);

        // Способ 2, не работает
        // Так уже не ломается, но спиннеры пустые. Нужен курсор, который достанет из БД всё
      /*  SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, null, new String[]{"Column_TeamName"}, new int[] {android.R.id.text1});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_g.setAdapter(adapter);
        input_h.setAdapter(adapter);*/

        add_button.setOnClickListener(v -> {
            DBClass mdb = new DBClass(Edit.this);
            mdb.goal(input_h.getSelectedItem().toString().trim(),
                    input_g.getSelectedItem().toString().trim(),
                    input_goalh.getText().toString().trim(),
                    input_goalg.getText().toString().trim());
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadSpinnerData() {
        DBClass db = new DBClass(Edit.this);
        List<String> labels = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        input_g.setAdapter(dataAdapter);
        input_h.setAdapter(dataAdapter);
    }
}