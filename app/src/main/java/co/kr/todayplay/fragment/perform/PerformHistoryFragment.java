package co.kr.todayplay.fragment.perform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformHistoryAdapter;

public class PerformHistoryFragment extends Fragment {
    RecyclerView history_rv;

    public PerformHistoryFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_history, container, false);
        history_rv = (RecyclerView)viewGroup.findViewById(R.id.history_rv);
        //history_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL, false));
        history_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        ArrayList<PerformHistoryAdapter.Item> data = new ArrayList<>();
        data.add(new PerformHistoryAdapter.Item(R.drawable.poster_sample1,"연극 라이어","2011, 90분", "이윤택 (문화게릴라)", "노심동, 조정우, 민..."));
        data.add(new PerformHistoryAdapter.Item(R.drawable.poster_sample2,"연극 라이어","2011, 90분", "이윤택 (문화게릴라)", "노심동, 조정우, 민..."));
        data.add(new PerformHistoryAdapter.Item(R.drawable.poster_sample3,"연극 라이어","2011, 90분", "이윤택 (문화게릴라)", "노심동, 조정우, 민..."));
        PerformHistoryAdapter performHistoryAdapter = new PerformHistoryAdapter(data);
        history_rv.setAdapter(performHistoryAdapter);

        return viewGroup;
    }
}
