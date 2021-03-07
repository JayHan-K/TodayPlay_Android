package co.kr.todayplay.DBHelper.UserDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDBHelper extends SQLiteOpenHelper {

    public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS User(user_id INTEGER, scrap_journal_id INTEGER, heart_play_id INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //insert scrap_journal_id
    public void add_scrap(int user_id, int scrap_journal_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO User (user_id, scrap_journal_id) Values('" + user_id + "','" + scrap_journal_id + "');");
        Log.d("add_scrap", "user_id = " + user_id + " | journal_id = " + scrap_journal_id);

        db.close();
    }

    //insert heart_play_id
    public void add_heart(int user_id, int heart_play_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO User (user_id, heart_play_id) Values('" + user_id + "','" + heart_play_id + "');");
        Log.d("add_heart", "user_id = " + user_id + " | play_id = " + heart_play_id);

        db.close();
    }

    //delete heart_play_id
    public void delete_heart(int play_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM User WHERE heart_play_id ='" + play_id + "';");
        Log.d("delete_heart", "play_id = " + play_id);
        db.close();
    }

    //delete journal_scrap_id
    public void delete_scrap(int journal_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM User WHERE scrap_journal_id ='" + journal_id + "';");
        Log.d("delete_scrap", "journal_id = " + journal_id);
        db.close();
    }

    //IsScraped
    public boolean IsScraped(int journal_id) {
        SQLiteDatabase db = getReadableDatabase();
        int user_id = -1;
        int scrap_journal_id = -1;
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM User WHERE scrap_journal_id = " + journal_id + "", null);
        while (cursor.moveToNext()) {
            user_id = cursor.getInt(0);
            scrap_journal_id = cursor.getInt(1);
        }
        Log.i("IsScraped", "journal_id = " + journal_id );

        if(scrap_journal_id != -1)  return true;
        else    return false;
    }

    //IsHeart
    public boolean IsHeart(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        int user_id = -1;
        int heart_play_id = -1;
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM User WHERE heart_play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            user_id = cursor.getInt(0);
            heart_play_id = cursor.getInt(1);
        }
        Log.i("IsHeart", "play_id = " + play_id );

        if(heart_play_id != -1)  return true;
        else    return false;
    }
}
