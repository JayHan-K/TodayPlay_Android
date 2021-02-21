package co.kr.todayplay.CommentDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class CommentDBHelper extends SQLiteOpenHelper {
    public CommentDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Comment(comment_id INTEGER PRIMARY KEY, user_id INTEGER,comment_date TEXT, comment_good TEXT, comment_bad TEXT,comment_pic TEXT,reply INTEGER,recommend INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //insert
    public void insert_Comment(int comment_id, int user_id, String comment_date, String comment_good, String comment_bad, String comment_pic, int reply, int recommend) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Comment (comment_id, user_id, comment_date, comment_good, comment_bad, comment_pic, reply,recommend) Values('" + comment_id + "','" + user_id + "','" + comment_date + "','" + comment_good + "','" + comment_bad + "','" + comment_pic + "','" + reply + "','" + recommend + "');");
        Log.d("insert_comment", "insert_comment: " + comment_id + "|" + user_id + "|" + comment_date + "|" + comment_good + "|" + comment_bad + "|" + comment_pic + "|" + reply + "|" + recommend + "|"  );

        db.close();
    }

    //update
    public void update_Comment(int comment_id, int user_id, String comment_date, String comment_good, int comment_pic, int reply, int recommend) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Perform SET comment_id='" + comment_id + "',user_id='" + user_id + "',comment_date= '" + comment_date + "',comment_good= '" + comment_good + "',comment_pic= '" + comment_pic + "',reply= '" + reply + "',thumbnail2= '" + recommend + "';");
        Log.d("update_comment", "update_comment: " + comment_id + "|" + user_id + "|" + comment_date + "|" + comment_good + "|" + comment_pic + "|" + reply + "|" + recommend + "|"  );

        db.close();
    }

    //delete
    public void delete_Perform(int comment_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Comment WHERE comment_id ='" + comment_id + "';");
        Log.d("delete_Comment", "delete_Comment: " + comment_id);
        db.close();
    }

    public int getCommentUser_Id(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        int user_id =0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT user_id FROM Comment WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            user_id = cursor.getInt(0);
        }
        Log.i("getCommentuser_id", "user_id"+user_id );
        return user_id;
    }

    public String getCommentComment_Date(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        String comment_date = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT comment_date FROM Commentv WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            comment_date = cursor.getString(0);
        }
        Log.i("comment_date", comment_date + " ");
        return comment_date;
    }

    public String getCommentComment_Good(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        String Comment_good = "";
        Cursor cursor;
        int i = 0;
        cursor = db.rawQuery("SELECT Comment_good FROM Perform WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            Comment_good = cursor.getString(3);
            i++;
        }
        Log.i("getPerformKeywords", Comment_good + " ");
        return Comment_good;
    }

    public int getCommentComment_Bad(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        int Comment_bad = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT Comment_bad FROM Comment WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            Comment_bad = cursor.getInt(4);
        }
        Log.i("getPerformMain_Journal", Comment_bad + " ");
        return Comment_bad;
    }

    public String getCommentComment_Pic(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        String Comment_Pic = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT Comment_Pic FROM Comment WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            Comment_Pic = cursor.getString(5);
        }
        Log.i("getPerformThumbnail1", Comment_Pic + " ");
        return Comment_Pic;
    }

    public String getCommentReply(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        String Reply = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT Reply FROM Comment WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            Reply = cursor.getString(6);
        }
        Log.i("getPerformThumbnail2", Reply + " ");
        return Reply;
    }

    public String getCommentRecommend(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        String Recommend = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT Recommend FROM Comment WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            Recommend = cursor.getString(7);
        }
        Log.i("getPerformRelative", Recommend + " ");
        return Recommend;
    }



    public ArrayList<Comment> getAllPlay() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Comment", null);

        ArrayList<Comment> journalArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            journalArrayList.add(new Comment(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7)));

        }
        return journalArrayList;
    }
}
