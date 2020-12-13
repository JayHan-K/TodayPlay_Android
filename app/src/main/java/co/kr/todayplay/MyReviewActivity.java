package co.kr.todayplay;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.adapter.MyPageReviewCommentAdapter;
import co.kr.todayplay.object.DetailComment;

public class MyReviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreview);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.myreview_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        ArrayList<DetailComment> data = new ArrayList<>();

        data.add(new DetailComment(
                "썸씽로튼",
                "2020.00.00",
                R.drawable.editor_profile_4,
                "쥬",
                "20대 / 병아리",
                true,
                "다들 연기장인들이었어요. 무대의상도 화려하고, 오케스트라의 웅장함이 가슴 뛰게 하네요ㅎㅎ 한번 더 보고 싶어요!",
                "하지만 너무 비싸요... VIP석이 절반은 넘는 듯ㅜ 할인 종류도 부족해서.. 뭔가 할인이 더 있으면 좋을 듯요..",
                128,
                35)
        );

        data.add(new DetailComment(
                "레미제라블",
                "2020.00.00",
                R.drawable.editor_profile_4,
                "쥬",
                "20대 / 병아리",
                true,
                "화려한 의상이랑 춤으로 즐겁게 보기 좋았어요! 앙상블들 갈아서 만든 춤이라더니 진짜 군무 대박이에요!",
                "화려하지만 그에 반해 스토리가 약한 느낌.. 솔직히 뻔하고 지루한 스토리에요..",
                128,
                35)
        );

        recyclerView.setAdapter(new MyPageReviewCommentAdapter(data,getApplicationContext()));

    }
}
