package co.kr.todayplay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JoinPreferenceAnalysisGuideActivity extends AppCompatActivity {
    Button button;
    int sub;
    String email;
    String password;
    String name;
    String birth;
    String phone;
    String job;
    String nickname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_preference_analysis_guide_ver2);

        Intent intent = getIntent();
        sub =intent.getIntExtra("sub",0);
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        birth = intent.getStringExtra("birth");
        phone = intent.getStringExtra("phone");
        job = intent.getStringExtra("job");
        nickname = intent.getStringExtra("nickname");

        button = (Button)findViewById(R.id.next_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinPreferenceAnalysisActivity.class);
                Log.d("emailforth","emailforth="+email);
                intent.putExtra("sub",sub);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("name", name);
                intent.putExtra("birth", birth);
                intent.putExtra("phone", phone);
                intent.putExtra("job", job);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
                finish();
            }
        });
    }
}
