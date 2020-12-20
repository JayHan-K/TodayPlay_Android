package co.kr.todayplay.fragment.perform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformDetailJournalAdapter;
import co.kr.todayplay.adapter.PerformDetailStaffAdapter;

public class PerformDetailFragment extends Fragment {
    ImageView perform_iv;
    RecyclerView staff_rv;
    RecyclerView journal_rv;

    public PerformDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_perform_detail,container,false);
        perform_iv = (ImageView)viewGroup.findViewById(R.id.perform_iv);
        staff_rv = (RecyclerView)viewGroup.findViewById(R.id.staff_rv);
        //staff_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.HORIZONTAL, false));
        staff_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<PerformDetailStaffAdapter.StaffItem> data = new ArrayList<>();
        data.add(new PerformDetailStaffAdapter.StaffItem(R.drawable.editor_profile_5, "연출", "이현규"));
        data.add(new PerformDetailStaffAdapter.StaffItem(R.drawable.editor_profile_5, "연출", "이현규"));
        data.add(new PerformDetailStaffAdapter.StaffItem(R.drawable.editor_profile_5, "연출", "이현규"));
        data.add(new PerformDetailStaffAdapter.StaffItem(R.drawable.editor_profile_5, "연출", "이현규"));
        PerformDetailStaffAdapter performDetailStaffAdapter = new PerformDetailStaffAdapter(data);
        staff_rv.setAdapter(performDetailStaffAdapter);

        journal_rv = (RecyclerView)viewGroup.findViewById(R.id.journal_rv);
        //journal_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.HORIZONTAL, false));
        //journal_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<PerformDetailJournalAdapter.JournalItem> data2 = new ArrayList<>();
        data2.add(new PerformDetailJournalAdapter.JournalItem(R.drawable.journal_image_sample1));
        data2.add(new PerformDetailJournalAdapter.JournalItem(R.drawable.journal_image_sample2));
        PerformDetailJournalAdapter performDetailJournalAdapter = new PerformDetailJournalAdapter(data2);
        journal_rv.setAdapter(performDetailJournalAdapter);

        return viewGroup;
    }
}
