package co.kr.todayplay.object;

public class Show {
    private int imageSource;
    private String showName;
    public Show(int imageSource,String showName){
        this.imageSource = imageSource;
        this.showName = showName;
    }

    public String getShowName() {
        return showName;
    }

    public int getImageSource() {
        return imageSource;
    }
}
