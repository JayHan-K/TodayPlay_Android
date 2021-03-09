package co.kr.todayplay.object;

public class RecommandItem {
    private int drawable;
    private String title;
    private int id;

    public RecommandItem(int id, int drawable,String title){
        this.id = id;
        this.drawable = drawable;
        this.title = title;
    }
    public int getId(){return id;}
    public int getDrawable(){return drawable;}
    public String getTitle(){return title;}
}
