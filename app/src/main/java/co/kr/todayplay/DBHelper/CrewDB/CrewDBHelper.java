package co.kr.todayplay.DBHelper.CrewDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class CrewDBHelper extends SQLiteOpenHelper {
    public CrewDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE IF NOT EXISTS Crew (crew_id INTEGER PRIMARY KEY, crew_name TEXT, crew_pic TEXT, crew_position TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //insert
    public void insert_crew(int id, String name, String pic, String position){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Crew(crew_id, crew_name, crew_pic, crew_position) VALUES('" + id + "', '" + name + "', '" + pic + "', '"+ position +"');");
        Log.d("insert_crew", "insert_crew: " + id + "|" + name + "|" + pic + "|" + position);
        db.close();
    }

    //update
    public void update_crew(int id, String name, String pic, String position){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Crew SET crew_name = '" + name + "', crew_pic = '" + pic + "', crew_position = '"+ position +"' WHERE crew_id = '" + id + "';");
        Log.d("update_crew", "insert_crew: " + id + "|" + name + "|" + pic + "|" + position);
        db.close();
    }

    //delete
    public void delete_crew(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Crew WHERE crew_id ='" + id + "';");
        Log.d("delete_crew", "delete_crew: " + id);
        db.close();
    }

    //Crew Table의 모든 Tuple get
    public ArrayList<Crew> getAllCrew(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Crew", null);

        ArrayList<Crew> crewArrayList = new ArrayList<Crew>();
        while(cursor.moveToNext()){
            crewArrayList.add(new Crew(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }
        for(int i=0; i<crewArrayList.size(); i++){
            Log.d("getAllCrew", "getAllCrew: " + crewArrayList.get(i).getId() +" | "+ crewArrayList.get(i).getName() +" | "+ crewArrayList.get(i).getPic() +" | "+ crewArrayList.get(i).getPosition());
        }
        return crewArrayList;
    }

    public boolean isExistCrewID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT crew_name FROM Crew WHERE crew_id = "+ id +"", null);
        boolean check = false;
        while(cursor.moveToNext()){
            check = true;
        }
        Log.i("isExistCrew", "crew_id = " + id + " is Exist!!");
        return check;
    }

    public String getCrewName(int id){
        SQLiteDatabase db = getReadableDatabase();
        String name = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT crew_name FROM Crew WHERE crew_id = "+ id +"", null);
        while(cursor.moveToNext()){
            name = cursor.getString(0);
        }
        Log.i("getCrewName", "getCrewName: " + name);
        return name;
    }

    public String getCrewPic(int id){
        SQLiteDatabase db = getReadableDatabase();
        String pic = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT crew_pic FROM Crew WHERE crew_id = "+ id +"", null);
        while(cursor.moveToNext()){
            pic = cursor.getString(0);
        }
        Log.i("getCrewPic", "getCrewPic: " + pic);
        return pic;
    }

    public String getCrewPosition(int id){
        SQLiteDatabase db = getReadableDatabase();
        String position = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT crew_position FROM Crew WHERE crew_id = "+ id +"", null);
        while(cursor.moveToNext()){
            position = cursor.getString(0);
        }
        Log.i("getCrewPosition", "getCrewPosition: " + position);
        return position;
    }
}
