package co.kr.todayplay.fragment.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalChickListAdapter;

public class JournalChickFragment extends Fragment {
    RecyclerView chick_rv;
    public static JournalChickFragment newInstance(){ return new JournalChickFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_chick, container, false);
        chick_rv = (RecyclerView)viewGroup.findViewById(R.id.chick_rv);
        chick_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<JournalChickListAdapter.ChickItem> data = new ArrayList<>();
        data.add(new JournalChickListAdapter.ChickItem(R.drawable.editors_sample1,"공연 병아리 필수","'2020년 공연 트랜'"));
        data.add(new JournalChickListAdapter.ChickItem(R.drawable.editors_sample2,"연인과 함께","'애정 뿜뿜 데이트하기 좋은 '"));
        data.add(new JournalChickListAdapter.ChickItem(R.drawable.editors_sample3,"집순이도 볼 수 있어","'Show Must Go On'"));
        data.add(new JournalChickListAdapter.ChickItem(R.drawable.editors_sample4,"해외가면 꼭 봐","'토니어워즈 모아보기'"));
        data.add(new JournalChickListAdapter.ChickItem(R.drawable.family,"2020 공연트렌드","'토니어워즈 모아보기'"));
        data.add(new JournalChickListAdapter.ChickItem(R.drawable.alone,"샤롯데 씨어 첫! 방문기","'토니어워즈 모아보기'"));
        data.add(new JournalChickListAdapter.ChickItem(R.drawable.the42nd,"나는 도대체 어디...?","'토니어워즈 모아보기'"));
        data.add(new JournalChickListAdapter.ChickItem(R.drawable.tip,"4대 뮤지컬 오페라의 유령","'토니어워즈 모아보기'"));
        data.add(new JournalChickListAdapter.ChickItem(R.drawable.tip_,"극장 꿀팁","'토니어워즈 모아보기'"));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position==6){
                    return 2;
                }
                else return 1;
            }
        });
        chick_rv.setLayoutManager(gridLayoutManager);
        JournalChickListAdapter journalChickListAdapter = new JournalChickListAdapter(data);
        chick_rv.setAdapter(journalChickListAdapter);
        return viewGroup;
    }
}
