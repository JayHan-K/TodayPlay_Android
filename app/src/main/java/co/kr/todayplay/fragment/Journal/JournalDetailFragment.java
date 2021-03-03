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
import co.kr.todayplay.JoinIdentificationActivityVer2;
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
    int user_id = -1;
    String journal_title;
    String[] comments, relation_journal;

    String journalCommentIdResult;
    String[] journalCommentIds;

    public JournalDetailFragment(){}

    @SuppressLint("SetJavaScriptEnabled")
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
        comment_save_btn = (Button)viewGroup.findViewById(R.id.comment_save_btn);
        Bundle bundle = getArguments();
        if(bundle != null){
            journal_id = bundle.getInt("journal_id");
            Log.d("Bundle result", "journal_id: " + journal_id);
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "user_id: " + user_id);
        }
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
        webView.loadUrl("http://183.111.253.75/media/journal/" + file_name[0] + "/index.html");

        //--Comments 로드 Part--
        final ArrayList<PerformReviewCommentAdapter.CommentItem> comment_arraylist = new ArrayList<>();
        //ArrayList<PerformReviewCommentAdapter.CommentItem> recomment_data = new ArrayList<>();
        //recomment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23", "꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", true));
        //comment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23","꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", recomment_data, false));
        //comment_data.add(new PerformReviewCommentAdapter.CommentItem(R.drawable.icon_mypage, "제인", "2020.10.23","꿀팁 공유 감사합니다!\n2층에서는 샹들리에 떨어지는게 잘 안보이나요?", false));
        //final PerformReviewCommentAdapter performReviewCommentAdapter = new PerformReviewCommentAdapter(getActivity(), comment_arraylist);
        //comment_rv.setAdapter(performReviewCommentAdapter);
        //comment_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));


        String journal_data = postGetCommentIds(Integer.toString(journal_id), new VolleyCommentCallback() {
                    @Override
                    public void onSuccess(String data) {
                        Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                        if (data.equals("")) {
                            Log.d("postGetCommentIds", "POST ResultFailed.");
                        } else {
                            Log.d("postGetCommentIds", "onSuccess: " + data);
                            try {
                                journalCommentIdResult = new JSONObject(data).getJSONObject("journal").getString("journal_comments");
                                Log.d("journalIdResultObj", journalCommentIdResult);
                                journalCommentIds = journalCommentIdResult.split(", ");
                                num_comment_tv.setText(journalCommentIds.length + "");
                                num_comment_tv2.setText(journalCommentIds.length + "");
                                for(int i=0; i<journalCommentIds.length; i++){
                                    Log.d("journalCommentIds", journalCommentIds[i]);
                                    String result = postGetCommentData(journalCommentIds[i], new VolleyCommentCallback() {
                                        @Override
                                        public void onSuccess(String comment_data) {
                                            Toast.makeText(getActivity().getApplicationContext(), "Result: " + comment_data, Toast.LENGTH_SHORT).show();
                                            if (comment_data.equals("")) {
                                                Log.d("postGetCommentData", "POST ResultFailed.");

                                            } else {
                                                Log.d("postGetCommentData", "onSuccess: " +  comment_data);

                                                try {
                                                    JSONObject comment_object = new JSONObject(comment_data).getJSONObject("comment");
                                                    String comment = comment_object.getString("comment");
                                                    String user_id = comment_object.getString("user_id");
                                                    String comment_date = comment_object.getString("comment_date");
                                                    String comment_reply = comment_object.getString("comment_reply");
                                                    Log.d("comment_data", comment + " | " + comment_date + " | " + comment_reply + " | " + user_id);
                                                    //user data POST
                                                    String user_data = postGetUserData(user_id, new VolleyCommentCallback() {
                                                        @Override
                                                        public void onSuccess(String data) {
                                                            if(data.equals("-1")) Log.d("postGetUserData", "POST ResultFailed.");
                                                            else{
                                                                try {
                                                                    JSONObject user_object = new JSONObject(data).getJSONObject("user");
                                                                    String user_pic = user_object.getString("user_pic");
                                                                    String user_name = user_object.getString("nickname");
                                                                    Log.d("postGetUserData", "onSuccess: " +  data);
                                                                    String user_img_path = getActivity().getApplicationContext().getFileStreamPath(user_pic).toString();
                                                                    Log.d("comment_reply", comment_reply);


                                                                    //여기부터~~~~


                                                                    if(comment_reply.equals("")){
                                                                        comment_arraylist.add(new PerformReviewCommentAdapter.CommentItem(user_pic, user_name, comment_date, comment, false));
                                                                        Log.d("add comment", "Success " + comment);
                                                                    }
                                                                    else{
                                                                        //recomment data post
                                                                        String recomment_result = postGetCommentData(comment_reply, new VolleyCommentCallback() {
                                                                            @Override
                                                                            public void onSuccess(String data) {
                                                                                if(data.equals("")) Log.d("postGetRecommentData", "POST ResultFailed.");
                                                                                else{
                                                                                    try {
                                                                                        JSONObject rcm_object = new JSONObject(data).getJSONObject("comment");
                                                                                        String recomment_date = rcm_object.getString("comment_date");
                                                                                        String recomment = rcm_object.getString("comment");
                                                                                        String recomment_user_id = rcm_object.getString("user_id");

                                                                                        //recomment user data post
                                                                                        String rcm_user_data = postGetUserData(recomment_user_id, new VolleyCommentCallback() {
                                                                                            @Override
                                                                                            public void onSuccess(String data) {
                                                                                                if(data.equals("")) Log.d("postGetRCMUserData", "POST ResultFailed.");
                                                                                                else {
                                                                                                    String recomment_user_pic = "";
                                                                                                    String recomment_user_name = "";
                                                                                                    Log.d("postGetRCMUserData", "onSuccess: " +  data);
                                                                                                    String rcm_user_img_path = getActivity().getApplicationContext().getFileStreamPath(recomment_user_pic).toString();

                                                                                                    ArrayList<PerformReviewCommentAdapter.CommentItem> recomment_data = new ArrayList<>();
                                                                                                    recomment_data.add(new PerformReviewCommentAdapter.CommentItem(recomment_user_pic, recomment_user_name, recomment_date, recomment, true));
                                                                                                    comment_arraylist.add(new PerformReviewCommentAdapter.CommentItem(user_pic, user_name, comment_date,comment, recomment_data, false));

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
                                final PerformReviewCommentAdapter performReviewCommentAdapter = new PerformReviewCommentAdapter(getActivity(), comment_arraylist);
                                comment_rv.setAdapter(performReviewCommentAdapter);
                                comment_rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
        });



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

        //--댓글 저장 Part--
        comment_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comment_et.getText().equals("")){
                    Toast.makeText(getActivity().getApplicationContext(),"댓글을 작성해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    SimpleDateFormat formatter = new SimpleDateFormat( "yyyy.MM.dd", Locale.KOREA );
                    Date date = Calendar.getInstance().getTime();
                    String now_date = formatter.format (date);
                    String result = postSendCommentData(Integer.toString(user_id), comment_et.getText().toString(), now_date, Integer.toString(journal_id), new VolleyCommentCallback() {
                        @Override
                        public void onSuccess(String data) {
                            Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                            if (data.equals("1")) {
                                Log.d("postSendCommentData", "POST ResultFailed.");
                            } else {
                                comment_et.setText("");
                            }
                        }

                    });

                    Log.d("postSendCommentData", result);

                    // 여기서 email 주소를 가지고 서버에서 url 요청을 합니다.
                    // http://183.111.253.75/request_user_email_duplicate/
                    // request POST에 email 이란 항목으로 email 주소를 보내셔야 중복 확인이 가능합니다.
                    // 이메일 회원가입에서도 위 방식으로 중복확인해주세요
                    // 위 요청으로 중복이 있으면 홈으로 이동
                    // 없으면 회원가입창 이동해주시고 email 정보를 같이 보내주세요.
                }
            }
        });
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

    //Comment Save
    public String postSendCommentData(String user_id, String comment, String comment_date, String comment_source_id, VolleyCommentCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://183.111.253.75/request_save_comment/";

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
            String url = "http://183.111.253.75/request_journal_info_by_id/";

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
            String url = "http://183.111.253.75/request_comment_info_by_id/";

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
            String url = "http://183.111.253.75/request_user_pic_nickname_by_id/";

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

}




interface VolleyCommentCallback{
    void onSuccess(String data);
}