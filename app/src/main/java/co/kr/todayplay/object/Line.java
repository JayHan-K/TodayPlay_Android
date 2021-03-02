package co.kr.todayplay.object;

public class Line {
    int play_id;
    String Line;

    public Line(int play_id, String line){
        this.play_id = play_id;
        this.Line = line;
    }

    public int getPlay_id() {
        return play_id;
    }

    public String getLine() {
        return Line;
    }
}
