package co.kr.todayplay.object;

import java.io.Serializable;

public class Recommend implements Serializable {
    int play_id;


    public Recommend(int play_id){
        this.play_id =play_id;
    }

    public int getPlay_id() {
        return play_id;
    }
}
