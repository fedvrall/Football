package com.example.football;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    ListView lv = findViewById(R.id.lv);
    ArrayAdapter<String> adapter;
    EditText search = findViewById(R.id.search);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        ImageView addItem = (ImageView)findViewById(R.id.img);
        TextView tv = (TextView)findViewById(R.id.textvieeeeew);
       // ImageButton menu = findViewById(R.id.menubutton);
        iv.setAlpha(0.3f);
        DBClass database = new DBClass(this);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase( "AC.db", MODE_PRIVATE, null);

        String result [] = {};
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.matches, result);


        //String result = "";
        Cursor query = db.rawQuery("select * from " + database.Table_Name + "; ", null);
        while(query.moveToNext()){
            String home = query.getString(1);
            String guest = query.getString(2);
            int homepoints = query.getInt(3);
            int guestpoints = query.getInt(4);
            tv.append(home + " - " + guest + "\t" + homepoints + " : " + guestpoints + "\n");
        }
        query.close();



        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Edit.class);
                startActivity(intent);
            }
        });
        tv.bringToFront();

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Edit.class);
                //intent.putExtra("tv", tv.getText().toString());
                startActivity(intent);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.this.adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuuuuu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void onAddClick(MenuItem item){

    }

    public void onDeleteClick(MenuItem item){

    }

    public void onExitClick(MenuItem item){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.menuItem1)
        {
            return true;
        }
        if(id == R.id.menuItem2)
        {
            return true;
        }
        if(id == R.id.menuItem3)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}