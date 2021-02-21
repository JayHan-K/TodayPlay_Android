package co.kr.todayplay.CommentDB;

public class Comment {
    int comment_id;
    int user_id;
    String comment_date;
    String comment_good;
    String comment_bad;
    String comment_pic;
    int reply;
    int recommend;

    public Comment(int comment_id, int user_id, String comment_date, String comment_good, String comment_bad, String comment_pic, int reply, int recommend){
        this.comment_id = comment_id;
        this.user_id = user_id;
        this.comment_date = comment_date;
        this.comment_good =comment_good;
        this.comment_bad = comment_bad;
        this.comment_pic = comment_pic;
        this.reply =reply;
        this.recommend =recommend;
    }

    public int getComment_id() {
        return comment_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getComment_date() {
        return comment_date;
    }

    public String getComment_good() {
        return comment_good;
    }

    public String getComment_bad() {
        return comment_bad;
    }

    public String getComment_pic() {
        return comment_pic;
    }

    public int getReply() {
        return reply;
    }

    public int getRecommend() {
        return recommend;
    }
}
