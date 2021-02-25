package co.kr.todayplay;

import android.content.Intent;
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
                keyword = "신나는/역동적인" + "," + keyword;
                fun_btn.setEnabled(false);
                Log.d("fun_btn","fun_btn"+keyword);
            }
        });

        horror_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "스릴러/역동적인"+ "," + keyword  ;
                horror_btn.setEnabled(false);
                Log.d("horror_btn","horror_btn"+keyword);
            }
        });

        romance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "로맨스"+ "," + keyword;
                romance_btn.setEnabled(false);
                Log.d("romance_btn","romance_btn"+keyword);
            }
        });
        darama_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "감동/드라마"+ "," + keyword;
                darama_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "연극"+ "," + keyword;
                play_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        musical_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "뮤지컬"+ "," + keyword;
                musical_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        chang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "창극"+ "," + keyword;
                chang_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        kids_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "어린이"+ "," + keyword;
                kids_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "역사/고전극"+ "," + keyword;
                history_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        large_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "대극장"+ "," + keyword;
                large_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        mid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "중극장"+ "," + keyword;
                mid_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        small_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "소극장"+ "," + keyword;
                small_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        cry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "눈물 콧물 쏙 빼는"+ "," + keyword;
                cry_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        warm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "마음이 따뜻해지는"+ "," + keyword;
                warm_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        intense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "긴장감이 있는"+ "," + keyword;
                intense_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });

        funny_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "웃음 폭탄"+ "," + keyword;
                funny_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });
        feelings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "여운이 남는"+ "," + keyword;
                feelings_btn.setEnabled(false);
                Log.d("darama_btn","darama_btn"+keyword);
            }
        });

    }

}
