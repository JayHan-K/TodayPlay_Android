package co.kr.todayplay.PlayDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class PerformDBHelper extends SQLiteOpenHelper {
    public PerformDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Perform(play_id INTEGER PRIMARY KEY, play_title TEXT,play_category TEXT, play_keywords TEXT, play_main_journal_file TEXT,play_journal_thumbnail1_img TEXT,play_journal_thumbnail2_img TEXT,relative_journal TEXT, play_youtube_links TEXT, play_main_poster TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //insert
    public void insert_Perform(int play_id, String play_title, String play_category, String play_keywords, int play_main_journal_file, String play_journal_thumbnail1_img, String play_journal_thumbnail2_img, String relative_journal, String play_youtube_links, String play_main_poster) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Perform (play_id, play_title, play_category, play_keywords, play_main_journal_file, play_journal_thumbnail1_img, play_journal_thumbnail2_img,relative_journal, play_youtube_links,play_main_poster) Values('" + play_id + "','" + play_title + "','" + play_category + "','" + play_keywords + "','" + play_main_journal_file + "','" + play_journal_thumbnail1_img + "','" + play_journal_thumbnail2_img + "','" + relative_journal + "','" + play_youtube_links + "','" + play_main_poster + "');");
        Log.d("insert_play", "insert_play: " + play_id + "|" + play_title + "|" + play_category + "|" + play_keywords + "|" + play_main_journal_file + "|" + play_journal_thumbnail1_img + "|" + play_journal_thumbnail2_img + "|" + relative_journal + "|" + play_youtube_links + "|" + play_main_poster + "|" );

        db.close();
    }

    //update
    public void update_Perform(int play_id, String play_title, String play_category, String play_keywords, int play_main_journal_file, String play_journal_thumbnail1_img, String play_journal_thumbnail2_img, String relative_journal, String play_youtube_links, String play_main_poster) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Perform SET play_id='" + play_id + "',play_title='" + play_title + "',play_category= '" + play_category + "',play_keywords= '" + play_keywords + "',play_main_journal_file= '" + play_main_journal_file + "',play_journal_thumbnail1_img= '" + play_journal_thumbnail1_img + "',play_journal_thumbnail2_img= '" + play_journal_thumbnail2_img + "',relative_journal='" + relative_journal + "',play_youtube_links='" + play_youtube_links + "',play_main_poster= '" + play_main_poster +"';");
        Log.d("update_play", "update_play: " + play_id + "|" + play_title + "|" + play_category + "|" + play_keywords + "|" + play_main_journal_file + "|" + play_journal_thumbnail1_img + "|" + play_journal_thumbnail2_img + "|" + relative_journal + "|" + play_youtube_links + "|" + play_main_poster + "|" );

        db.close();
    }

    //delete
    public void delete_Perform(int play_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Perform WHERE play_id ='" + play_id + "';");
        Log.d("delete_play", "delete_play: " + play_id);
        db.close();
    }

    public String getPerformTitle(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String play_title = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_title FROM Perform WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            play_title = cursor.getString(0);
        }
        Log.i("getPerformTitle", "play_title"+play_title );
        return play_title;
    }

    public String getPerformCategory(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String play_category = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_category FROM Perform WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            play_category = cursor.getString(0);
        }
        Log.i("getPerformCategory", play_category + " ");
        return play_category;
    }

    public String getPerformKeywords(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String keywords = "";
        Cursor cursor;
        int i = 0;
        cursor = db.rawQuery("SELECT play_keywords FROM Perform WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            keywords = cursor.getString(3);
            i++;
        }
        Log.i("getPerformKeywords", keywords + " ");
        return keywords;
    }

    public int getPerformMain_Journal(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        int main_journal = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_main_journal_file FROM Perform WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            main_journal = cursor.getInt(4);
        }
        Log.i("getPerformMain_Journal", main_journal + " ");
        return main_journal;
    }

    public String getPerformThumbnail1(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String thumbnail1 = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_journal_thumbnail1_img FROM Perform WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            thumbnail1 = cursor.getString(5);
        }
        Log.i("getPerformThumbnail1", thumbnail1 + " ");
        return thumbnail1;
    }

    public String getPerformThumbnail2(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String thumbnail2 = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_journal_thumbnail2_img FROM Perform WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            thumbnail2 = cursor.getString(6);
        }
        Log.i("getPerformThumbnail2", thumbnail2 + " ");
        return thumbnail2;
    }

    public String getPerformRelative_Journal(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String relative_journal = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT relative_journal FROM Perform WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            relative_journal = cursor.getString(7);
        }
        Log.i("getPerformRelative", relative_journal + " ");
        return relative_journal;
    }

    public String getPerformVideos(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String videos = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_youtube_links FROM Perform WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            videos = cursor.getString(8);
        }
        Log.i("getPerformVideos", videos + " ");
        return videos;
    }

    public String getPerformPoster(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String poster = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_main_poster FROM Perform WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            poster = cursor.getString(9);
        }
        Log.i("getPerformPoster", poster + " ");
        return poster;
    }

    public ArrayList<Perform> getAllPlay() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Perform", null);

        ArrayList<Perform> journalArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            journalArrayList.add(new Perform(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9)));

        }
        return journalArrayList;
    }
}
