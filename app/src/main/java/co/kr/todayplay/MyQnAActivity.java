package co.kr.todayplay;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.kr.todayplay.adapter.MyPageQnAAdapter;

public class MyQnAActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myqna);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.myqna_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        List<MyPageQnAAdapter.Item> data = new ArrayList<>();

        data.add(new MyPageQnAAdapter.Item(MyPageQnAAdapter.QUESTION,"공연 병아리 입니다. 공연 추천 부탁드려요", "2020.00.00","공연 추천","관리자님 도와줘요","마스터",5,47));
        data.add(new MyPageQnAAdapter.Item(MyPageQnAAdapter.ANSWER,"뮤지컬을 선호하신다면 ~~~을 추천드리고, 연극을 보고싶으시다면, ~~~~~을 추천드립니다! 이외에 장르별로도 다양하게 있어서 참고해 공연을 선택해주시면....","2020.07.09"));

        data.add(new MyPageQnAAdapter.Item(MyPageQnAAdapter.QUESTION,"세종문화회관 근처 맛집 있을까요?", "2020.00.00","공연 추천","아무나 도와줘요","마스터",5,47));
        data.add(new MyPageQnAAdapter.Item(MyPageQnAAdapter.ANSWER,"3뮤지컬을 선호하신다면 ~~~을 추천드리고, 연극을 보고싶으시다면, ~~~~~을 추천드립니다! 이외에 장르별로도 다양하게 있어서 참고해 공연을 선택해주시면....","2020.07.09"));

        recyclerView.setAdapter(new MyPageQnAAdapter(data));

    }
}
