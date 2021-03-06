package co.kr.todayplay.object;

import java.io.Serializable;

public class Ranking implements Serializable {
    String category;
    int order;
    int play_id;

    public Ranking(String category,int order,int play_id){
        this.category = category;
        this.order = order;
        this.play_id= play_id;
    }

    public String getCategory() {
        return category;
    }

    public int getOrder() {
        return order;
    }

    public int getPlay_id() {
        return play_id;
    }

}
