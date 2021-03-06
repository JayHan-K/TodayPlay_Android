package co.kr.todayplay.object;

import java.io.Serializable;

public class Banner implements Serializable {
    int order;
    int play_id;
    String banner;

    public Banner(int order, int play_id, String banner){
        this.order = order;
        this.play_id = play_id;
        this.banner = banner;
    }

    public int getOrder() {
        return order;
    }

    public int getPlay_id() {
        return play_id;
    }

    public String getBanner() {
        return banner;
    }
}
