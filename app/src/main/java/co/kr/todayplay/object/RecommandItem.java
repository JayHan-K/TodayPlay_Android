package co.kr.todayplay.object;

public class RecommandItem {
    private int drawable;
    private String title;

    public RecommandItem(int drawable, String title){
        this.drawable = drawable;
        this.title = title;
    }
    public int getDrawable(){return drawable;}
    public String getTitle(){return title;}
}
