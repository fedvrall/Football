package com.example.football;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBClass extends SQLiteOpenHelper {

    private final Context context;
    private static final String DataBase_Name = "AC.db";
    private static final int DataBase_Version = 1;
    public static final String Table_Name = "Footbal";
    public static final String Table_Team = "Team";
    private static String Column_Guest = "guest";
    private static final String Column_Home = "home";
    private static final String Column_TeamName = "TeamName";
    private static final String Column_GoalGuest = "GoalG";
    private static final String Column_GoalHome = "GoalH";
    private static final String ID = "id";
    private static final String ID_Team = "teamid";
    //Spinner input_h, input_g;
    //Cursor curs;


    public DBClass(@Nullable Context context) {
        super(context, DataBase_Name, null, DataBase_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "Create Table " + Table_Name +
                        " (" + ID + " Integer PRIMARY KEY AUTOINCREMENT, " +
                        Column_Home + " Text, " +
                        Column_Guest + " Text, " +
                        Column_GoalHome + " Text, " +
                        Column_GoalGuest + " Text);";
        db.execSQL(query);
        String queryTeam = "create Table "+ Table_Team +
                "(" + ID_Team + " Integer PRIMARY KEY AUTOINCREMENT, " +
                  Column_TeamName + "Text);";
        db.execSQL(queryTeam);

        String insertTeams = "insert into " + Table_Team + " values" +
                "('Зенит')," +
                "('Спартак')," +
                "('ЦСКА')," +
                "('Крылья Советов')," +
                "('Ростов')," +
                "('Ахмат')," +
                "('Локомотив');";
        db.execSQL(insertTeams);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if Exists " + Table_Name);
        onCreate(db);
    }

    void goal(String home, String guest, String goalh,String goalg)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Home, home);
        cv.put(Column_Guest, guest);
        cv.put(Column_GoalHome, goalh);
        cv.put(Column_GoalGuest, goalg);

        long result = db.insert(Table_Name,null, cv);
        if(result == -1)
        {
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor search(String id){
        SQLiteDatabase db = this.getWritableDatabase();

       Cursor cursor = db.rawQuery("Select value from" + Table_Name +  " where " + ID + " = ? ", new String[]{id});
       return cursor;
    }

    public List<String> getAllLabels(){
        List<String> list = new ArrayList<String>();

        String selectQuery = "SELECT value FROM " + Table_Team;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // возвращаем команды
        return list;
    }

    // Я не знаю, адаптеры не работают, курсоры не работают
/*    public void fillSpinners(*//*Spinner input_g, Spinner input_h*//*)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.query(Table_Team, new String[]{"ID_Team", "Column_TeamName"}, null, null, null, null, null);
        *//*SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, android.R.layout.simple_spinner_item, curs, new String[]{"Column_TeamName"}, new int[] {android.R.id.text1});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_g.setAdapter(adapter);
        input_h.setAdapter(adapter);*//*
    }*/
}
