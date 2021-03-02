package co.kr.todayplay;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class JoinPreferenceAnalysisActivity extends AppCompatActivity {
    int sub;
    String email;
    String password;
    String name;
    String birth;
    String phone;
    String job;
    String nickname;
    String keyword;

    int fun_btn_cnt, horror_btn_cnt, romance_btn_cnt, darama_btn_cnt, play_btn_cnt, musical_btn_cnt, chang_btn_cnt, kids_btn_cnt, history_btn_cnt;
    int large_btn_cnt, mid_btn_cnt, small_btn_cnt, cry_btn_cnt, warm_btn_cnt, intense_btn_cnt, funny_btn_cnt, feelings_btn_cnt;

    Button fun_btn, horror_btn, romance_btn, darama_btn, play_btn, musical_btn, chang_btn, kids_btn, history_btn;
    Button large_btn, mid_btn, small_btn,cry_btn,warm_btn,intense_btn,submit_btn,funny_btn,feelings_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_preference_analysis);
        fun_btn = findViewById(R.id.fun_btn);
        horror_btn = findViewById(R.id.horror_btn);
        romance_btn = findViewById(R.id.romance_btn);
        darama_btn = findViewById(R.id.darama_btn);
        play_btn = findViewById(R.id.play_btn);
        musical_btn = findViewById(R.id.musical_btn);
        chang_btn = findViewById(R.id.chang_btn);
        kids_btn = findViewById(R.id.kids_btn);
        history_btn = findViewById(R.id.history_btn);
        large_btn = findViewById(R.id.large_btn);
        mid_btn = findViewById(R.id.mid_btn);
        small_btn = findViewById(R.id.small_btn);
        cry_btn = findViewById(R.id.cry_btn);
        warm_btn = findViewById(R.id.warm_btn);
        intense_btn = findViewById(R.id.intense_btn);
        funny_btn = findViewById(R.id.funny_btn);
        feelings_btn = findViewById(R.id.feelings_btn);
        submit_btn =findViewById(R.id.submit_btn);




        keyword = "";
        Intent intent = getIntent();
        sub =intent.getIntExtra("sub",0);
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        birth = intent.getStringExtra("birth");
        phone = intent.getStringExtra("phone");
        job = intent.getStringExtra("job");
        nickname = intent.getStringExtra("nickname");

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("birth","birth="+birth);
                Intent intent = new Intent(getApplicationContext(),StartPlayActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("name",name);
                intent.putExtra("birth",birth);
                intent.putExtra("phone",phone);
                intent.putExtra("job",job);
                intent.putExtra("nickname",nickname);
                intent.putExtra("keyword",keyword);
                startActivity(intent);
                finish();
            }
        });


        fun_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fun_btn_cnt==0){
//                    fun_btn.setBackgroundColor(Color.parseColor("#fed700"));
                    fun_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "신나는/역동적인" + "," + keyword;
                    fun_btn_cnt=1;
                    Log.d("fun_btn","fun_btn"+keyword);
                }else{
//                    fun_btn.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    fun_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("신나는/역동적인,","");
                    fun_btn_cnt=0;
                    Log.d("fun_btn","fun_btn"+keyword);
                }
            }
        });

        horror_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(horror_btn_cnt==0){
                    horror_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "스릴러/역동적인"+ "," + keyword;
                    horror_btn_cnt=1;
                    Log.d("horror_btn","horror_btn"+keyword);
                }else{
                    horror_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("스릴러/역동적인,","");
                    horror_btn_cnt=0;
                    Log.d("horror_btn","horror_btn"+keyword);
                }
            }
        });

        romance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(romance_btn_cnt==0){
                    romance_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "로맨스"+ "," + keyword;
                    romance_btn_cnt=1;
                    Log.d("romance_btn","romance_btn"+keyword);
                }else{
                    romance_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("로맨스,","");
                    romance_btn_cnt=0;
                    Log.d("romance_btn","romance_btn"+keyword);
                }
            }
        });

        darama_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(darama_btn_cnt==0){
                    darama_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "감동/드라마"+ "," + keyword;
                    darama_btn_cnt=1;
                    Log.d("darama_btn","darama_btn"+keyword);
                }else{
                    darama_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("감동/드라마,","");
                    darama_btn_cnt=0;
                    Log.d("darama_btn","darama_btn"+keyword);
                }
            }
        });

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(play_btn_cnt==0){
                    play_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "연극"+ "," + keyword;
                    play_btn_cnt=1;
                    Log.d("play_btn","play_btn"+keyword);
                }else{
                    play_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("연극,","");
                    play_btn_cnt=0;
                    Log.d("play_btn","play_btn"+keyword);
                }
            }
        });

        musical_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musical_btn_cnt==0){
                    musical_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "뮤지컬"+ "," + keyword;
                    musical_btn_cnt=1;
                    Log.d("musical_btn","musical_btn"+keyword);
                }else{
                    musical_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("뮤지컬,","");
                    musical_btn_cnt=0;
                    Log.d("musical_btn","musical_btn"+keyword);
                }
            }
        });

        chang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chang_btn_cnt==0){
                    chang_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "창극"+ "," + keyword;
                    chang_btn_cnt=1;
                    Log.d("chang_btn","chang_btn"+keyword);
                }else{
                    chang_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("창극,","");
                    chang_btn_cnt=0;
                    Log.d("chang_btn","chang_btn"+keyword);
                }
            }
        });

        kids_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kids_btn_cnt==0){
                    kids_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "어린이" + "," + keyword;
                    kids_btn_cnt=1;
                    Log.d("fun_btn","fun_btn"+keyword);
                }else{
                    kids_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("어린이,","");
                    kids_btn_cnt=0;
                    Log.d("fun_btn","fun_btn"+keyword);
                }
            }
        });

        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(history_btn_cnt==0){
                    history_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "역사/고전극" + "," + keyword;
                    history_btn_cnt=1;
                    Log.d("fun_btn","fun_btn"+keyword);
                }else{
                    history_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("역사/고전극,","");
                    history_btn_cnt=0;
                    Log.d("fun_btn","fun_btn"+keyword);
                }
            }
        });

        large_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(large_btn_cnt==0){
                    large_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "대극장" + "," + keyword;
                    large_btn_cnt=1;
                    Log.d("fun_btn","fun_btn"+keyword);
                }else{
                    large_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("대극장,","");
                    large_btn_cnt=0;
                    Log.d("fun_btn","fun_btn"+keyword);
                }
            }
        });

        mid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mid_btn_cnt==0){
                    mid_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "중극장" + "," + keyword;
                    mid_btn_cnt=1;
                    Log.d("fun_btn","fun_btn"+keyword);
                }else{
                    mid_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("중극장,","");
                    mid_btn_cnt=0;
                    Log.d("fun_btn","fun_btn"+keyword);
                }
            }
        });

        small_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(small_btn_cnt==0){
                    small_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "소극장" + "," + keyword;
                    small_btn_cnt=1;
                    Log.d("fun_btn","fun_btn"+keyword);
                }else{
                    small_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("소극장,","");
                    small_btn_cnt=0;
                    Log.d("fun_btn","fun_btn"+keyword);
                }
            }
        });

        cry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cry_btn_cnt==0){
                    cry_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "눈물 콧물 쏙 빼는" + "," + keyword;
                    cry_btn_cnt=1;
                    Log.d("fun_btn","fun_btn"+keyword);
                }else{
                    cry_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("눈물 콧물 쏙 빼는,","");
                    cry_btn_cnt=0;
                    Log.d("fun_btn","fun_btn"+keyword);
                }
            }
        });

        warm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(warm_btn_cnt==0){
                    warm_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "마음이 따뜻해지는" + "," + keyword;
                    warm_btn_cnt=1;
                    Log.d("fun_btn","fun_btn"+keyword);
                }else{
                    warm_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("마음이 따뜻해지는,","");
                    warm_btn_cnt=0;
                    Log.d("fun_btn","fun_btn"+keyword);
                }
            }
        });

        intense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intense_btn_cnt==0){
                    intense_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "긴장감이 있는" + "," + keyword;
                    intense_btn_cnt=1;
                    Log.d("intense_btn","intense_btn"+keyword);
                }else{
                    intense_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("긴장감이 있는,","");
                    intense_btn_cnt=0;
                    Log.d("intense_btn","intense_btn"+keyword);
                }
            }
        });

        funny_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(funny_btn_cnt==0){
                    funny_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "웃음 폭탄" + "," + keyword;
                    funny_btn_cnt=1;
                    Log.d("fun_btn","fun_btn"+keyword);
                }else{
                    funny_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("웃음 폭탄,","");
                    funny_btn_cnt=0;
                    Log.d("fun_btn","fun_btn"+keyword);
                }
            }
        });

        feelings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(feelings_btn_cnt==0){
                    feelings_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    keyword = "여운이 남는" + "," + keyword;
                    feelings_btn_cnt=1;
                    Log.d("fun_btn","fun_btn"+keyword);
                }else{
                    feelings_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                    keyword = keyword.replace("여운이 남는,","");
                    feelings_btn_cnt=0;
                    Log.d("fun_btn","fun_btn"+keyword);
                }
            }
        });

    }

}
