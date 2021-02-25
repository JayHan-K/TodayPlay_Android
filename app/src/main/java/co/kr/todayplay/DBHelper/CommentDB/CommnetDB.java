package co.kr.todayplay.DBHelper.CommentDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class CommnetDB extends SQLiteOpenHelper {
    public CommnetDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE IF NOT EXISTS Comment (comment_id INTEGER PRIMARY KEY, user_id INTEGER, comment TEXT, comment_date TEXT, comment_reply INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //insert
    public void insert_comment(int comment_id, int user_id, String comment_date, int comment_reply) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Comment(comment_id, user_id ,comment_date, comment_reply) VALUES('" + comment_id + "', '" + user_id + "', '" + comment_date + "', '" + comment_reply + "');");
        Log.d("insert_comment", "insert_comment: " + comment_id + "|" + user_id + "|" + comment_date + "|" + comment_reply);
        db.close();
    }

    //update
    public void update_comment(int comment_id, int user_id, String comment_date, int comment_reply) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Comment SET comment_id='" + comment_id + "', user_id='" + user_id + "', comment_date= '" + comment_date + "', comment_reply= '" + comment_reply + "';");
        Log.d("update_Comment", "update_Comment: " + comment_id + "|" + user_id + "|" + comment_date + "|" + comment_reply );

        db.close();
    }

    //delete
    public void delete_Comment(int comment_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Comment WHERE comment_id ='" + comment_id + "';");
        Log.d("delete_Comment", "delete_Comment: " + comment_id);
        db.close();
    }

    public int getUser_Id(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        int comment_user_id = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT user_id FROM Comment WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            comment_user_id = cursor.getInt(0);
        }
        Log.i("getUser_Id", "user_id"+comment_user_id );
        return comment_user_id;
    }

    public String getComment(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        String comment = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT comment FROM Comment WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            comment = cursor.getString(0);
        }
        Log.i("getComment", "comment"+comment );
        return comment;
    }

    public String getCommentDate(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        String comment_date = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT comment_date FROM Comment WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            comment_date = cursor.getString(0);
        }
        Log.i("getCommentDate", "comment_date"+comment_date );
        return comment_date;
    }

    public int getComment_reply(int comment_id) {
        SQLiteDatabase db = getReadableDatabase();
        int recomment_id = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT comment_reply FROM Comment WHERE comment_id = " + comment_id + "", null);
        while (cursor.moveToNext()) {
            recomment_id = cursor.getInt(0);
        }
        Log.i("getComment_reply", "comment_reply"+recomment_id );
        return recomment_id;
    }
}
