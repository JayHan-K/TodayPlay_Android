package co.kr.todayplay.object;

public class Journal {
    String journalStr;
    int imageResource;

    public Journal(String journalStr, int imageResource){
        this.imageResource = imageResource;
        this.journalStr = journalStr;
    }
    public String getJournalStr(){
        return this.journalStr;
    }

    public int getImageResource(){
        return this.imageResource;
    }
}
