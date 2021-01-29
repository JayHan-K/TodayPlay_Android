package co.kr.todayplay.fragment.Journal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalRecommendListAdapter;

public class JournalViewFragment extends Fragment {
    TextView hidden_journal_title, hidden_journal_subtitle;
    ImageButton hidden_back_btn;
    WebView webView;
    //RecyclerView recommend_journal_rv;

    public JournalViewFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_view, container, false);

        hidden_journal_subtitle = (TextView)viewGroup.findViewById(R.id.journal_subtitle_tv);
        hidden_journal_title = (TextView)viewGroup.findViewById(R.id.journal_title_tv);
        hidden_back_btn = (ImageButton) viewGroup.findViewById(R.id.hidden_back_btn);

        webView = (WebView)viewGroup.findViewById(R.id.jouranl_webview);
        if(webView == null){
            Log.d("webView", "onCreateView: webView is null");
        }
        webView.loadUrl("file:///android_asset/journal1.html");
        /*
        recommend_journal_rv = (RecyclerView)viewGroup.findViewById(R.id.recommend_journal_rv);

        recommend_journal_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ArrayList<JournalRecommendListAdapter.Item> recommend_journal_data = new ArrayList<>();
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img03, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img04, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img05, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img06, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img05, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_data.add(new JournalRecommendListAdapter.Item(R.drawable.editor_journal_img06, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        recommend_journal_rv.setAdapter(new JournalRecommendListAdapter(recommend_journal_data));
        */

        return viewGroup;
    }
}
