package co.kr.todayplay.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.kr.todayplay.ItemClickListener;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.RecyclerDecoration;
import co.kr.todayplay.adapter.ProfileHomeShowAdapter;
import co.kr.todayplay.adapter.RealReviewSearchSuggestionAdapter;
import co.kr.todayplay.object.Show;

public class ProfileFragment extends Fragment {
    private ArrayList<Show> PersonalizedShow = new ArrayList<Show>();
    RecyclerDecoration spaceDecoration = new RecyclerDecoration(25, 0);
    ScrollView profileFragmentmainScrollView;
    FrameLayout profileFragmentChildFragment;
    int user_id = -1;
    String play_count;
    String journal_count;
    String review_count;
    String nickname;
    String birthday;
    String job;
    String phone;
    String email;
    TextView my_review_id;
    TextView my_pick_id;
    TextView scrap_id;
    TextView mynickname;
    String exp;
    String rank;
    String[] keyword;
    TextView textView18;
    TextView textView20;


    public ProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_profile_fragment, container, false);
        profileFragmentmainScrollView = viewGroup.findViewById(R.id.pf_scrollView);
        profileFragmentChildFragment = viewGroup.findViewById(R.id.pf_fragment_child_fragment);
        my_review_id = viewGroup.findViewById(R.id.review_id);
        my_pick_id = viewGroup.findViewById(R.id.my_pick_id);
        scrap_id = viewGroup.findViewById(R.id.scrap_id);
        mynickname = viewGroup.findViewById(R.id.textView19);
        textView18 = viewGroup.findViewById(R.id.textView18);
        textView20 = viewGroup.findViewById(R.id.textView20);

        Bundle bundle = getArguments();
        if (bundle != null) {
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "user_id: " + user_id);
        }

        RecyclerView profileMyShowRecyclerView = viewGroup.findViewById(R.id.profile_my_show_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ItemClickListener mListener = (ItemClickListener) (new ItemClickListener() {
            @Override
            public void onItemClicked(@NotNull RecyclerView.ViewHolder vh, @NotNull Object item, int pos) {
                Show show = (Show) item;
//                homeChangeToShowDetail(show);
            }

            @Override
            public void onItemClicked(
                    @NotNull RealReviewSearchSuggestionAdapter.ViewHolder v,
                    @NotNull Object item,
                    int pos
            ) {
            }

        });

        PersonalizedShow = getPersonals();
        ProfileHomeShowAdapter profileAdapter = new ProfileHomeShowAdapter(PersonalizedShow, getContext(), mListener);
        profileMyShowRecyclerView.setAdapter(profileAdapter);
        profileMyShowRecyclerView.setLayoutManager(layoutManager);
        profileMyShowRecyclerView.addItemDecoration(spaceDecoration);

        try {
            new LoadingMyPick().execute();
        } catch (Exception e) {

        }


        ConstraintLayout profileMyReviewRelativeLayout = viewGroup.findViewById(R.id.profile_my_review_rl);
        profileMyReviewRelativeLayout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileChangeToReview();
            }

        }));

        Button profile_to_fav = viewGroup.findViewById(R.id.profile_to_fav);
        profile_to_fav.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileChangeToFav();
            }
        }));
        Button profile_to_info = viewGroup.findViewById(R.id.button11);
        profile_to_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                pf_change_info_Activity pf_change_info_activity = new pf_change_info_Activity();
                bundle1.putString("birthday",birthday);
                bundle1.putString("phone",phone);
                bundle1.putString("nickname",nickname);
                bundle1.putString("job",job);
                bundle1.putString("email",email);
                bundle1.putInt("user_id",user_id);
                pf_change_info_activity.setArguments(bundle1);
                ((MainActivity)view.getContext()).replaceFragment3(pf_change_info_activity);
            }
        });

        ConstraintLayout profileScrapRelativeLayout = viewGroup.findViewById(R.id.profile_scrap_rl);
        profileScrapRelativeLayout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileChangeToPick();
            }

        }));

        ConstraintLayout profileMyQnARelativeLayout = viewGroup.findViewById(R.id.profile_qna_rl);
        profileMyQnARelativeLayout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileChangeToScrap();
            }

        }));

        ImageView pf_setting = viewGroup.findViewById(R.id.pf_setting);
        pf_setting.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileChangeToSettings();
            }
        }));


        return viewGroup;
    }

    public ArrayList getPersonals() {
        ArrayList shows = new ArrayList();
        shows.add(
                new Show(
                        R.drawable.poster_sample6,
                        "마리퀴리"
                )
        );

        shows.add(
                new Show(
                        R.drawable.poster_sample5,
                        "렌트"
                )
        );

        shows.add(
                new Show(
                        R.drawable.poster_sample4,
                        "레미제라블"
                )
        );

        shows.add(
                new Show(
                        R.drawable.poster_sample2,
                        "라스트 세션"
                )
        );
        shows.add(
                new Show(
                        R.drawable.poster_sample9,
                        "쉬어매드니스"
                )
        );
        shows.add(
                new Show(
                        R.drawable.poster_sample15,
                        "파우스트"
                )
        );
        shows.add(
                new Show(
                        R.drawable.poster_sample10,
                        "썸씽로튼"
                )
        );
        shows.add(
                new Show(
                        R.drawable.poster_sample12,
                        "제이미"
                )
        );

        return shows;
    }

    public void profileChangeToReview() {
        Bundle bundle = new Bundle();
        MyReviewActivity myReviewActivity = new MyReviewActivity();
        bundle.putSerializable("user_id", user_id);
        myReviewActivity.setArguments(bundle);

        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                myReviewActivity
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);

    }

    public void profileChangeToPick() {
        Bundle bundle = new Bundle();
        MypickFragment mypickFragment = new MypickFragment();
        bundle.putSerializable("user_id", user_id);
        mypickFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                mypickFragment
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);

    }

    public void profileChangeToScrap() {
        MyScrapFragment myScrapFragment = new MyScrapFragment(user_id);
        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                myScrapFragment
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);


    }

    public void profileChangeToinfo() {

//        getChildFragmentManager().beginTransaction().replace(
//                R.id.pf_fragment_child_fragment,
//                new pf_change_info_Activity()
//        ).commitAllowingStateLoss();
//        profileFragmentmainScrollView.setVisibility(View.GONE);
//        profileFragmentChildFragment.setVisibility(View.VISIBLE);




    }

    public void profileChangeToFav() {
        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                new Profile_Fav_AnalyzeFragment()
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);

    }

    public void profileChangeToSettings() {
        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                new SettingsFragment()
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);
    }

    public void BackToHome() {
        profileFragmentChildFragment.setVisibility(View.GONE);
        profileFragmentmainScrollView.setVisibility(View.VISIBLE);
    }

    public String get_user_info(profileVolleyCallback callback) {
        try {
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = "http://211.174.237.197/request_user_info_by_id/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    callback.onSuccess(response);


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Login_Home", error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("user_id", String.valueOf(user_id));
                    return params;
                }
            };
            queue.add(stringRequest);
            return "1";

        } catch (Exception e) {
            Log.d("Login_Home", e.toString());

        }
        return "0";
    }


    private class LoadingMyPick extends AsyncTask {
        boolean isFinished = false;
        int count = 0;
        int maxCount = 0;

        @Override
        protected Object doInBackground(Object[] objects) {
            get_user_info(new profileVolleyCallback() {
                @Override
                public void onSuccess(String data) {
                    Log.d("profile_data", "profile_data=" + data);
                    String[] my_play_str = data.split("my_play\": \"")[1].split("\",")[0].split(",");
                    String[] my_journal_str = data.split("my_journal\": \"")[1].split("\",")[0].split(",");
                    String[] my_review_str = data.split("my_review\": \"")[1].split("\",")[0].split(",");
                    String my_nickname = data.split("nickname\": \"")[1].split("\",")[0];
                    birthday = data.split("birthday\": \"")[1].split("\",")[0];
                    job = data.split("job\": \"")[1].split("\",")[0];
                    email = data.split("email\": \"")[1].split("\",")[0];
                    phone = data.split("phone\": \"")[1].split("\",")[0];
                    exp = data.split("exp\": ")[1].split(",")[0];
                    rank = data.split("rank\": \"")[1].split("\",")[0];
                    String[] my_keyword = data.split("keyword\": \"")[1].split("\",")[0].split(",");
                    keyword = my_keyword;

                    Log.d("rank",rank);
                    Log.d("exp",exp);

                    play_count =Integer.toString(my_play_str.length) ;
                    journal_count = Integer.toString(my_journal_str.length);
                    review_count = Integer.toString(my_review_str.length-1);
                    nickname = my_nickname;
                    my_review_id.setText(review_count);
                    my_pick_id.setText(play_count);
                    scrap_id.setText(journal_count);
                    nickname = StringEscapeUtils.unescapeJava(nickname);
                    mynickname.setText(nickname);
                    textView18.setText(rank);
                    String exps = exp +"%";
                    textView20.setText(exps);
                    Log.d("count", "count = " + birthday + "  " + email + "  " + phone);
                    Log.d("nickname", "nickname= " + my_nickname);
                }
            });
            return null;
        }

    }
}

interface profileVolleyCallback{
    void onSuccess(String data);
}
