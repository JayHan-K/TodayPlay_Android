package co.kr.todayplay.fragment.Journal;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
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
import co.kr.todayplay.AppHelper;
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.DBHelper.UserDB.UserDBHelper;
import co.kr.todayplay.JoinIdentificationActivityVer2;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalRecommendListAdapter;
import co.kr.todayplay.adapter.PerformDetailJournalAdapter;
import co.kr.todayplay.adapter.PerformReviewCommentAdapter;

public class JournalDetailFragment extends Fragment {
    public JournalDetailFragment(){}

    int journal_id = -1;
    int user_id = -1;
    //수정 유저 닉네임, 프로필 메인엑티비티에서 가져오기
    String user_pic = "";
    String user_name = "Tester";
    String journal_title;
    String[] relation_journal;
    Boolean scrap_flag = false;

    JournalDBHelper journalDBHelper;
    UserDBHelper userDBHelper;
    AppBarLayout appBarLayout;

    //layout
    ImageButton back_btn, scrap_btn;
    TextView num_of_scrap_tv, num_of_scrap_tv2, num_comment_tv, num_comment_tv2, num_view_tv, editor_tv;
    RecyclerView comment_rv, recommend_journal_rv;
    EditText comment_et;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView journal_banner_iv, journal_content_iv;
    WebView webView;
    ConstraintLayout relation_journal_container;
    Button more_comment_btn, comment_save_btn;
    InputMethodManager inputMethodManager;

    //댓글 관련 변수
    ArrayList<PerformReviewCommentAdapter.CommentItem> all_comment_data = new ArrayList<>();
    PerformReviewCommentAdapter commentAdapter;
    String[] journalCommentIds;
    int all_cmt = 0;

    //APIs
    //sendPOSTJournal_idRequestForViews -> 저널 조회수 업데이트
    String journal_view_request_url = "http://211.174.237.197/request_increment_journal_num_of_view/";

    //sendPOSTScrapUpdateRequest -> 스크랩 버튼 클릭 시 상태 업데이트
    String scrap_update_request_url = "http://211.174.237.197/request_increment_journal_num_of_scrap/";

    //journal_id = n인 저널에 해당하는 모든 데이터를 JSON 형식으로 응답하는 API -> 여기서 해당 저널(해당 프로젝트에서 임의 저널 아이디 = 1)에 남겨진 모든 댓글 아이디들을 JSON Parsing하여 받아올 수 있다.(comments)
    //sendPOSTJournal_idRequestForComment
    String journal_id_request_url = "http://211.174.237.197/request_journal_info_by_id/";

    //comment_id = n인 댓글 데이터를 응답하는 API -> user_id(작성자 아이디), comment(댓글 내용), date(댓글 작성 시각)를 JSON Parsing하여 받아올 수 있다.
    //sendPOSTComment_idRequest 함수의 매개변수
    String comment_id_request_url = "http://211.174.237.197/request_comment_info_by_id/";

    //user_id = n인 유저 정보 데이터를 응답하는 API -> user_nickname(유저 닉네임, 해당 프로젝트에서 임의 사용자 닉네임 = "화요밍")을 JSON Parsing하여 받아올 수 있다.
    //sendPOSTComment_idRequest 함수의 매개변수
    String user_id_request_url = "http://211.174.237.197/request_user_pic_nickname_by_id/";

    //새로운 댓글 데이터를 전송하는 API -> user_id(유저 아이디, 해당 프로젝트에서 임의 사용자 아이디 = 6), comment(댓글 내용), date(현재 시각)을 JSON 형식으로 파라미터에 담아 요청한다.
    //sendPOSTCommentRequest 함수의 매개변수
    String send_comment_url = "http://211.174.237.197/request_save_comment/";

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_detail, container, false);
        journalDBHelper = new JournalDBHelper(getActivity().getApplicationContext(), "Journal.db", null, 1);
        userDBHelper = new UserDBHelper(getActivity().getApplicationContext(), "User.db", null, 1);
        Log.d("JournalDetail", "journal_id " + journal_id + ": journal_title = " + journal_title + " , journal_num_of_scrap = " + journalDBHelper.getJournalNum_of_scrap(journal_id) + ", journal_num_of_view = " + journalDBHelper.getJournalNum_of_view(journal_id) + ", journal_comments = " + journalDBHelper.getJournalComments(journal_id) + ", journal_banner_img = " + journalDBHelper.getJournalBanner_img(journal_id) + ", relation_journal = " + journalDBHelper.getJournalRelation_journal(journal_id));

        //수정 유저 정보
        //이전 fragment의 데이터 가져오기 JOURNAL_ID, USER_ID
        Bundle bundle = getArguments();
        if(bundle != null){
            journal_id = bundle.getInt("journal_id");
            Log.d("Bundle result", "journal_id: " + journal_id);
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "user_id: " + user_id);
        }
        //메인 Activity가 메모리에서 만들어질 때, RequestQueue를 하나만 생성한다.
        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        //layout binding
        num_of_scrap_tv = (TextView)viewGroup.findViewById(R.id.num_bookmarks_tv);
        num_of_scrap_tv2 = (TextView)viewGroup.findViewById(R.id.num_bookmarks_tv2);
        appBarLayout = (AppBarLayout) viewGroup.findViewById(R.id.app_bar);
        back_btn = (ImageButton) viewGroup.findViewById(R.id.back_btn);
        scrap_btn = (ImageButton) viewGroup.findViewById(R.id.bookmark_btn);
        inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        more_comment_btn = (Button)viewGroup.findViewById(R.id.more_comment_btn);
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


        //Set hidden 저널 title
        journal_title = journalDBHelper.getJournalTitle(journal_id);
        //Set 저널 에디터
        editor_tv.setText("by. " + journalDBHelper.getJournalEditor(journal_id));
        //Set 저널 스크랩
        num_of_scrap_tv.setText(journalDBHelper.getJournalNum_of_scrap(journal_id) + "");
        num_of_scrap_tv2.setText(journalDBHelper.getJournalNum_of_scrap(journal_id) + "");
        //Set 저널 포스터
        String banner_img_path = getActivity().getApplicationContext().getFileStreamPath(journalDBHelper.getJournalBanner_img(journal_id)).toString();
        Bitmap bm = BitmapFactory.decodeFile(banner_img_path);
        journal_banner_iv.setImageBitmap(bm);

        //Set collapsing 툴바
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

        //조회수, 스크랩
        num_view_tv.setText("0");
        num_of_scrap_tv.setText("0");
        num_of_scrap_tv2.setText("0");

        //1. 조회수 update to Server
        sendPOSTJournal_idRequestForViews(journal_view_request_url, Integer.toString(journal_id));

        //2. Set 조회수, 스크랩
        sendPOSTJournal_idRequestForScrapViews(journal_id_request_url, Integer.toString(journal_id));

        //3. 저널 스크랩 버튼
        scrap_flag = userDBHelper.IsScraped(journal_id);
        if(!scrap_flag) scrap_btn.setBackgroundResource(R.drawable.journal_scrap_default);
        else scrap_btn.setBackgroundResource(R.drawable.journal_bookmark_icon_yellow);
        scrap_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!scrap_flag){
                    scrap_btn.setBackgroundResource(R.drawable.journal_bookmark_icon_yellow);
                    scrap_flag = true;
                    sendPOSTScrapUpdateRequest(scrap_update_request_url, Integer.toString(journal_id), Integer.toString(user_id), "insert");
                }
                else{
                    scrap_btn.setBackgroundResource(R.drawable.journal_scrap_default);
                    scrap_flag = false;
                    sendPOSTScrapUpdateRequest(scrap_update_request_url, Integer.toString(journal_id), Integer.toString(user_id), "delete");
                }
            }
        });

        /*
        //--WebView Part Start--
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
        //--WebView Part Start--
        */
        //--Journal 본문 Part Start--
        journal_content_iv = (ImageView)viewGroup.findViewById(R.id.journal_iv);
        journal_content_iv.setImageResource(R.drawable.journal_sample2);

        //연관 저널
        String relation_journal_string = journalDBHelper.getJournalRelation_journal(journal_id);
        if(relation_journal_string == null){
            Log.d("RelationJournalString", "null");
            relation_journal_container.setVisibility(View.GONE);
        }
        else{
            relation_journal = relation_journal_string.split(", ");
            for(int i=0; i<relation_journal.length; i++){
                Log.d("Relation_journal", relation_journal[i] + ", ");
            }
        }
        recommend_journal_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ArrayList<JournalRecommendListAdapter.Item> recommend_journal_data = new ArrayList<>();
        String relation_journals = journalDBHelper.getJournalRelation_journal(journal_id);
        Log.d("relation_journals", relation_journals);
        if (!relation_journals.equals("")){
            String[] relative_journals = relation_journals.split(",");
            for(int i=0; i<relative_journals.length; i++){
                Log.d("relative_journals", i + " = " + relative_journals[i]);
                String[] relative_id = relative_journals[i].split("[.]");
                int new_relative_journal = Integer.parseInt(relative_id[0]);
                recommend_journal_data.add(new JournalRecommendListAdapter.Item(new_relative_journal, getActivity().getApplicationContext().getFileStreamPath(journalDBHelper.getJournalThumbnail2_img(new_relative_journal)).toString(), journalDBHelper.getJournalSubtitle(new_relative_journal),journalDBHelper.getJournalTitle(new_relative_journal)));
            }
        }
        recommend_journal_rv.setAdapter(new JournalRecommendListAdapter(recommend_journal_data));

        //댓글
        commentAdapter = new PerformReviewCommentAdapter(getActivity(), all_comment_data);
        comment_rv.setAdapter(commentAdapter);
        comment_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //댓글수, 댓글 더보기 버튼 초기화
        num_comment_tv2.setText("0");
        num_comment_tv.setText("0");
        more_comment_btn.setVisibility(View.GONE);

        //1. 댓글 데이터를 서버에 요청하여 로드하고 RecyclerView를 갱신한다.
        sendPOSTJournal_idRequestForComment(journal_id_request_url, Integer.toString(journal_id));

        //2. 댓글 더보기 버튼 클릭하면 visible_cmt(RecyclerView에 출력되는 아이템 개수)를 증가시켜 RecyclerView를 갱신한다.
        more_comment_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn_text = (String)((Button)more_comment_btn).getText();
                btn_text = btn_text.replace("+  ", "");
                btn_text = btn_text.substring(0, 1);
                int visible_cmt = Integer.parseInt(btn_text) + commentAdapter.getVisible_cmt();
                commentAdapter.setVisible_cmt(visible_cmt);
                commentAdapter.notifyDataSetChanged();

                updateMore_btn();
            }
        });

        //3. 새로운 댓글이 등록되면 서버에 데이터를 전송하고 RecyclerView를 갱신한다.
        comment_save_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment_et.getText().toString().equals("")) Toast.makeText(getActivity().getApplicationContext(), "댓글을 작성해주세요.", Toast.LENGTH_LONG).show();
                else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);
                    Date date = Calendar.getInstance().getTime();
                    String now_date = format.format(date);

                    sendPOSTCommentRequest(send_comment_url, Integer.toString(user_id), comment_et.getText().toString(), now_date);
                }
            }
        });

        //뒤로가기 버튼
        back_btn.setEnabled(true);
        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("pressed","pressed=");
                ((MainActivity)viewGroup.getContext()).onBackPressed();

            }
        });

        return viewGroup;
    }

    public void sendPOSTScrapUpdateRequest(String url, String journal_id, String user_id, String action){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("1")){
                            Log.d("ScrapUpdateRequest", "Response Failed");
                        }
                        else{
                            int num_scrap = Integer.parseInt(num_of_scrap_tv.getText().toString());
                            Log.d("ScrapUpdateRequest","Success to " + action + " scrap = " + num_scrap + " journal_id = " + journal_id);

                            if(action == "insert") {
                                userDBHelper.add_scrap(Integer.parseInt(user_id), Integer.parseInt(journal_id));
                                num_of_scrap_tv2.setText(Integer.toString(num_scrap+1));
                                num_of_scrap_tv.setText(Integer.toString(num_scrap+1));
                            }
                            else if(action == "delete"){
                                userDBHelper.delete_scrap(Integer.parseInt(journal_id));
                                num_of_scrap_tv2.setText(Integer.toString(num_scrap-1));
                                num_of_scrap_tv.setText(Integer.toString(num_scrap-1));
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ScrapUpdateRequest", "Error = " + error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("journal_id", journal_id);
                params.put("user_id", user_id);
                params.put("action", action);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    public void sendPOSTJournal_idRequestForScrapViews(String url, String journal_id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RequestForScrapViews", "response = " + response);
                        try{
                            int num_of_scrap = new JSONObject(response).getJSONObject("journal").getInt("journal_num_of_scrap");
                            int num_of_views = new JSONObject(response).getJSONObject("journal").getInt("journal_num_of_view");
                            num_of_scrap_tv.setText(Integer.toString(num_of_scrap));
                            num_of_scrap_tv2.setText(Integer.toString(num_of_scrap));
                            num_view_tv.setText(Integer.toString(num_of_views));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("journal_id", journal_id);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    public void sendPOSTJournal_idRequestForViews(String url, String journal_id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("-1")) Log.d("RequestForViews", "Response Success");
                        else Log.d("RequestForViews", "Response Failed");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RequestForViews", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("journal_id", journal_id);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    public void sendPOSTJournal_idRequestForComment(String url, String journal_id){
        all_comment_data.clear();
        all_cmt = 0;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String all_journal_data_response) {
                        Log.d("Journal_idRequest", "all_journal_data_result = " + all_journal_data_response);
                        String journalCommentIdResult = "";
                        try {
                            journalCommentIdResult = new JSONObject(all_journal_data_response).getJSONObject("journal").getString("journal_comments");
                            Log.d("Journal_idRequest", "journalCommentIdResult = " + journalCommentIdResult);
                            journalCommentIds = journalCommentIdResult.split(", ");
                            if(!journalCommentIds[0].equals("")){
                                for(int i=0; i<journalCommentIds.length; i++){
                                    Log.d("Journal_idRequest", "journalCommentIds " + i + " = " + journalCommentIds[i]);
                                    sendPOSTComment_idRequest(comment_id_request_url, journalCommentIds[i]);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CommentRequest", "Error = " + error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("journal_id", journal_id);
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    public void sendPOSTComment_idRequest(String url, String comment_id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String all_comment_data_response) {
                        if(!all_comment_data_response.equals("-1")){
                            all_cmt++;
                            Log.d("Comment_idRequest", all_comment_data_response);
                            try{
                                JSONObject comment_object = new JSONObject(all_comment_data_response).getJSONObject("comment");
                                String comment = comment_object.getString("comment");
                                String user_id = comment_object.getString("user_id");
                                String comment_date = comment_object.getString("comment_date");
                                String reply = comment_object.getString("comment_reply");
                                Log.d("Comment_idRequest", "comment_id = " + comment_id + " comment = " + comment + " comment_date =  " + comment_date  + " user_id = " + user_id + "comment_reply = " + reply);
                                sendPOSTUser_idRequest(user_id_request_url, user_id, comment, comment_date, reply);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Comment_idRequest", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("comment_id", comment_id);
                return params;
            }
        };
        Log.d("Comment_idRequest", "comment_id = " + comment_id);
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    public void sendPOSTUser_idRequest(String url, String user_id, String comment, String comment_date, String reply){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("-1")){
                            Log.d("User_idRequest", "user_id = " + user_id + "response = " + response);
                            try{
                                JSONObject jsonObject = new JSONObject(response).getJSONObject("user");
                                String user_name = jsonObject.getString("nickname");
                                String user_pic = jsonObject.getString("user_pic");
                                String user_img_path = "";
                                //리댓
                                boolean isRecomment = false;
                                if(!reply.equals("")){
                                    isRecomment = true;
                                }
                                Log.d("User_idRequest", "user_id = " + user_id + " user_name = " + user_name );
                                all_comment_data.add(new PerformReviewCommentAdapter.CommentItem(user_pic, user_name, comment_date, comment, isRecomment));
                                Log.d("User_idRequest", "all_comment_data = " + all_comment_data.size());
                                Log.d("User_idRequest", "journal_comments_id length = " + journalCommentIds.length);
                                if(all_cmt == all_comment_data.size()){
                                    Log.d("User_idRequest", "Done to get All comments data !! | all_cmt = " + all_cmt + " all_comment_data = " + all_comment_data.size());
                                    if(all_cmt >= 3) commentAdapter.setVisible_cmt(3);
                                    else if(all_cmt > 0) commentAdapter.setVisible_cmt(all_cmt);
                                    else commentAdapter.setVisible_cmt(0);
                                    all_comment_data = commentSort(all_comment_data);
                                    num_comment_tv.setText(Integer.toString(all_cmt));
                                    num_comment_tv2.setText(Integer.toString(all_cmt));
                                    commentAdapter.notifyDataSetChanged();
                                    updateMore_btn();
                                }

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("User_idRequest", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("user_id", user_id);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    public void sendPOSTCommentRequest(String url, String user_id, String comment, String comment_date){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CommentRequest", "response = " + response);
                        if(!response.equals("1")){
                            Toast.makeText(getActivity().getApplicationContext(), "댓글이 등록되었습니다.", Toast.LENGTH_LONG).show();
                            comment_et.setText("");
                            inputMethodManager.hideSoftInputFromWindow(comment_et.getWindowToken(), 0);
                            all_comment_data.add(new PerformReviewCommentAdapter.CommentItem(user_pic, user_name, comment_date, comment, false));
                            all_comment_data = commentSort(all_comment_data);
                            commentAdapter.notifyDataSetChanged();
                            num_comment_tv.setText(Integer.toString(all_comment_data.size()));
                            num_comment_tv2.setText(Integer.toString(all_comment_data.size()));
                            updateMore_btn();
                        }
                        else Log.d("CommentRequest", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CommentRequest", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("user_id", user_id);
                params.put("comment_date", comment_date);
                params.put("comment", comment);
                params.put("comment_source", "journal");
                params.put("comment_source_id", Integer.toString(journal_id));
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    //최신 순으로 댓글을 정렬하는 함수
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

    //더보기 가능한 댓글 수를 파악하여 댓글 더보기 버튼을 업데이트하는 함수
    public void updateMore_btn(){
        int remain_cmt = commentAdapter.getDataSize() - commentAdapter.getVisible_cmt();
        if(remain_cmt >= 3){
            more_comment_btn.setText("+  3개의 댓글 더보기");
            more_comment_btn.setVisibility(View.VISIBLE);
        }
        else if(remain_cmt > 0){
            more_comment_btn.setText("+  " + remain_cmt + "개의 댓글 더보기");
            more_comment_btn.setVisibility(View.VISIBLE);
        }
        else more_comment_btn.setVisibility(View.GONE);
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
}