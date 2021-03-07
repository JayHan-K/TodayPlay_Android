package co.kr.todayplay.DBHelper.JournalDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

import co.kr.todayplay.object.Recommend;

public class JournalDBHelper extends SQLiteOpenHelper {
    public JournalDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE IF NOT EXISTS Journal (journal_id INTEGER PRIMARY KEY, journal_title TEXT, journal_subtitle TEXT, journal_editor TEXT, journal_category TEXT, journal_num_of_scrap INTEGER, journal_comments TEXT, journal_num_of_view TEXT, journal_relation_play TEXT, journal_relation_journal TEXT, journal_keyword TEXT, journal_banner_img TEXT, journal_thumbnail1_img TEXT, journal_thumbnail2_img TEXT, journal_file TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //insert
    public void insert_journal(int id, String title, String subtitle, String editor, String category, int num_of_scrap, String comments, int num_of_view, String relation_play, String relation_journal, String keyword, String banner_img, String thumbnail1_img, String thumbnail2_img, String file){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Journal(journal_id, journal_title, journal_subtitle, journal_editor, journal_category, journal_num_of_scrap, journal_comments, journal_num_of_view, journal_relation_play, journal_relation_journal, journal_keyword, journal_banner_img, journal_thumbnail1_img, journal_thumbnail2_img, journal_file) VALUES('" + id + "', '" + title + "', '" + subtitle + "', '"+ editor +"', '" + category + "', '" + num_of_scrap + "', '" + comments + "', '"+ num_of_view +"', '"+ relation_play +"', '"+ relation_journal +"', '"+ keyword +"', '"+ banner_img +"', '"+ thumbnail1_img +"', '"+ thumbnail2_img +"', '"+ file +"');");
        Log.d("insert_journal", "insert_Journal: " + id + "|" + title + "|" + subtitle + "|" + editor + "|" + category + "|" + num_of_scrap + "|" + comments + "|" + num_of_view + "|" + relation_play + "|" + relation_journal + "|" + keyword + "|" + banner_img + "|" + thumbnail1_img + "|" + thumbnail2_img + "|" + file);
        db.close();
    }

    //update
    public void update_journal(int id, String title, String subtitle, String editor, String category, int num_of_scrap, String comments, int num_of_view, String relation_play, String relation_journal, String keyword, String banner_img, String thumbnail1_img, String thumbnail2_img, String file){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Journal SET journal_title='" + title + "', journal_subtitle= '" + subtitle + "', journal_editor= '" + editor +"', journal_category= '" + category + "',journal_num_of_scrap= '" + num_of_scrap + "',journal_comments= '" + comments + "', journal_num_of_view= '" + num_of_view + "', journal_relation_play = '" + relation_play + "', journal_relation_journal = '" + relation_journal + "', journal_keyword='" + keyword + "', journal_banner_img='" + banner_img + "', journal_thumbnail1_img = '"+ thumbnail1_img +"', journal_thumbnail2_img = '"+ thumbnail2_img +"', journal_file = '"+ file+"' WHERE journal_id = '" + id + "';");
        Log.d("update_journal", "update_Journal: " + id + "|" + title + "|" + subtitle + "|" + editor + "|" + category + "|" + num_of_scrap + "|" + comments + "|" + num_of_view + "|" + relation_play + "|" + relation_journal + "|" + keyword + "|" + banner_img + "|" + thumbnail1_img + "|" + thumbnail2_img + "|" + file);
        db.close();
    }


    //delete
    public void delete_journal(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Journal WHERE journal_id ='" + id + "';");
        Log.d("delete_journal", "delete_journal: " + id);
        db.close();
    }

    //Journal Table의 모든 Tuple get
    public ArrayList<Journal> getAllJournal(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Journal", null);

        ArrayList<Journal> journalArrayList = new ArrayList<>();
        while(cursor.moveToNext()){
            journalArrayList.add(new Journal(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14)));
        }
        for(int i=0; i<journalArrayList.size(); i++){
            Log.d("getAllJournal", "getAllJournal: " + journalArrayList.get(i).getTitle() +" | "+ journalArrayList.get(i).getSubtitle() +" | "+ journalArrayList.get(i).getEditor() +" | "+ journalArrayList.get(i).getCategory() +" | "+ journalArrayList.get(i).getNum_of_scrap() +" | " + journalArrayList.get(i).getComments()  +" | "+ journalArrayList.get(i).getNum_of_view()  +" | "+ journalArrayList.get(i).getRelation_play()  +" | "+ journalArrayList.get(i).getRelation_journal()  +" | "+ journalArrayList.get(i).getKeyword()  +" | "+ journalArrayList.get(i).getBanner_img()  +" | "+ journalArrayList.get(i).getThumbnail1_img()  +" | "+ journalArrayList.get(i).getThumbnail2_img()  +" | "+ journalArrayList.get(i).getFile());
        }
        return journalArrayList;
    }

    public boolean isExistJournalID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT journal_title FROM Journal WHERE journal_id = "+ id +"", null);
        boolean check = false;
        while(cursor.moveToNext()){
            check = true;
        }
        if (check) Log.d("isExistJournalID: ", "journal_id = " + id + " is already exists!!");
        return check;
    }

    public String getJournalTitle(int id){
        SQLiteDatabase db = getReadableDatabase();
        String title = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_title FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            title = cursor.getString(0);
        }
        Log.i("getJournalTitle", "getJournalTitle: " + title);
        return title;
    }

    public String getJournalSubtitle(int id){
        SQLiteDatabase db = getReadableDatabase();
        String subtitle = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_subtitle FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            subtitle = cursor.getString(0);
        }
        Log.i("getJournalSubtitle", "getJournalSubtitle: " + subtitle);
        return subtitle;
    }

    public String getJournalEditor(int id){
        SQLiteDatabase db = getReadableDatabase();
        String editor = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_editor FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            editor = cursor.getString(0);
        }
        Log.i("getJournalEditor", "getJournalEditor: " + editor);
        return editor;
    }

    public String getJournalCategory(int id){
        SQLiteDatabase db = getReadableDatabase();
        String category = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_category FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            category = cursor.getString(0);
        }
        Log.i("getJournalCategory", "getJournalCategory: " + category);
        return category;
    }

    public int getJournalNum_of_scrap(int id){
        SQLiteDatabase db = getReadableDatabase();
        int num_of_scrap = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_num_of_scrap FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            num_of_scrap = cursor.getInt(0);
        }
        Log.i("getJournalNum_of_scrap", "getJournalNum_of_scrap: " + num_of_scrap);
        return num_of_scrap;
    }

    public String getJournalComments(int id){
        SQLiteDatabase db = getReadableDatabase();
        String comments = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_comments FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            comments = cursor.getString(0);
        }
        Log.i("getJournalComments", "getJournalComments: " + comments);
        return comments;
    }

    public int getJournalNum_of_view(int id){
        SQLiteDatabase db = getReadableDatabase();
        int num_of_view = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_num_of_view FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            num_of_view = cursor.getInt(0);
        }
        Log.i("getJournalNum_of_view", "getJournalNum_of_view: " + num_of_view);
        return num_of_view;
    }

    public String getJournalRelation_play(int id){
        SQLiteDatabase db = getReadableDatabase();
        String relation_play = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_relation_play FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            relation_play = cursor.getString(0);
        }
        Log.i("getJournalRelation_play", "getJournalRelation_play: " + relation_play);
        return relation_play;
    }

    public String getJournalRelation_journal(int id){
        SQLiteDatabase db = getReadableDatabase();
        String relation_journal = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_relation_journal FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            relation_journal = cursor.getString(0);
        }
        Log.i("getJournalRelation_jour", "getJournalRelation_journal: " + relation_journal);
        return relation_journal;
    }

    public String getJournalKeyword(int id){
        SQLiteDatabase db = getReadableDatabase();
        String keyword = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_keyword FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            keyword = cursor.getString(0);
        }
        Log.i("getJournalKeyword", "getJournalKeyword: " + keyword);
        return keyword;
    }

    public String getJournalBanner_img(int id){
        SQLiteDatabase db = getReadableDatabase();
        String banner_img = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_banner_img FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            banner_img = cursor.getString(0);
        }
        Log.i("getJournalBanner_img", "getJournalBanner_img: " + banner_img);
        return banner_img;
    }

    public String getJournalThumbnail1_img(int id){
        SQLiteDatabase db = getReadableDatabase();
        String thumbnail1_img = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_thumbnail1_img FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            thumbnail1_img = cursor.getString(0);
        }
        Log.i("getJournalThumbnail1_im", "getJournalThumbnail1_img: " + thumbnail1_img);
        return thumbnail1_img;
    }

    public String getJournalThumbnail2_img(int id){
        SQLiteDatabase db = getReadableDatabase();
        String thumbnail2_img = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_thumbnail2_img FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            thumbnail2_img = cursor.getString(0);
        }
        Log.i("getJournalThumbnail2_im", "getJournalThumbnail2_img: " + thumbnail2_img);
        return thumbnail2_img;
    }

    public String getJournalFile(int id){
        SQLiteDatabase db = getReadableDatabase();
        String file = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT journal_file FROM Journal WHERE journal_id = "+ id +"", null);
        while(cursor.moveToNext()){
            file = cursor.getString(0);
        }
        Log.i("getJournalFile", "getJournalFile: " + file);
        return file;
    }

    public ArrayList<Recommend> getkeywordjournal_id(String keyword){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Recommend> keywords = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT journal_id FROM Journal WHERE journal_keyword LIKE ? ");
        String[] params = {"%"+keyword+"%"};
        Cursor cursor;
        cursor = db.rawQuery(sb.toString(),params);
        while(cursor.moveToNext()){
            Recommend recommend = new Recommend(cursor.getInt(0));
            keywords.add(recommend);
        }
        return keywords;
    }

    public ArrayList<Recommend> getalljournal_id(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT journal_id FROM Journal",null);
        ArrayList<Recommend> journalArraylist = new ArrayList<>();
        while(cursor.moveToNext()){
            journalArraylist.add(new Recommend(cursor.getInt(0)));
        }
        return journalArraylist;
    }
}
