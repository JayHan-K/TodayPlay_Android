package co.kr.todayplay.fragment.perform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformReviewAdapter;
import co.kr.todayplay.adapter.PerformTotalReviewAdapter;

public class PerformTotalReviewFragment extends Fragment {
    ImageButton back_btn;
    Button write_btn;
    TextView perform_title_tv, perform_date_tv, perform_director_tv, perform_actor_tv;
    RecyclerView review_rv;
    ImageView perform_poster_iv;

    public PerformTotalReviewFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_total_review,container,false);
        back_btn = (ImageButton)viewGroup.findViewById(R.id.back_btn);
        write_btn = (Button)viewGroup.findViewById(R.id.write_review_btn);
        perform_actor_tv = (TextView)viewGroup.findViewById(R.id.actor_tv);
        perform_date_tv = (TextView)viewGroup.findViewById(R.id.perform_date_tv);
        perform_director_tv = (TextView)viewGroup.findViewById(R.id.direct_tv);
        perform_title_tv = (TextView)viewGroup.findViewById(R.id.perform_title_tv);
        review_rv = (RecyclerView)viewGroup.findViewById(R.id.perform_review_rv);
        perform_poster_iv = (ImageView)viewGroup.findViewById(R.id.perform_iv);

        final ArrayList<PerformTotalReviewAdapter.ReviewItem> data = new ArrayList<>();
        ArrayList<Integer> image_data = new ArrayList<>();
        image_data.add(R.drawable.poster_sample1);
        image_data.add(R.drawable.poster_sample2);
        image_data.add(R.drawable.poster_sample3);
        image_data.add(R.drawable.poster_sample4);
        image_data.add(R.drawable.poster_sample5);
        image_data.add(R.drawable.poster_sample6);
        data.add(new PerformTotalReviewAdapter.ReviewItem(R.drawable.icon_mypage, "제인", true, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ","무대장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ", image_data, 23));
        data.add(new PerformTotalReviewAdapter.ReviewItem(R.drawable.icon_mypage, "제인1", false, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ", "무대장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ", 23));

        final PerformTotalReviewAdapter performTotalReviewAdapter = new PerformTotalReviewAdapter(getActivity().getApplicationContext(), data);
        review_rv.setAdapter(performTotalReviewAdapter);
        review_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        return viewGroup;
    }
}
