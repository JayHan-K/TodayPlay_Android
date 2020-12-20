package co.kr.todayplay.fragment.perform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformReviewAdapter;

public class PerformReviewFragment extends Fragment {
    RecyclerView review_rv;
    Button write_review_btn, more_review_btn;

    public PerformReviewFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_review,container,false);
        review_rv = (RecyclerView)viewGroup.findViewById(R.id.review_rv);
        write_review_btn = (Button)viewGroup.findViewById(R.id.write_rv_btn);
        more_review_btn = (Button)viewGroup.findViewById(R.id.more_rv_btn);
        //review_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(),LinearLayoutManager.VERTICAL,false));
        //review_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        ArrayList<PerformReviewAdapter.ReviewItem> data = new ArrayList<>();
        data.add(new PerformReviewAdapter.ReviewItem(R.drawable.icon_mypage, "제인", true, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 "));
        data.add(new PerformReviewAdapter.ReviewItem(R.drawable.icon_mypage, "제인", false, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 "));
        PerformReviewAdapter performReviewAdapter = new PerformReviewAdapter(data);
        review_rv.setAdapter(performReviewAdapter);

        return viewGroup;
    }
}
