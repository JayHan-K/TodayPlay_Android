package co.kr.todayplay.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.MyPageReviewCommentAdapter;
import co.kr.todayplay.object.DetailComment;
import im.dacer.androidcharts.LineView;

public class MyReviewActivity extends Fragment {

    public MyReviewActivity(){}

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_myreview, container, false);
        LineView lineView = (LineView)viewGroup.findViewById(R.id.line_view);
        Button review_to_profile = viewGroup.findViewById(R.id.back_profile3);

        RecyclerView recyclerView = (RecyclerView) viewGroup.findViewById(R.id.myreview_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));
        ArrayList<DetailComment> data = new ArrayList<>();
        ArrayList<String> date =new ArrayList<String>();
        ArrayList<Integer> wrote = new ArrayList<>();

        date.add("1월");
        date.add("2월");
        date.add("3월");
        date.add("4월");
        date.add("5월");
        date.add("6월");
        date.add("7월");
        date.add("8월");
        date.add("9월");
        date.add("10월");
        date.add("11월");
        date.add("12월");
        wrote.add(0);
        wrote.add(2);
        wrote.add(1);
        wrote.add(1);
        wrote.add(0);
        wrote.add(0);
        wrote.add(2);
        wrote.add(2);
        wrote.add(1);
        wrote.add(2);
        wrote.add(0);
        wrote.add(3);
        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(wrote);

        lineView.setDrawDotLine(false);
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY);
        lineView.setColorArray(new int[]{Color.parseColor("#ffe826")});
        lineView.setBottomTextList(date);
        lineView.setDataList(dataLists);



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

        review_to_profile.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ProfileFragment parentFrag = (ProfileFragment) MyReviewActivity.this.getParentFragment() ;
                parentFrag.BackToHome();

            }
        }));

        recyclerView.setAdapter(new MyPageReviewCommentAdapter(data,this.getContext()));
        return viewGroup;

    }

}
