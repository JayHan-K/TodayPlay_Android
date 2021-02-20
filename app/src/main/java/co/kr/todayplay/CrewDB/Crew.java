package co.kr.todayplay.CrewDB;

public class Crew {
    int id;
    String name;
    String pic;
    String position;

    public Crew(){}
    public Crew(int id, String name, String pic, String position) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPic() {
        return pic;
    }

    public String getPosition() {
        return position;
    }
}

