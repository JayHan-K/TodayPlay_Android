package co.kr.todayplay.DBHelper.ReviewDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class ReviewDBHelper extends SQLiteOpenHelper {
    public ReviewDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Review(review_id INTEGER PRIMARY KEY, user_id INTEGER,review_date TEXT, review_good TEXT, review_bad TEXT, review_tip TEXT, review_pic TEXT, comment INTEGER,recommend INTEGER, review_num_of_heart);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //insert
    public void insert_Review(int review_id, int user_id, String review_date, String review_good, String review_bad, String review_tip, String review_pic, int comment, int recommend, int review_num_of_heart) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Review (review_id, user_id, review_date, review_good, review_bad, review_tip, review_pic, commnet, recommend, review_num_of_heart) Values('" + review_id + "','" + user_id + "','" + review_date + "','" + review_good + "','" + review_bad + "', '" + review_tip + "', '" + review_pic + "','" + comment + "','" + recommend + "','" + review_num_of_heart + "');");
        Log.d("insert_Review", "insert_Review: " + review_id + "|" + user_id + "|" + review_date + "|" + review_good + "|" + review_bad + "|" + review_tip + "|" + review_pic + "|" + comment + "|" + recommend + "|" + review_num_of_heart);

        db.close();
    }

    //update
    public void update_Review(int review_id, int user_id, String review_date, String review_good, String review_bad, String review_tip, String review_pic, int comment, int recommend, int review_num_of_heart) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Review SET review_id='" + review_id + "', user_id='" + user_id + "', review_date= '" + review_date + "', review_good= '" + review_good + "', review_bad= '" + review_bad + "', review_tip= '" + review_tip + "', review_pic= '" + review_pic + "', comment= '" + comment + "', recommend= '" + recommend + "', review_num_of_heart= '" + review_num_of_heart + "';");
        Log.d("update_Review", "update_Review: " + review_id + "|" + user_id + "|" +review_date + "|" + review_good + "|" +  review_bad + "|"+ review_tip + "|" + review_pic + "|" + comment + "|" + recommend + "|" + review_num_of_heart );

        db.close();
    }

    //delete
    public void delete_Review(int review_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Review WHERE review_id ='" + review_id + "';");
        Log.d("delete_Review", "delete_Review: " + review_id);
        db.close();
    }

    public int getReviewUser_Id(int review_id) {
        SQLiteDatabase db = getReadableDatabase();
        int user_id =0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT user_id FROM Review WHERE review_id = " + review_id + "", null);
        while (cursor.moveToNext()) {
            user_id = cursor.getInt(0);
        }
        Log.i("getReviewuser_id", "user_id"+user_id );
        return user_id;
    }

    public String getReviewReview_Date(int review_id) {
        SQLiteDatabase db = getReadableDatabase();
        String review_date = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT review_date FROM Reviewv WHERE review_id = " + review_id + "", null);
        while (cursor.moveToNext()) {
            review_date = cursor.getString(0);
        }
        Log.i("Review_date", review_date + " ");
        return review_date;
    }

    public String getReviewReview_Good(int review_id) {
        SQLiteDatabase db = getReadableDatabase();
        String review_good = "";
        Cursor cursor;
        int i = 0;
        cursor = db.rawQuery("SELECT Review_good FROM Review WHERE review_id = " + review_id + "", null);
        while (cursor.moveToNext()) {
            review_good = cursor.getString(0);
            i++;
        }
        Log.i("getReviewReview_Good", review_good + " ");
        return review_good;
    }

    public int getReviewReview_Bad(int review_id) {
        SQLiteDatabase db = getReadableDatabase();
        int review_bad = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT review_bad FROM review WHERE review_id = " + review_id + "", null);
        while (cursor.moveToNext()) {
            review_bad = cursor.getInt(0);
        }
        Log.i("getReviewReview_Bad", review_bad + " ");
        return review_bad;
    }

    public int getReviewReview_tip(int review_id) {
        SQLiteDatabase db = getReadableDatabase();
        int review_tip = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT review_tip FROM review WHERE review_id = " + review_id + "", null);
        while (cursor.moveToNext()) {
            review_tip = cursor.getInt(0);
        }
        Log.i("getReviewReview_Bad", review_tip + " ");
        return review_tip;
    }

    public String getReviewReview_Pic(int review_id) {
        SQLiteDatabase db = getReadableDatabase();
        String review_Pic = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT review_pic FROM Review WHERE review_id = " + review_id + "", null);
        while (cursor.moveToNext()) {
            review_Pic = cursor.getString(0);
        }
        Log.i("getReviewReview_Pic", review_Pic + " ");
        return review_Pic;
    }

    public int getReviewComment(int review_id) {
        SQLiteDatabase db = getReadableDatabase();
        int comment = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT comment FROM Review WHERE review_id = " + review_id + "", null);
        while (cursor.moveToNext()) {
            comment = cursor.getInt(0);
        }
        Log.i("getReviewComment", comment + " ");
        return comment;
    }

    public String getReviewRecommend(int review_id) {
        SQLiteDatabase db = getReadableDatabase();
        String Recommend = "";
        Cursor cursor;
        cursor = db.rawQuery("SELECT recommend FROM Review WHERE review_id = " + review_id + "", null);
        while (cursor.moveToNext()) {
            Recommend = cursor.getString(0);
        }
        Log.i("getReviewRecommend", Recommend + " ");
        return Recommend;
    }

    public int getReviewNum_of_heart(int review_id) {
        SQLiteDatabase db = getReadableDatabase();
        int num_of_heart = 0;
        Cursor cursor;
        cursor = db.rawQuery("SELECT review_num_of_heart FROM Review WHERE review_id = " + review_id + "", null);
        while (cursor.moveToNext()) {
            num_of_heart = cursor.getInt(0);
        }
        Log.i("getReviewNum_of_heart", num_of_heart + " ");
        return num_of_heart;
    }

    public ArrayList<Review> getAllReview() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Review", null);

        ArrayList<Review> journalArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            journalArrayList.add(new Review(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9)));

        }
        return journalArrayList;
    }
}
