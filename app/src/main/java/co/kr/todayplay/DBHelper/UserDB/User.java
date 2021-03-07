package co.kr.todayplay.DBHelper.UserDB;

import java.util.ArrayList;

public class User {
    int user_id;
    ArrayList<Integer> scrap_journal_id = new ArrayList<Integer>();
    ArrayList<Integer> heart_play_id = new ArrayList<Integer>();

    public  User(int user_id){
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public ArrayList<Integer> getHeart_play_id() {
        return heart_play_id;
    }

    public ArrayList<Integer> getScrap_journal_id() {
        return scrap_journal_id;
    }

    public void addScrap(int journal_id){
        this.scrap_journal_id.add(journal_id);
    }

    public void addHeart(int play_id){
        this.heart_play_id.add(play_id);
    }
}
