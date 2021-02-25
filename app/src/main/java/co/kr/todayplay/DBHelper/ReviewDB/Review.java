package co.kr.todayplay.DBHelper.ReviewDB;

public class Review {
    int review_id;
    int user_id;
    String review_date;
    String review_good;
    String review_bad;
    String review_tip;
    String review_pic;
    int comment;
    int recommend;
    int review_num_of_heart;

    public Review(int review_id, int user_id, String review_date, String review_good, String review_bad, String review_tip, String review_pic, int comment, int recommend, int review_num_of_heart){
        this.review_id = review_id;
        this.user_id = user_id;
        this.review_date = review_date;
        this.review_good =review_good;
        this.review_bad = review_bad;
        this.review_tip = review_tip;
        this.review_pic = review_pic;
        this.comment =comment;
        this.recommend =recommend;
        this.review_num_of_heart = review_num_of_heart;
    }

    public int getReview_id() {
        return review_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getReview_date() {
        return review_date;
    }

    public String getReview_good() {
        return review_good;
    }

    public String getReview_bad() {
        return review_bad;
    }

    public String getReview_tip(){
        return review_tip;
    }

    public String getreview_pic() {
        return review_pic;
    }

    public int getCommnet() {
        return comment;
    }

    public int getReview_num_of_heart() {
        return review_num_of_heart;
    }

    public int getRecommend() {
        return recommend;
    }
}
