package co.kr.todayplay.fragment.perform;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformHistoryAdapter;
import co.kr.todayplay.adapter.PerformVideoAdapter;

public class PerformVideoFragment extends Fragment {
    RecyclerView video_rv;
    private Context mContext;

    public PerformVideoFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_video, container, false);
        video_rv = (RecyclerView)viewGroup.findViewById(R.id.video_rv);
        //video_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL, false));
        video_rv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        ArrayList<PerformVideoAdapter.Item> data = new ArrayList<>();
        data.add(new PerformVideoAdapter.Item(R.drawable.poster_sample3,"뮤지컬 라이어 쇼케이스","(2020.10.30)", "뮤지컬 라이어 유튜브 공식 채널", "10만회"));
        data.add(new PerformVideoAdapter.Item(R.drawable.poster_sample4,"뮤지컬 라이어 쇼케이스","(2020.10.30)", "뮤지컬 라이어 유튜브 공식 채널", "10만회"));
        data.add(new PerformVideoAdapter.Item(R.drawable.poster_sample5,"뮤지컬 라이어 쇼케이스","(2020.10.30)", "뮤지컬 라이어 유튜브 공식 채널", "10만회"));
        PerformVideoAdapter performVideoAdapter = new PerformVideoAdapter(data);
        video_rv.setAdapter(performVideoAdapter);

        return viewGroup;
    }

}
