package co.kr.todayplay.fragment.perform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformReviewImageAdapter;

public class PerformWriteReviewFragment extends Fragment {
    EditText good_et, bad_et, tip_et;
    Button photo_btn, save_btn;
    RecyclerView photo_rv;

    public PerformWriteReviewFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_write_review,container,false);
        good_et = (EditText)viewGroup.findViewById(R.id.good_et);
        bad_et = (EditText)viewGroup.findViewById(R.id.bad_et);
        tip_et = (EditText)viewGroup.findViewById(R.id.tip_et);
        photo_btn = (Button)viewGroup.findViewById(R.id.photo_btn);
        save_btn = (Button)viewGroup.findViewById(R.id.save_btn);
        photo_rv = (RecyclerView)viewGroup.findViewById(R.id.photo_rv);

        ArrayList<Integer> image_data = new ArrayList<>();
        image_data.add(R.drawable.poster_sample1);
        image_data.add(R.drawable.poster_sample2);
        image_data.add(R.drawable.poster_sample3);
        image_data.add(R.drawable.poster_sample4);
        image_data.add(R.drawable.poster_sample5);
        image_data.add(R.drawable.poster_sample6);

        final PerformReviewImageAdapter performReviewImageAdapter = new PerformReviewImageAdapter(image_data);
        photo_rv.setAdapter(performReviewImageAdapter);
        photo_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        return viewGroup;
    }
}
