package co.kr.todayplay.PlayDB;

public class Perform {
    int play_id;
    String title;
    String category;
    String poster;
    String keywords;
    int main_journal;
    String relative_journal;
    String thumbnail1;
    String thumbnail2;
    String videos;


    public Perform(int play_id, String title, String category, String keywords, int main_journal, String thumbnail1, String thumbnail2, String relative_journal, String videos, String poster){
        this.play_id = play_id;
        this.title = title;
        this.category = category;
        this.keywords =keywords;
        this.main_journal = main_journal;
        this.thumbnail1 =thumbnail1;
        this.thumbnail2 = thumbnail2;
        this.relative_journal = relative_journal;
        this.videos = videos;
        this.poster = poster;

    }

    public int getPlay_id() {
        return play_id;
    }

    public String getCategory() {
        return category;
    }

    public String getPoster() {
        return poster;
    }

    public String getKeywords() {
        return keywords;
    }

    public int getMain_journal() {
        return main_journal;
    }

    public String getRelative_journal() {
        return relative_journal;
    }

    public String getThumbnail1() {
        return thumbnail1;
    }

    public String getThumbnail2() {
        return thumbnail2;
    }

    public String getVideos() {
        return videos;
    }

    public void setPlay_id(int play_id) {
        this.play_id = play_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setMain_journal(int main_journal) {
        this.main_journal = main_journal;
    }

    public void setRelative_journal(String relative_journal) {
        this.relative_journal = relative_journal;
    }

    public void setThumbnail1(String thumbnail1) {
        this.thumbnail1 = thumbnail1;
    }

    public void setThumbnail2(String thumbnail2) {
        this.thumbnail2 = thumbnail2;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }
}
