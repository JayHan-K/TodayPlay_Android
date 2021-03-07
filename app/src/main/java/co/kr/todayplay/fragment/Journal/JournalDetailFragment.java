package co.kr.todayplay.fragment.Journal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.DBHelper.UserDB.UserDBHelper;
import co.kr.todayplay.JoinIdentificationActivityVer2;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalRecommendListAdapter;
import co.kr.todayplay.adapter.PerformReviewCommentAdapter;

public class JournalDetailFragment extends Fragment {
    JournalDBHelper journalDBHelper;
    UserDBHelper userDBHelper;

    AppBarLayout appBarLayout;
    Button more_comment_btn, comment_save_btn;
    ImageButton back_btn, scrap_btn;
    TextView num_of_scrap_tv, num_of_scrap_tv2, num_comment_tv, num_comment_tv2, num_view_tv, editor_tv;
    RecyclerView comment_rv, recommend_journal_rv;
    EditText comment_et;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView journal_banner_iv;
    WebView webView;
    ConstraintLayout relation_journal_container;

    //journal_title, journal_editor, journal_num_of_scrap, journal_comments, journal_num_of_view, journal_file

    int journal_id;
    int user_id = -1;
    String journal_title;
    String[] comments, relation_journal;

    String journalCommentIdResult;
    String[] journalCommentIds;

    int visible_cmt = 3;

    Boolean scrap_flag = false;

    ArrayList<PerformReviewCommentAdapter.CommentItem> all_comment_array = new ArrayList<>();
    ArrayList<PerformReviewCommentAdapter.CommentItem> comment_array = new ArrayList<>();
    PerformReviewCommentAdapter performReviewCommentAdapter;

    public JournalDetailFragment(){}

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_detail, container, false);
        journalDBHelper = new JournalDBHelper(getActivity().getApplicationContext(), "Journal.db", null, 1);
        userDBHelper = new UserDBHelper(getActivity().getApplicationContext(), "User.db", null, 1);

        Bundle bundle = getArguments();
        if(bundle != null){
            journal_id = bundle.getInt("journal_id");
            Log.d("Bundle result", "journal_id: " + journal_id);
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "user_id: " + user_id);
        }

        appBarLayout = (AppBarLayout) viewGroup.findViewById(R.id.app_bar);
        back_btn = (ImageButton) viewGroup.findViewById(R.id.back_btn);

        // -- scrap part Start --
        num_of_scrap_tv = (TextView)viewGroup.findViewById(R.id.num_bookmarks_tv);
        num_of_scrap_tv2 = (TextView)viewGroup.findViewById(R.id.num_bookmarks_tv2);
        loadScrap(Integer.toString(journal_id));
        scrap_flag = userDBHelper.IsScraped(journal_id);
        scrap_btn = (ImageButton) viewGroup.findViewById(R.id.bookmark_btn);
        if(!scrap_flag) scrap_btn.setBackgroundResource(R.drawable.journal_scrap_default);
        else scrap_btn.setBackgroundResource(R.drawable.journal_bookmark_icon_yellow);
        scrap_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!scrap_flag){
                    scrap_btn.setBackgroundResource(R.drawable.journal_bookmark_icon_yellow);
                    scrap_flag = true;
                    // Insert to Server
                    String delete_heart = postSendScrapChange(Integer.toString(user_id), Integer.toString(journal_id), "insert", new VolleyCommentCallback() {
                        @Override
                        public void onSuccess(String data) {
                            //Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                            if (!data.equals("1")) {
                                Log.d("postSendScrapChange", "POST ResultFailed.");

                            } else {
                                Log.d("postSendScrapChange","Success to delete scrap | journal_id = " + journal_id);
                                userDBHelper.add_scrap(user_id, journal_id);
                                loadScrap(Integer.toString(journal_id));
                            }
                        }

                    });

                }
                else{
                    scrap_btn.setBackgroundResource(R.drawable.journal_scrap_default);
                    scrap_flag = false;
                    // Delete to Server
                    String delete_heart = postSendScrapChange(Integer.toString(user_id), Integer.toString(journal_id), "delete", new VolleyCommentCallback() {
                        @Override
                        public void onSuccess(String data) {
                            //Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                            if (!data.equals("1")) {
                                Log.d("postSendScrapChange", "POST ResultFailed.");

                            } else {
                                Log.d("postSendScrapChange","Success to delete scrap | journal_id = " + journal_id);
                                userDBHelper.delete_scrap(journal_id);
                                loadScrap(Integer.toString(journal_id));
                            }
                        }

                    });
                }
            }
        });
        // -- scrap part End --


        more_comment_btn = (Button)viewGroup.findViewById(R.id.more_comment_btn);
        more_comment_btn.setVisibility(View.GONE);
        comment_save_btn = (Button)viewGroup.findViewById(R.id.comment_btn);

        num_comment_tv = (TextView)viewGroup.findViewById(R.id.num_comment_tv);
        num_comment_tv2 = (TextView)viewGroup.findViewById(R.id.num_comment);
        num_view_tv = (TextView)viewGroup.findViewById(R.id.num_views_tv);
        editor_tv = (TextView)viewGroup.findViewById(R.id.editor_name_tv);
        comment_rv = (RecyclerView)viewGroup.findViewById(R.id.journal_comment_rv);
        recommend_journal_rv = (RecyclerView)viewGroup.findViewById(R.id.recommend_journal_rv);
        comment_et = (EditText)viewGroup.findViewById(R.id.write_comment_et);
        journal_banner_iv = (ImageView)viewGroup.findViewById(R.id.journal_poster_iv);
        relation_journal_container = (ConstraintLayout)viewGroup.findViewById(R.id.bottom_part);
        comment_save_btn = (Button)viewGroup.findViewById(R.id.comment_save_btn);

        journal_title = journalDBHelper.getJournalTitle(journal_id);
        editor_tv.setText("by. " + journalDBHelper.getJournalEditor(journal_id));
        Log.d("JournalDetail", "journal_id " + journal_id + ": journal_title = " + journal_title + " , journal_num_of_scrap = " + journalDBHelper.getJournalNum_of_scrap(journal_id) + ", journal_num_of_view = " + journalDBHelper.getJournalNum_of_view(journal_id) + ", journal_comments = " + journalDBHelper.getJournalComments(journal_id) + ", journal_banner_img = " + journalDBHelper.getJournalBanner_img(journal_id) + ", relation_journal = " + journalDBHelper.getJournalRelation_journal(journal_id));
        num_of_scrap_tv.setText(journalDBHelper.getJournalNum_of_scrap(journal_id) + "");
        num_of_scrap_tv2.setText(journalDBHelper.getJournalNum_of_scrap(journal_id) + "");

        // -- num_of_view part Start --
        String send_view_result = postSendViewData(Integer.toString(journal_id), new VolleyCommentCallback() {
            @Override
            public void onSuccess(String data) {
                //Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                if (data.equals("-1")) {
                    Log.d("postSendViewData", "POST ResultFailed.");
                } else {
                    Log.d("postSendViewData","Success to send view | journal_id = " + journal_id);
                }
            }

        });

        String get_num_view = postGetCommentIds(Integer.toString(journal_id), new VolleyCommentCallback() {
            @Override
            public void onSuccess(String data){
                if (data.equals("-1")) {
                    Log.d("postGetNumOfView", "POST ResultFailed.");
                } else {
                    Log.d("postGetNumOfView", "onSuccess: " + data);
                    try {
                        String journal_num_of_view = new JSONObject(data).getJSONObject("journal").getString("journal_num_of_view");
                        Log.d("journal_num_of_view", journal_num_of_view);

                        num_view_tv.setText(journal_num_of_view);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        // -- num_of_view part End --

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

        back_btn.setEnabled(true);
        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("pressed","pressed=");
                ((MainActivity)viewGroup.getContext()).onBackPressed();

            }
        });

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

        //--WebView Part--
        webView = (WebView)viewGroup.findViewById(R.id.journal_wv);
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

        String file = journalDBHelper.getJournalFile(journal_id);
        String[] file_name = file.split("[.]");
        webView.loadUrl("http://211.174.237.197/media/journal/" + file_name[0] + "/index.html");


        //--Comments 로드 Part Start--
        //ArrayList<PerformReviewCommentAdapter.CommentItem> recomment_data = new ArrayList<>();
        //recomment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23", "꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", true));
        //comment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23","꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", recomment_data, false));
        //comment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23","꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", false));
        //final PerformReviewCommentAdapter performReviewCommentAdapter = new PerformReviewCommentAdapter(getActivity(), all_comment_array);
        //comment_rv.setAdapter(performReviewCommentAdapter);
        //comment_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        performReviewCommentAdapter = new PerformReviewCommentAdapter(getActivity(), comment_array);
        comment_rv.setAdapter(performReviewCommentAdapter);
        comment_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        num_comment_tv2.setText("0");
        num_comment_tv.setText("0");

        // Default
        updateComment(Integer.toString(journal_id));

        // 댓글 더보기
        more_comment_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("more_btn", "onClicked");
                String btn_text = (String)((Button)more_comment_btn).getText();
                btn_text = btn_text.replace("+  ", "");
                btn_text = btn_text.substring(0, 1);
                visible_cmt = visible_cmt + Integer.parseInt(btn_text);
                Log.d("more_btn", "btn_text = " + btn_text + " | visible_cmt = " + visible_cmt);

                updateComment(Integer.toString(journal_id));

            }
        });

        // 댓글 저장 Part
        comment_save_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comment_et.getText().toString().equals("")){
                    Toast.makeText(getActivity().getApplicationContext(),"댓글을 작성해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    SimpleDateFormat formatter = new SimpleDateFormat( "yyyy.MM.dd", Locale.KOREA );
                    Date date = Calendar.getInstance().getTime();
                    String now_date = formatter.format (date);
                    String result = postSendCommentData(Integer.toString(user_id), comment_et.getText().toString(), now_date, Integer.toString(journal_id), new VolleyCommentCallback() {
                        @Override
                        public void onSuccess(String data) {
                            //Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                            if (data.equals("1")) {
                                Log.d("postSendCommentData", "POST ResultFailed.");
                            } else {
                                comment_et.setText("");
                                Toast.makeText(getActivity().getApplicationContext(),"댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                updateComment(Integer.toString(journal_id));

                            }
                        }

                    });

                    Log.d("postSendCommentData", result);

                }
            }
        });
        //--Comments 로드 Part Start--


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

    /*
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
    }

    private void loadHtmlPage()
    {
        String htmlString = getHtmlFromAsset();
        if (htmlString != null) webView.loadDataWithBaseURL("file:///android_asset/journal4/", htmlString, "text/html", "UTF-8", null);
        else Toast.makeText(getActivity(), "html이 없습니다", Toast.LENGTH_LONG).show();
    }
    */

    public String postSendViewData(String journal_id, VolleyCommentCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://211.174.237.197/request_increment_journal_num_of_view/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {


                    String data = response;
                    Log.d("postSendViewData", data);
                    resposeData[0] = data;

                    callback.onSuccess(data);


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("postSendViewData", error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("journal_id", journal_id);

                    return params;
                }
            };
            queue.add(stringRequest);
            return resposeData[0];


        } catch (Exception e) {
            Log.d("postSendViewData", e.toString());

        }
        return "1";
    }

    public String postSendScrapData(String journal_id, VolleyCommentCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://211.174.237.197/request_increment_journal_num_of_scrap/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {


                    String data = response;
                    Log.d("postSendScrapData", data);
                    resposeData[0] = data;

                    callback.onSuccess(data);


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("postSendScrapData", error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("journal_id", journal_id);

                    return params;
                }
            };
            queue.add(stringRequest);
            return resposeData[0];


        } catch (Exception e) {
            Log.d("postSendScrapData", e.toString());

        }
        return "1";
    }


    //Comment Save
    public String postSendCommentData(String user_id, String comment, String comment_date, String comment_source_id, VolleyCommentCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://211.174.237.197/request_save_comment/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {


                    String data = response;
                    Log.d("postSendCommentData", data);
                    resposeData[0] = data;

                    callback.onSuccess(data);


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("postSendCommentData", error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("user_id", user_id);
                    params.put("comment_date", comment_date);
                    params.put("comment", comment);
                    params.put("comment_source", "journal");
                    params.put("comment_source_id", comment_source_id);
                    return params;
                }
            };
            queue.add(stringRequest);
            return resposeData[0];


        } catch (Exception e) {
            Log.d("postSendCommentData", e.toString());

        }
        return "0";
    }

    //Get Comment_ids
    public String postGetCommentIds(String journal_id, VolleyCommentCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://211.174.237.197/request_journal_info_by_id/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    String data = response;
                    Log.d("postGetCommentIds", data);
                    callback.onSuccess(data);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("postGetCommentIds", error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("journal_id", journal_id);
                    return params;
                }
            };
            queue.add(stringRequest);
            return resposeData[0];

        } catch (Exception e) {
            Log.d("postGetCommentIds", e.toString());

        }
        return "0";
    }

    //Get Comment
    public String postGetCommentData(String comment_id, VolleyCommentCallback callback){
        try{
            final String[] response_var = {""};
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://211.174.237.197/request_comment_info_by_id/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    String comment_data = response;
                    Log.d("postGetCommentData", comment_data);
                    callback.onSuccess(response);
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("postGetCommentData", error.toString());
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("comment_id", comment_id);
                    return params;
                }
            };
            queue.add(stringRequest);
            return response_var[0];


        }catch(Exception e){
            Log.d("postGetCommentData", e.toString());

        }
        return "0";
    }

    //Get User Data
    public String postGetUserData(String user_id, VolleyCommentCallback callback){
        try{
            final String[] response_var = {""};
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://211.174.237.197/request_user_pic_nickname_by_id/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    String user_id = response;
                    Log.d("postGetUserData", user_id);
                    callback.onSuccess(response);
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("postGetUserData", error.toString());
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("user_id", user_id);
                    return params;
                }
            };
            queue.add(stringRequest);
            return response_var[0];


        }catch(Exception e){
            Log.d("postGetUserData", e.toString());

        }
        return "0";
    }

    public ArrayList<PerformReviewCommentAdapter.CommentItem> commentSort(ArrayList<PerformReviewCommentAdapter.CommentItem> comments){
        ArrayList<PerformReviewCommentAdapter.CommentItem> comment_list = comments;

        Collections.sort(comment_list, new Comparator<PerformReviewCommentAdapter.CommentItem>() {
            @Override
            public int compare(PerformReviewCommentAdapter.CommentItem o1, PerformReviewCommentAdapter.CommentItem o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return comment_list;
    }

    public void updateComment(String journal_id){
        String journal_data = postGetCommentIds(journal_id, new VolleyCommentCallback() {
            @Override
            public void onSuccess(String data) {
                all_comment_array.clear();
                comment_array.clear();
                //Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                if (data.equals("-1")) {
                    Log.d("postGetCommentIds", "POST ResultFailed.");
                } else {
                    Log.d("postGetCommentIds", "onSuccess: " + data);
                    try {
                        //Journal Comment Ids 불러오기
                        journalCommentIdResult = new JSONObject(data).getJSONObject("journal").getString("journal_comments");
                        Log.d("journalIdResultObj", journalCommentIdResult);
                        journalCommentIds = journalCommentIdResult.split(", ");
                        if(!journalCommentIds[0].equals("")) {

                            num_comment_tv.setText(journalCommentIds.length + "");
                            num_comment_tv2.setText(journalCommentIds.length + "");

                            //각각의 Journal Comment 데이터 가져오기
                            for (int i = 0; i < journalCommentIds.length; i++) {
                                Log.d("journalCommentIds", journalCommentIds[i]);
                                String result = postGetCommentData(journalCommentIds[i], new VolleyCommentCallback() {
                                    @Override
                                    public void onSuccess(String comment_data) {
                                        //Toast.makeText(getActivity().getApplicationContext(), "Result: " + comment_data, Toast.LENGTH_SHORT).show();
                                        if (comment_data.equals("-1")) {
                                            Log.d("postGetCommentData", "POST ResultFailed.");

                                        } else {
                                            Log.d("postGetCommentData", "onSuccess: " + comment_data);

                                            try {
                                                //Comment 데이터 parsing
                                                JSONObject comment_object = new JSONObject(comment_data).getJSONObject("comment");
                                                String comment = comment_object.getString("comment");
                                                String user_id = comment_object.getString("user_id");
                                                String comment_date = comment_object.getString("comment_date");
                                                String comment_reply = comment_object.getString("comment_reply");
                                                String user_pic = "";
                                                String user_name = "";
                                                String user_img_path = "";
                                                Log.d("comment_data", comment + " | " + comment_date + " | " + comment_reply + " | " + user_id);

                                                //user data POST
                                                String user_data = postGetUserData(user_id, new VolleyCommentCallback() {
                                                    @Override
                                                    public void onSuccess(String data) {
                                                        if (data.equals("-1")) {
                                                            Log.d("postGetUserData", "POST ResultFailed.");
                                                            all_comment_array.add(new PerformReviewCommentAdapter.CommentItem(user_pic, user_name, comment_date, comment, false));
                                                            all_comment_array = commentSort(all_comment_array);

                                                            Log.d("add all_comment", "Success " + comment + " | num of all_comment_array = " + all_comment_array.size());
                                                            if(comment_array.size() < visible_cmt){
                                                                comment_array.add(new PerformReviewCommentAdapter.CommentItem(user_pic, user_name, comment_date, comment, false));
                                                                Log.d("add comment", "Success " + comment + " | num of comment_array = " + all_comment_array.size());
                                                                comment_array = commentSort(comment_array);
                                                                performReviewCommentAdapter.notifyDataSetChanged();
                                                            }
                                                            if(all_comment_array.size() - comment_array.size() > 3) more_comment_btn.setVisibility(View.VISIBLE);
                                                            else if(all_comment_array.size() - comment_array.size() > 0){
                                                                more_comment_btn.setVisibility(View.VISIBLE);
                                                                more_comment_btn.setText("+  " + (all_comment_array.size() - comment_array.size()) + "개의 댓글 더 보기");
                                                            }
                                                            else more_comment_btn.setVisibility(View.GONE);
                                                        } else {
                                                            try {
                                                                //user 데어터 parsing
                                                                JSONObject user_object = new JSONObject(data).getJSONObject("user");
                                                                String user_pic = user_object.getString("user_pic");
                                                                String user_name = user_object.getString("nickname");
                                                                Log.d("postGetUserData", "onSuccess: " + data);
                                                                String user_img_path = getActivity().getApplicationContext().getFileStreamPath(user_pic).toString();
                                                                Log.d("comment_reply", comment_reply);


                                                                //recomment 존재 여부 체크
                                                                if (comment_reply.equals("")) {
                                                                    //recomment가 없는 comment 객체 추가
                                                                    all_comment_array.add(new PerformReviewCommentAdapter.CommentItem(user_pic, user_name, comment_date, comment, false));
                                                                    all_comment_array = commentSort(all_comment_array);

                                                                    Log.d("add all_comment", "Success " + comment + " | num of all_comment_array = " + all_comment_array.size());
                                                                    if(comment_array.size() < visible_cmt){
                                                                        comment_array.add(new PerformReviewCommentAdapter.CommentItem(user_pic, user_name, comment_date, comment, false));
                                                                        Log.d("add comment", "Success " + comment + " | num of comment_array = " + all_comment_array.size());
                                                                        comment_array = commentSort(comment_array);
                                                                        performReviewCommentAdapter.notifyDataSetChanged();
                                                                    }
                                                                    if(all_comment_array.size() - comment_array.size() > 3) more_comment_btn.setVisibility(View.VISIBLE);
                                                                    else if(all_comment_array.size() - comment_array.size() > 0){
                                                                        more_comment_btn.setVisibility(View.VISIBLE);
                                                                        more_comment_btn.setText("+  " + (all_comment_array.size() - comment_array.size()) + "개의 댓글 더 보기");
                                                                    }
                                                                    else more_comment_btn.setVisibility(View.GONE);
                                                                }
                                                                else {
                                                                    //recomment data post
                                                                    String recomment_result = postGetCommentData(comment_reply, new VolleyCommentCallback() {
                                                                        @Override
                                                                        public void onSuccess(String data) {
                                                                            if (data.equals(""))
                                                                                Log.d("postGetRecommentData", "POST ResultFailed.");
                                                                            else {
                                                                                try {
                                                                                    //recomment data parsing
                                                                                    JSONObject rcm_object = new JSONObject(data).getJSONObject("comment");
                                                                                    String recomment_date = rcm_object.getString("comment_date");
                                                                                    String recomment = rcm_object.getString("comment");
                                                                                    String recomment_user_id = rcm_object.getString("user_id");

                                                                                    //recomment user data post
                                                                                    String rcm_user_data = postGetUserData(recomment_user_id, new VolleyCommentCallback() {
                                                                                        @Override
                                                                                        public void onSuccess(String data) {
                                                                                            if (data.equals(""))
                                                                                                Log.d("postGetRCMUserData", "POST ResultFailed.");
                                                                                            else {
                                                                                                //recomment user data parsing
                                                                                                String recomment_user_pic = "";
                                                                                                String recomment_user_name = "";
                                                                                                Log.d("postGetRCMUserData", "onSuccess: " + data);
                                                                                                String rcm_user_img_path = getActivity().getApplicationContext().getFileStreamPath(recomment_user_pic).toString();

                                                                                                ArrayList<PerformReviewCommentAdapter.CommentItem> recomment_data = new ArrayList<>();
                                                                                                recomment_data.add(new PerformReviewCommentAdapter.CommentItem(recomment_user_pic, recomment_user_name, recomment_date, recomment, true));
                                                                                                //recomment를 가진 comment 객체 추가
                                                                                                all_comment_array.add(new PerformReviewCommentAdapter.CommentItem(user_pic, user_name, comment_date, comment, recomment_data, false));
                                                                                                all_comment_array = commentSort(all_comment_array);

                                                                                                if(comment_array.size() < visible_cmt){
                                                                                                    comment_array.add(new PerformReviewCommentAdapter.CommentItem(user_pic, user_name, comment_date, comment, recomment_data, false));
                                                                                                    Log.d("add comment", "Success " + comment + " | num of comment_array = " + comment_array.size());
                                                                                                    comment_array = commentSort(comment_array);

                                                                                                    performReviewCommentAdapter.notifyDataSetChanged();
                                                                                                }
                                                                                                if(all_comment_array.size() - comment_array.size() > 3) more_comment_btn.setVisibility(View.VISIBLE);
                                                                                                else if(all_comment_array.size() - comment_array.size() > 0){
                                                                                                    more_comment_btn.setVisibility(View.VISIBLE);
                                                                                                    more_comment_btn.setText("+  " + (all_comment_array.size() - comment_array.size()) + "개의 댓글 더 보기");
                                                                                                }
                                                                                                else more_comment_btn.setVisibility(View.GONE);
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                } catch (JSONException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                    }
                                                });


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    }

                                });

                                Log.d("postSendCommentData", result);
                            }
                        }
                        Log.d("Comments","Finish adding comments data | num = " + all_comment_array.size());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void loadScrap(String journal_id){
        String get_scrap_view = postGetCommentIds(journal_id, new VolleyCommentCallback() {
            @Override
            public void onSuccess(String data){
                if (data.equals("-1")) {
                    //Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                    Log.d("postGetNumOfScrap", "POST ResultFailed.");
                } else {
                    Log.d("postGetNumOfScrap", "onSuccess: " + data);
                    try {
                        String journal_num_of_scrap = new JSONObject(data).getJSONObject("journal").getString("journal_num_of_scrap");
                        Log.d("journal_num_of_scrap", journal_num_of_scrap);

                        num_of_scrap_tv.setText(journal_num_of_scrap);
                        num_of_scrap_tv2.setText(journal_num_of_scrap);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public String postSendScrapChange(String user_id, String journal_id, String action, VolleyCommentCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://211.174.237.197/request_increment_journal_num_of_scrap/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {


                    String data = response;
                    Log.d("postSendScrapChange", data);
                    resposeData[0] = data;

                    callback.onSuccess(data);


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("postSendScrapChange", error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("action", action);
                    params.put("user_id", user_id);
                    params.put("journal_id", journal_id);

                    return params;
                }
            };
            queue.add(stringRequest);
            return resposeData[0];


        } catch (Exception e) {
            Log.d("postSendScrapChange", e.toString());

        }
        return "1";
    }



}




interface VolleyCommentCallback{
    void onSuccess(String data);
}