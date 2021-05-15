package co.kr.todayplay.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.kr.todayplay.AppHelper;
import co.kr.todayplay.BlurredImage;
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.DBHelper.PlayDB.Play;
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.DBHelper.UserDB.UserDBHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformPagerAdapter;
import co.kr.todayplay.object.Banner;
import co.kr.todayplay.object.Ranking;

public class PerformInfoFragment extends Fragment {
    PlayDBHelper playDBHelper;
    UserDBHelper userDBHelper;

    TabLayout tabLayout;
    ViewPager viewPager;
    PerformPagerAdapter performPagerAdapter;
    ConstraintLayout poster;
    ImageView realposter;
    TextView rangking_tv, category_tv;
    ImageButton back_btn;
    TextView perform_title_tv;

    int play_id = -1;
    int user_id = -1;

    Boolean heart_flag = false;

    String send_play_id_url = " http://211.174.237.197/request_rank_by_play_id/";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_perform_info, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id);
        }
        playDBHelper = new PlayDBHelper(this.getContext(), "Play.db",null,1);
        userDBHelper = new UserDBHelper(getActivity().getApplicationContext(), "User.db", null, 1);

        //메인 Activity가 메모리에서 만들어질 때, RequestQueue를 하나만 생성한다.
        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        realposter = (ImageView)viewGroup.findViewById(R.id.perfrom_img);
        back_btn = (ImageButton)viewGroup.findViewById(R.id.back_btn);
        poster = (ConstraintLayout)viewGroup.findViewById(R.id.poster_part);
        rangking_tv = (TextView)viewGroup.findViewById(R.id.rangking_tv);
        category_tv = (TextView)viewGroup.findViewById(R.id.category_tv);
        perform_title_tv = (TextView)viewGroup.findViewById(R.id.perfrom_title_tv);

        //Set 탭레이아웃, 뷰페이저
        tabLayout = (TabLayout)viewGroup.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("상세 정보"));
        tabLayout.addTab(tabLayout.newTab().setText("후기 분석"));
        tabLayout.addTab(tabLayout.newTab().setText("영상 자료"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        performPagerAdapter = new PerformPagerAdapter(getFragmentManager(), tabLayout.getTabCount(), play_id, user_id);
        viewPager = (ViewPager)viewGroup.findViewById(R.id.viewPager);
        viewPager.setAdapter(performPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                performPagerAdapter.notifyDataSetChanged();
                Log.d("TAG", "onTabSelected: "+tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }

        });

        //Set 카테고리, 제목, 포스터
        Log.d("perform Play_id","play_id="+play_id);
        String category = playDBHelper.getPlayCategory(play_id);
        String title = playDBHelper.getPlayTitle(play_id);
        perform_title_tv.setText(title);
        category_tv.setText(category);
        Log.d("perform path","path="+playDBHelper.getPlayPoster(play_id));
        String main_poster_path = getActivity().getApplicationContext().getFileStreamPath(playDBHelper.getPlayPoster(play_id)).toString();
        Bitmap bm = BitmapFactory.decodeFile(main_poster_path);
        realposter.setImageBitmap(bm);

        //Set 랭킹
        sendPOSTPlay_idRequest(send_play_id_url, Integer.toString(play_id));

        //배경
        /*
        if(!poster2.equals("")){
            Bitmap image = BitmapFactory.decodeFile(imgpath);
            realposter.setImageBitmap(image);
            Bitmap newImg = BlurredImage.fastblur(this.getContext(), image, 25);
            //Bitmap scaledImg = imageZoom(newImg,10);
            //Bitmap gradientImg = addGradient(newImg, Color.TRANSPARENT, Color.argb(0,37,37,37));
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), newImg);
            poster.setBackground(bitmapDrawable);
        }else {
            //Bitmap bm = BitmapFactory.decodeFile(item.getImg_path());
            //realposter.setImageBitmap(bm);
            //Bitmap newImg = BlurredImage.fastblur(this.getContext(), image, 25);
            //Bitmap scaledImg = imageZoom(newImg,10);
            //Bitmap gradientImg = addGradient(newImg, Color.TRANSPARENT, Color.argb(0,37,37,37));
            //BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), newImg);
            //poster.setBackground(bitmapDrawable);
        }
         */
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)viewGroup.getContext()).onBackPressed();
            }
        });


        return viewGroup;
    }

    public void sendPOSTPlay_idRequest(String url, String play_id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Play_idRequest", "Response = " + response);
                        if(!response.equals("0")){
                            rangking_tv.setText(response + "위");
                        }
                        else{
                            rangking_tv.setText("-위");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Play_idRequest", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("play_id", play_id);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    //수정
    public static Bitmap imageZoom(Bitmap bitmap, double maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] b = baos.toByteArray();
        double mid = (double) (b.length / 1024);
        double i = mid / maxSize;
        if (i > 1.0D) {
            bitmap = scaleWithWH(bitmap,
                    (double) bitmap.getWidth() / Math.sqrt(i),
                    (double) bitmap.getHeight() / Math.sqrt(i));
        }

        return bitmap;
    }

    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w != 0.0D && h != 0.0D && src != null) {
            int width = src.getWidth();
            int height = src.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidth = (float) (w / (double) width);
            float scaleHeight = (float) (h / (double) height);
            matrix.postScale(scaleWidth, scaleHeight);
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix,
                    true);
        } else {
            return src;
        }
    }

    public Bitmap addGradient(Bitmap src, int color1, int color2)
    {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,0,0,h, color1, color2, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0,0,w,h,paint);

        return result;
    }
}