package co.kr.todayplay.fragment.Journal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalRecommendListAdapter;
import co.kr.todayplay.adapter.PerformReviewCommentAdapter;

public class JournalDetailFragment extends Fragment {
    AppBarLayout appBarLayout;
    Button more_comment_btn, comment_save_btn;
    ImageButton back_btn, bookmark_btn;
    TextView num_bookmarks_tv, num_bookmarks_tv2, num_comment_tv, num_comment_tv2, num_view_tv, editor_tv;
    RecyclerView comment_rv, recommend_journal_rv;
    EditText comment_et;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView journal_banner_iv;
    WebView webView;
    ConstraintLayout relation_journal_container;

    //journal_title, journal_editor, journal_num_of_scrap, journal_comments, journal_num_of_view, journal_file
    JournalDBHelper journalDBHelper;
    int journal_id;
    String journal_title;
    String[] comments, relation_journal;

    public JournalDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_detail, container, false);
        journalDBHelper = new JournalDBHelper(getActivity().getApplicationContext(), "Journal.db", null, 1);
        appBarLayout = (AppBarLayout) viewGroup.findViewById(R.id.app_bar);
        back_btn = (ImageButton) viewGroup.findViewById(R.id.back_btn);
        bookmark_btn = (ImageButton) viewGroup.findViewById(R.id.bookmark_btn);
        more_comment_btn = (Button)viewGroup.findViewById(R.id.more_comment_btn);
        comment_save_btn = (Button)viewGroup.findViewById(R.id.comment_btn);
        num_bookmarks_tv = (TextView)viewGroup.findViewById(R.id.num_bookmarks_tv);
        num_bookmarks_tv2 = (TextView)viewGroup.findViewById(R.id.num_bookmarks_tv2);
        num_comment_tv = (TextView)viewGroup.findViewById(R.id.num_comment_tv);
        num_comment_tv2 = (TextView)viewGroup.findViewById(R.id.num_comment);
        num_view_tv = (TextView)viewGroup.findViewById(R.id.num_views_tv);
        editor_tv = (TextView)viewGroup.findViewById(R.id.editor_name_tv);
        comment_rv = (RecyclerView)viewGroup.findViewById(R.id.journal_comment_rv);
        recommend_journal_rv = (RecyclerView)viewGroup.findViewById(R.id.recommend_journal_rv);
        comment_et = (EditText)viewGroup.findViewById(R.id.write_comment_et);
        journal_banner_iv = (ImageView)viewGroup.findViewById(R.id.journal_poster_iv);
        relation_journal_container = (ConstraintLayout)viewGroup.findViewById(R.id.bottom_part);

        Bundle bundle = getArguments();
        if(bundle != null){
            journal_id = bundle.getInt("journal_id");
            Log.d("Bundle result", "journal_id: " + journal_id);
        }
        //journal_comments, relation_journal
        journal_title = journalDBHelper.getJournalTitle(journal_id);
        editor_tv.setText("by. " + journalDBHelper.getJournalEditor(journal_id));
        Log.d("JournalDetail", "journal_id " + journal_id + ": journal_title = " + journal_title + " , journal_num_of_scrap = " + journalDBHelper.getJournalNum_of_scrap(journal_id) + ", journal_num_of_view = " + journalDBHelper.getJournalNum_of_view(journal_id) + ", journal_comments = " + journalDBHelper.getJournalComments(journal_id) + ", journal_banner_img = " + journalDBHelper.getJournalBanner_img(journal_id) + ", relation_journal = " + journalDBHelper.getJournalRelation_journal(journal_id));
        num_bookmarks_tv.setText(journalDBHelper.getJournalNum_of_scrap(journal_id) + "");
        num_bookmarks_tv2.setText(journalDBHelper.getJournalNum_of_scrap(journal_id) + "");
        num_view_tv.setText(journalDBHelper.getJournalNum_of_view(journal_id) + "");
        String banner_img_path = getActivity().getApplicationContext().getFileStreamPath(journalDBHelper.getJournalBanner_img(journal_id)).toString();
        Bitmap bm = BitmapFactory.decodeFile(banner_img_path);
        journal_banner_iv.setImageBitmap(bm);
        String relation_jouranl_string = journalDBHelper.getJournalRelation_journal(journal_id);
        if(relation_jouranl_string == null){
            Log.d("RelationJournalString", "null");
            relation_journal_container.setVisibility(View.GONE);
        }
        else{
            relation_journal = relation_jouranl_string.split(", ");
            for(int i=0; i<relation_journal.length; i++){
                Log.d("Relation_journal", relation_journal[i] + ", ");
            }
        }
        String comments_string = journalDBHelper.getJournalComments(journal_id);
        if(comments_string != null){
            comments = comments_string.split(", ");
            for(int i=0; i<relation_journal.length; i++){
                Log.d("journal_comments", comments[i] + ", ");
            }
            if (comments_string.length() <= 2){
                more_comment_btn.setVisibility(View.GONE);
            }
        }

        collapsingToolbarLayout = (CollapsingToolbarLayout)viewGroup.findViewById(R.id.toolbar_layout);
        appBarLayout = (AppBarLayout)viewGroup.findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(journal_title);
                    isShow = true;
                }
                else if(isShow){
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        webView = (WebView)viewGroup.findViewById(R.id.journal_wv);
        if(webView == null){
            Log.d("webView", "onCreateView: webView is null");
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        String file = journalDBHelper.getJournalFile(journal_id);
        String[] file_name = file.split("[.]");
        webView.loadUrl("http://183.111.253.75/media/journal/" + file_name[0] + "/index.html");

        //Comments part
        final ArrayList<PerformReviewCommentAdapter.CommentItem> comment_data = new ArrayList<>();
        ArrayList<PerformReviewCommentAdapter.CommentItem> recomment_data = new ArrayList<>();
        //recomment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23", "꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", true));
        //comment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23","꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", recomment_data, false));
        //comment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23","꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", false));
        final PerformReviewCommentAdapter performReviewCommentAdapter = new PerformReviewCommentAdapter(getActivity(), comment_data);
        comment_rv.setAdapter(performReviewCommentAdapter);
        comment_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        //Relation_jouranl part
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

    /** * Gets html content from the assets folder. */
    private String getHtmlFromAsset() {
        InputStream is; StringBuilder builder = new StringBuilder();
        String htmlString = null;
        try {
            is = getActivity().getAssets().open("index.xhtml");
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                htmlString = builder.toString();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return htmlString;
    } /** * Loads html page with the content. */

    private void loadHtmlPage()
    {
        String htmlString = getHtmlFromAsset();
        if (htmlString != null) webView.loadDataWithBaseURL("file:///android_asset/journal4/", htmlString, "text/html", "UTF-8", null);
        else Toast.makeText(getActivity(), "html이 없습니다", Toast.LENGTH_LONG).show();
    }

}