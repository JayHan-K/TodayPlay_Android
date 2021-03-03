package co.kr.todayplay.object;

public class category_recommend {
    String category;
    String keyword;
    int[] play_id;
    public category_recommend(String category,String keyword,int[] play_id){
        this.category = category;
        this.keyword = keyword;
        this.play_id =play_id;
    }

    public String getCategory() {
        return category;
    }

    public String getKeyword() {
        return keyword;
    }

    public int[] getPlay_id() {
        return play_id;
    }
}
