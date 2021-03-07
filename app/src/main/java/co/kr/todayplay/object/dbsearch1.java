package co.kr.todayplay.object;

import java.io.Serializable;

public class dbsearch1 implements Serializable {
    int play_id;
    String img_path;
    String keyword;

    public dbsearch1(int play_id, String img_path,String keyword){
        this.play_id = play_id;
        this.img_path =img_path;
        this.keyword = keyword;
    }

    public int getPlay_id() {
        return play_id;
    }

    public String getImg_path() {
        return img_path;
    }

    public String getKeyword() {
        return keyword;
    }
}
