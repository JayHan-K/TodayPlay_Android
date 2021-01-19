package co.kr.todayplay.fragment.perform;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformReviewAdapter;

public class PerformReviewFragment extends Fragment {
    RecyclerView review_rv;
    Button write_review_btn, more_review_btn;
    BarChart satisfy_ratio_chart;
    PerformTotalReviewFragment performTotalReviewFragment = new PerformTotalReviewFragment();

    public PerformReviewFragment(){}

    private ArrayList<BarEntry> average_satisfy_data(){
        ArrayList<BarEntry> data_val = new ArrayList<>();
        data_val.add(new BarEntry(0,1));
        data_val.add(new BarEntry(1,2));
        data_val.add(new BarEntry(2,3));
        data_val.add(new BarEntry(3,5));
        data_val.add(new BarEntry(4,2));
        data_val.add(new BarEntry(5,1));
        return data_val;
    }

    private ArrayList<BarEntry> perform_satisfy_data(){
        ArrayList<BarEntry> data_val = new ArrayList<>();
        data_val.add(new BarEntry(0,1));
        data_val.add(new BarEntry(1,5));
        data_val.add(new BarEntry(2,4));
        data_val.add(new BarEntry(3,4));
        data_val.add(new BarEntry(4,3));
        data_val.add(new BarEntry(5,2));
        return data_val;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int review_data_size = 5;
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_review,container,false);
        review_rv = (RecyclerView)viewGroup.findViewById(R.id.review_rv);
        write_review_btn = (Button)viewGroup.findViewById(R.id.write_rv_btn);
        more_review_btn = (Button)viewGroup.findViewById(R.id.more_rv_btn);
        satisfy_ratio_chart = (BarChart)viewGroup.findViewById(R.id.satisfy_ratio_barChart);

        //--만족도 비율 차트 Start --
        /*
        final String xVal[]={"10대","20대","30대","40대","50대","60대+"};
        XAxis xAxis = satisfy_ratio_chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return xVal[(int) value-1];
            }
        });
        */
        BarDataSet barDataSet = new BarDataSet(average_satisfy_data(), "평균 만족도");
        barDataSet.setColor(Color.WHITE);
        BarDataSet barDataSet2 = new BarDataSet(perform_satisfy_data(),"이 공연 만족도");
        barDataSet2.setColor(Color.YELLOW);

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barData.addDataSet(barDataSet2);

        satisfy_ratio_chart.setData(barData);
        satisfy_ratio_chart.invalidate();
        //--만족도 비율 차트 End --

        final ArrayList<PerformReviewAdapter.ReviewItem> data = new ArrayList<>();
        ArrayList<Integer> image_data = new ArrayList<>();
        image_data.add(R.drawable.poster_sample1);
        image_data.add(R.drawable.poster_sample2);
        image_data.add(R.drawable.poster_sample3);
        image_data.add(R.drawable.poster_sample4);
        image_data.add(R.drawable.poster_sample5);
        image_data.add(R.drawable.poster_sample6);
        data.add(new PerformReviewAdapter.ReviewItem(R.drawable.icon_mypage, "제인", true, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ", image_data));
        data.add(new PerformReviewAdapter.ReviewItem(R.drawable.icon_mypage, "제인1", false, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 "));

        final PerformReviewAdapter performReviewAdapter = new PerformReviewAdapter(getActivity().getApplicationContext(), data);
        review_rv.setAdapter(performReviewAdapter);

        if(review_data_size > 2) {
            more_review_btn.setText((review_data_size - 2) + "개의 후기 더 보기");
        }
        else more_review_btn.setVisibility(View.GONE);
        review_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        more_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(performTotalReviewFragment);
            }
        });

        return viewGroup;
    }
}
