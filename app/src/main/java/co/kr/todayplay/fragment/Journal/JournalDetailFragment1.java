package co.kr.todayplay.fragment.Journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalRecommendListAdapter;
import co.kr.todayplay.adapter.PerformReviewCommentAdapter;

public class JournalDetailFragment1 extends Fragment {
    Button more_comment_btn, comment_save_btn;
    ImageButton back_btn, hidden_back_btn, bookmark_btn;
    TextView hidden_journal_title, hidden_journal_subtitle, num_bookmarks_tv, num_bookmarks_tv2, num_comment_tv, num_comment_tv2, num_view_tv, editor_tv;
    RecyclerView comment_rv, recommend_journal_rv;
    EditText comment_et;

    public JournalDetailFragment1(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_detail2, container, false);
        back_btn = (ImageButton) viewGroup.findViewById(R.id.back_btn);
        hidden_back_btn = (ImageButton) viewGroup.findViewById(R.id.hidden_back_btn);
        bookmark_btn = (ImageButton) viewGroup.findViewById(R.id.bookmark_btn);
        more_comment_btn = (Button)viewGroup.findViewById(R.id.more_comment_btn);
        comment_save_btn = (Button)viewGroup.findViewById(R.id.comment_btn);
        hidden_journal_subtitle = (TextView)viewGroup.findViewById(R.id.journal_subtitle_tv);
        hidden_journal_title = (TextView)viewGroup.findViewById(R.id.journal_title_tv);
        num_bookmarks_tv = (TextView)viewGroup.findViewById(R.id.num_bookmarks_tv);
        num_bookmarks_tv2 = (TextView)viewGroup.findViewById(R.id.num_bookmarks_tv2);
        num_comment_tv = (TextView)viewGroup.findViewById(R.id.num_comment_tv);
        num_comment_tv2 = (TextView)viewGroup.findViewById(R.id.num_comment);
        num_view_tv = (TextView)viewGroup.findViewById(R.id.num_views_tv);
        editor_tv = (TextView)viewGroup.findViewById(R.id.editor_name_tv);
        comment_rv = (RecyclerView)viewGroup.findViewById(R.id.journal_comment_rv);
        recommend_journal_rv = (RecyclerView)viewGroup.findViewById(R.id.recommend_journal_rv);
        comment_et = (EditText)viewGroup.findViewById(R.id.write_comment_et);


        final ArrayList<PerformReviewCommentAdapter.CommentItem> data = new ArrayList<>();
        ArrayList<PerformReviewCommentAdapter.CommentItem> recomment_data = new ArrayList<>();
        recomment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23", "꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", true));
        data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23","꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", recomment_data, false));
        data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23","꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", false));

        final PerformReviewCommentAdapter performReviewCommentAdapter = new PerformReviewCommentAdapter(getActivity(), data);
        comment_rv.setAdapter(performReviewCommentAdapter);
        comment_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        recommend_journal_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ArrayList<JournalRecommendListAdapter.Item> recommend_journal_data = new ArrayList<>();
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img03, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img04, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img05, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img06, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img05, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img06, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_rv.setAdapter(new JournalRecommendListAdapter(recommend_journal_data));

        return viewGroup;
    }
}
