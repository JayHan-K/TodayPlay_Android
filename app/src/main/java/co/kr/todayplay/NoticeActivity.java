package co.kr.todayplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import co.kr.todayplay.adapter.NoticeAdapter;
import co.kr.todayplay.object.Noticedata;

public class NoticeActivity extends AppCompatActivity {

    RecyclerView noticeRecyclerView;
    Button noticeCloseBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        noticeRecyclerView = (RecyclerView) findViewById(R.id.notice_rv);
        noticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticeRecyclerView.setAdapter(new NoticeAdapter(getNotices(), this));


    }

    ArrayList<Noticedata> getNotices() {
        ArrayList<Noticedata> notices = new ArrayList<>();
        notices.add(new Noticedata(true, "2020 공연 문화 에티켓지키기", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(true, "코로나 19 안전수칙 및 공연 이용 규제 관련 공지", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(true, "공연 연기 일정 확인", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "휴관 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "공연장 찾아오시는 길 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "임시공휴일 휴무 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "출석체크 포인트 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "멤버쉽 혜택 변경 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "오늘의 공연 APP 시스템 정비 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "티켓 예매 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "공연 연기 일정 확인", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "휴관 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "공연장 찾아오시는 길 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "임시공휴일 휴무 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "출석체크 포인트 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "멤버쉽 혜택 변경 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "오늘의 공연 APP 시스템 정비 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "티켓 예매 안내", "오늘의 공연 | 2020-00-00"));
        notices.add(new Noticedata(false, "공연 연기 일정 확인", "오늘의 공연 | 2020-00-00"));

        return notices;

    }
}
