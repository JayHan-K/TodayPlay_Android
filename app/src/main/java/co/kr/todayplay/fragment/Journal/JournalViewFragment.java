package co.kr.todayplay.fragment.Journal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalRecommendListAdapter;
import co.kr.todayplay.fragment.HomeFragment;

public class JournalViewFragment extends Fragment {
    PlayDBHelper playDBHelper;
    TextView hidden_journal_title, hidden_journal_subtitle;
    ImageButton hidden_back_btn;
    WebView webView;
    //RecyclerView recommend_journal_rv;

    int play_id = -1;
    int user_id = -1;

    public JournalViewFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_view, container, false);
        playDBHelper = new PlayDBHelper(this.getContext(), "Play.db",null,1);

        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id);
        }

        hidden_journal_subtitle = (TextView)viewGroup.findViewById(R.id.perform_subtitle_tv);
        hidden_journal_title = (TextView)viewGroup.findViewById(R.id.perform_title_tv);
        hidden_journal_title.setText(playDBHelper.getPlayTitle(play_id));
        hidden_journal_subtitle.setText("");
        hidden_back_btn = (ImageButton) viewGroup.findViewById(R.id.hidden_back_btn);
        hidden_back_btn.setEnabled(true);
        hidden_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("pressed","pressed=");
                HomeFragment homeFragment = new HomeFragment();
                ((MainActivity)view.getContext()).replaceFragmentHome(homeFragment);
            }
        });

        //--WebView Part Start--
        webView = (WebView)viewGroup.findViewById(R.id.journal_webview);
        if(webView == null){
            Log.d("webView", "onCreateView: webView is null");
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.clearCache(true);
        webView.clearHistory();

        String file = playDBHelper.getPlayMain_Journal(play_id);
        Log.d("relation_journal", file);
        String[] file_name = file.split("[.]");
        webView.loadUrl("http://211.174.237.197/media/play_journal/" + file_name[0] + "/index.html");        //--WebView Part End--
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
