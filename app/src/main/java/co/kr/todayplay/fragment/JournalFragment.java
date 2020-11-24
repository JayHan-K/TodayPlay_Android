package co.kr.todayplay.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalListAdaptor;
import co.kr.todayplay.fragment.Journal.JournalChickFragment;
import co.kr.todayplay.fragment.Journal.JournalHotFragment;

public class JournalFragment extends Fragment {
    private FragmentManager fragmentManager;
    private JournalChickFragment journalChickFragment;
    private JournalHotFragment journalHotFragment;
    private FragmentTransaction fragmentTransaction;
    RecyclerView recyclerView;

    public JournalFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_journal, container, false);
        //MainActivity mainActivity = (MainActivity)getActivity();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.journal_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        List<JournalListAdaptor.Item> data = new ArrayList<>();
        data.add(new JournalListAdaptor.Item(R.drawable.editors_sample2,"HOT"));
        data.add(new JournalListAdaptor.Item(R.drawable.editors_sample1,"병아리"));
        recyclerView.setAdapter(new JournalListAdaptor(data));

        return rootView;
    }
}
