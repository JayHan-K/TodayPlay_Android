package co.kr.todayplay.object;

public class CategoryRe  {
    int play_id;
    String category;
    String poster;
    int num_of_heart;

    public CategoryRe(int play_id,String category,String poster,int num_of_heart){
        this.play_id =play_id;
        this.category = category;
        this.poster =poster;
        this.num_of_heart = num_of_heart;
    }

    public int getPlay_id() {
        return play_id;
    }

    public String getCategory() {
        return category;
    }

    public String getposter() {
        return poster;
    }

    public int getNum_of_heart() {
        return num_of_heart;
    }

}
