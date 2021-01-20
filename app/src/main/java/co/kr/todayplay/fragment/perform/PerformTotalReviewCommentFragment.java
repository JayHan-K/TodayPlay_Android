package co.kr.todayplay.fragment.perform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformReviewAdapter;
import co.kr.todayplay.adapter.PerformReviewCommentAdapter;

public class PerformTotalReviewCommentFragment extends Fragment {
    ImageView review_user_iv;
    RecyclerView review_img_rv, comment_rv;
    TextView user_name_tv, user_age_level_tv, review_date_tv, good_review_tv, bad_review_tv, tip_review_tv, num_heart, num_review;
    Button comment_btn, write_review_btn;
    EditText comment_et;
    PerformWriteReviewFragment performWriteReviewFragment = new PerformWriteReviewFragment();

    public PerformTotalReviewCommentFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_total_review_comment,container,false);
        review_user_iv = (ImageView) viewGroup.findViewById(R.id.user_profile_iv);
        review_img_rv = (RecyclerView)viewGroup.findViewById(R.id.review_photo_rv);
        user_name_tv = (TextView)viewGroup.findViewById(R.id.user_name_tv);
        user_age_level_tv = (TextView)viewGroup.findViewById(R.id.age_level_tv);
        review_date_tv = (TextView)viewGroup.findViewById(R.id.review_date_tv);
        good_review_tv = (TextView)viewGroup.findViewById(R.id.good_review_tv);
        bad_review_tv = (TextView)viewGroup.findViewById(R.id.bad_review_tv);
        tip_review_tv = (TextView)viewGroup.findViewById(R.id.tip_tv);
        num_heart = (TextView)viewGroup.findViewById(R.id.num_heart_tv);
        num_review = (TextView)viewGroup.findViewById(R.id.num_review_tv);
        comment_btn = (Button)viewGroup.findViewById(R.id.comment_btn);
        write_review_btn = (Button)viewGroup.findViewById(R.id.write_review_btn);
        comment_et = (EditText)viewGroup.findViewById(R.id.write_comment_et);
        comment_rv = (RecyclerView)viewGroup.findViewById(R.id.comment_rv);

        final ArrayList<PerformReviewCommentAdapter.CommentItem> data = new ArrayList<>();
        ArrayList<PerformReviewCommentAdapter.CommentItem> recomment_data = new ArrayList<>();
        recomment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23", "꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", true));
        data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23","꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", recomment_data, false));
        data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23","꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", false));

        final PerformReviewCommentAdapter performReviewCommentAdapter = new PerformReviewCommentAdapter(getActivity().getApplicationContext(), data);
        comment_rv.setAdapter(performReviewCommentAdapter);
        comment_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        write_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(performWriteReviewFragment);
            }
        });

        return viewGroup;
    }
}
