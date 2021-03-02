package co.kr.todayplay.object;

public class History {
    String poster_img;
    String play_date;
    String play_product_company;
    String play_directer;
    String play_crew;

    public History(String poster_img,String play_date,String play_product_company,String play_directer,String play_crew){
        this.poster_img = poster_img;
        this.play_date = play_date;
        this.play_product_company = play_product_company;
        this.play_directer = play_directer;
        this.play_crew =play_crew;
    }

    public String getPoster_img() {
        return poster_img;
    }

    public String getPlay_date() {
        return play_date;
    }

    public String getPlay_product_company() {
        return play_product_company;
    }

    public String getPlay_directer() {
        return play_directer;
    }

    public String getPlay_crew() {
        return play_crew;
    }
}
