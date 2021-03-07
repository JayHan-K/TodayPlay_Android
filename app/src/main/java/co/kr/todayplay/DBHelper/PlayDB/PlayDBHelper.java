package co.kr.todayplay.DBHelper.PlayDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.kr.todayplay.object.CategoryRe;
import co.kr.todayplay.object.dbsearch1;

public class PlayDBHelper extends SQLiteOpenHelper {
    public PlayDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Play(play_id INTEGER PRIMARY KEY, play_title TEXT,play_category TEXT,play_main_poster TEXT, play_main_journal_file TEXT,play_journal_thumbnail1_img TEXT,play_journal_thumbnail2_img TEXT,play_keywords TEXT, play_youtube_links TEXT,play_crew TEXT, num_of_heart INTEGER, relative_journal TEXT, play_history TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //insert
    public void insert_Play(int play_id, String play_title, String play_category, String play_main_poster, String play_main_journal_file, String play_journal_thumbnail1_img, String play_journal_thumbnail2_img,String play_keywords, String play_youtube_links, String play_crew, int num_of_heart, String relative_journal, String history ) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Play (play_id, play_title, play_category, play_main_poster, play_main_journal_file, play_journal_thumbnail1_img, play_journal_thumbnail2_img,play_keywords, play_youtube_links, play_crew, num_of_heart, relative_journal, play_history) Values('" + play_id + "','" + play_title + "','" + play_category + "','" + play_main_poster + "','" + play_main_journal_file + "','" + play_journal_thumbnail1_img + "','" + play_journal_thumbnail2_img + "','" + play_keywords + "','" + play_youtube_links + "','"+play_crew+"','"+num_of_heart+"','" + relative_journal + "', '" + history + "');");
        Log.d("insert_play", "insert_play: " + play_id + "|" + play_title + "|" + play_category + "|" + play_main_poster + "|" + play_main_journal_file + "|" + play_journal_thumbnail1_img + "|" + play_journal_thumbnail2_img + "|" + play_keywords + "|" + play_youtube_links + "|" + play_crew + "|" + num_of_heart + "|" + relative_journal + "|" + history);

        db.close();
    }

    //update
    public void update_Play(int play_id, String play_title, String play_category, String play_main_poster, String play_main_journal_file, String play_journal_thumbnail1_img, String play_journal_thumbnail2_img, String play_keywords, String play_youtube_links, String play_crew, int num_of_heart, String relative_journal, String history) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Play SET play_title='" + play_title + "', play_category= '" + play_category + "', play_main_poster= '" + play_main_poster +"', play_main_journal_file= '" + play_main_journal_file + "',play_journal_thumbnail1_img= '" + play_journal_thumbnail1_img + "',play_journal_thumbnail2_img= '" + play_journal_thumbnail2_img + "', play_keywords= '" + play_keywords + "',play_youtube_links ='" + play_youtube_links + "', play_crew = '" + play_crew + "', num_of_heart = '" + num_of_heart + "', relative_journal='" + relative_journal + "', play_history = '"+ history +"' WHERE play_id='" + play_id + "';");
        Log.d("update_play", "update_play: " + play_id + "|" + play_title + "|" + play_category + "|" + play_main_poster + "|" + play_main_journal_file + "|" + play_journal_thumbnail1_img + "|" + play_journal_thumbnail2_img + "|" + play_keywords + "|" + play_youtube_links + "|" + play_crew + "|" + num_of_heart + "|" + relative_journal + "|" + history);
        db.close();
    }

    //delete
    public void delete_Play(int play_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Play WHERE play_id ='" + play_id + "';");
        Log.d("delete_play", "delete_play: " + play_id);
        db.close();
    }

    public boolean isExistPlayID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT play_title FROM Play WHERE play_id = "+ id +"", null);
        boolean check = false;
        while(cursor.moveToNext()){
            check = true;
        }
        if (check) Log.d("isExistPlayID: ", "play_id = " + id + " is already exists!!");
        return check;
    }

    public String getPlayTitle(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String play_title = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_title FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            play_title = cursor.getString(0);
        }
        Log.i("getPlayTitle", "play_title"+play_title );
        return play_title;
    }
    public ArrayList<CategoryRe> getkeywordplay_id(String keyword){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CategoryRe> keywords = new ArrayList<>() ;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM Play WHERE play_keywords LIKE ? ");
        if(keyword.contains("/")){
            String sp[] = keyword.split("/");
            keyword=sp[0];
        }
        Log.d("keywordin","keyword="+keyword);
        String[] params = {"%"+keyword+"%"};
        Cursor cursor;
//        cursor = db.rawQuery("SELECT keyword FROM play WHERE keyword LIKE '%keyowrds +  = "+ keyword.toString()+"",null);
        cursor = db.rawQuery(sb.toString(),params);
        while (cursor.moveToNext()){
            CategoryRe categoryRe = new CategoryRe(cursor.getInt(0),cursor.getString(2),cursor.getString(3),cursor.getInt(10));
            keywords.add(categoryRe);
        }
        Log.i("category_keywords",keywords.get(0).getCategory()+" ");
        cursor.close();
        return keywords;
    }


    public String getPlayCategory(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String play_category = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_category FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            play_category = cursor.getString(0);
        }
        Log.i("getPlayCategory", play_category.length() + " ");
        return play_category;
    }

    public String getPlayKeywords(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String keywords = "";
        Cursor cursor;
        int i = 0;
        cursor = db.rawQuery("SELECT play_keywords FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            keywords = cursor.getString(0);
            i++;
        }
        Log.i("getPlayKeywords", keywords + " ");
        return keywords;
    }


    public String getPlayMain_Journal(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String main_journal = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_main_journal_file FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            main_journal = cursor.getString(0);
        }
        Log.i("getPlayMain_Journal", main_journal + " ");
        return main_journal;
    }

    public String getPlayThumbnail1(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String thumbnail1 = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_journal_thumbnail1_img FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            thumbnail1 = cursor.getString(0);
        }
        Log.i("getPlayThumbnail1", thumbnail1 + " ");
        return thumbnail1;
    }

    public String getPlayhumbnail2(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String thumbnail2 = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_journal_thumbnail2_img FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            thumbnail2 = cursor.getString(0);
        }
        Log.i("getPlayhumbnail2", thumbnail2 + " ");
        return thumbnail2;
    }

    public String getPlayRelative_Journal(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String relative_journal = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT relative_journal FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            relative_journal = cursor.getString(0);
        }
        Log.i("getPlayRelative_Journal", relative_journal + " ");
        return relative_journal;
    }

    public String getPlayVideos(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String videos = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_youtube_links FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            videos = cursor.getString(0);
        }
        Log.i("getPlayVideos", videos + " ");
        return videos;
    }

    public String getPlayPoster(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String poster = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_main_poster FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            poster = cursor.getString(0);
        }
        Log.i("getPlayPoster", poster + " ");
        return poster;
    }

    public String getPlayPlay_Crew(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String play_crew = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_crew FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            play_crew = cursor.getString(0);
        }
        Log.i("getPlayplay_crew", play_crew + " ");
        return play_crew;
    }

    public int getPlayNum_of_heart(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        int num_of_heart = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT num_of_heart FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            num_of_heart = cursor.getInt(0);
        }
        Log.i("getPlayNum_of_heart", num_of_heart + " ");
        return num_of_heart;
    }

    public String getPlayHistory(int play_id) {
        SQLiteDatabase db = getReadableDatabase();
        String play_history = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT play_history FROM Play WHERE play_id = " + play_id + "", null);
        while (cursor.moveToNext()) {
            play_history = cursor.getString(0);
        }
        Log.i("getPlayHistory", play_history + " ");
        return play_history;
    }

    public ArrayList<Play> getAllPlay() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Play", null);

        ArrayList<Play> journalArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            journalArrayList.add(new Play(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),cursor.getInt(10),cursor.getString(11)));

        }
        return journalArrayList;
    }
    public ArrayList<dbsearch1>getmidsearch(String title){
        SQLiteDatabase db = getReadableDatabase();
        String[] params = {"%"+title+"%"};
        Cursor cursor = db.rawQuery("SELECT * FROM Play WHERE play_title LIKE ?" ,params);
        ArrayList<dbsearch1> dbsearch1s = new ArrayList<>();
        while(cursor.moveToNext()){
            dbsearch1s.add(new dbsearch1(cursor.getInt(0),cursor.getString(3),cursor.getString(7)));
        }
        return dbsearch1s;
    }

    public List<String> getAlltitle(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT play_title FROM Play",null);
        List<String> titleList = new ArrayList<>();
        while(cursor.moveToNext()){
            titleList.add(cursor.getString(0));
        }
//        titleList.add("연극");
//        titleList.add("뮤지컬");
//        titleList.add("창극");
//        titleList.add("어린이");
//        titleList.add("로맨스");
//        titleList.add("스릴러");
//        titleList.add("공포");
//        titleList.add("감동");
//        titleList.add("드라마");
//        titleList.add("역사");
//        titleList.add("고전극");
//        titleList.add("신나는");
//        titleList.add("역동적인");
//        titleList.add("대극장");
//        titleList.add("중극장");
//        titleList.add("소극장");
//        titleList.add("눈물 콧물 쏙 빼는");
//        titleList.add("마음이 따뜻해지는");
//        titleList.add("웃음 폭탄");
//        titleList.add("긴장감이 있는");
//        titleList.add("여운이 남는");
        return titleList;
    }





}
