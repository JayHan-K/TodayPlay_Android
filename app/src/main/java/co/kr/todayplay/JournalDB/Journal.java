package co.kr.todayplay.JournalDB;

public class Journal {
    int id;
    String title;
    String subtitle;
    String editor;
    String category;
    int num_of_scrap;
    String comments;
    int num_of_view;
    String relation_play;
    String relation_journal;
    String keyword;
    String banner_img;
    String thumbnail1_img;
    String thumbnail2_img;
    String file;

    public Journal(int id, String title, String subtitle, String editor, String category, int num_of_scrap, String comments, int num_of_view, String relation_play, String relation_journal, String keyword, String banner_img, String thumbnail1_img, String thumbnail2_img, String file) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.editor = editor;
        this.category = category;
        this.num_of_scrap = num_of_scrap;
        this.comments = comments;
        this.num_of_view = num_of_view;
        this.relation_play = relation_play;
        this.relation_journal = relation_journal;
        this.keyword = keyword;
        this.banner_img = banner_img;
        this.thumbnail1_img = thumbnail1_img;
        this.thumbnail2_img = thumbnail2_img;
        this.file = file;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getEditor() {
        return editor;
    }

    public String getCategory() {
        return category;
    }

    public int getNum_of_scrap() {
        return num_of_scrap;
    }

    public String getComments() {
        return comments;
    }

    public int getNum_of_view() {
        return num_of_view;
    }

    public String getRelation_play() {
        return relation_play;
    }

    public String getRelation_journal() {
        return relation_journal;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getBanner_img() {
        return banner_img;
    }

    public String getThumbnail1_img() {
        return thumbnail1_img;
    }

    public String getThumbnail2_img() {
        return thumbnail2_img;
    }

    public String getFile() {
        return file;
    }
}
