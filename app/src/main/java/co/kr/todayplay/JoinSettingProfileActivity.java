package co.kr.todayplay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JoinSettingProfileActivity extends AppCompatActivity {
    ImageView profile_iv;
    EditText nickname_et;
    Button next_btn, nickname_check_btn, total_certification_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_setting_profile);
        profile_iv = (ImageView)findViewById(R.id.user_profile_iv);
        nickname_et = (EditText)findViewById(R.id.user_nickname_et);
        next_btn = (Button)findViewById(R.id.next_btn);
        nickname_check_btn = (Button)findViewById(R.id.nickname_check_btn);
        total_certification_btn = (Button)findViewById(R.id.total_certification_btn);

        nickname_et.addTextChangedListener(watcher);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinPreferenceAnalysisGuideActivity.class);
                startActivity(intent);
            }
        });
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (nickname_et.getText().toString().length() == 0) {
                next_btn.setEnabled(false);
                nickname_check_btn.setEnabled(false);
                next_btn.setBackgroundResource(R.drawable.join_gray_rounded_corner);
                nickname_check_btn.setBackgroundResource(R.drawable.join_gray_rounded_corner);

            } else {
                next_btn.setEnabled(true);
                nickname_check_btn.setEnabled(true);
                next_btn.setBackgroundResource(R.drawable.join_yellow_rounded_corner);
                nickname_check_btn.setBackgroundResource(R.drawable.join_yellow_rounded_corner);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (nickname_et.getText().toString().length() == 0) {
                next_btn.setEnabled(false);
                nickname_check_btn.setEnabled(false);
                next_btn.setBackgroundResource(R.drawable.join_gray_rounded_corner);
                nickname_check_btn.setBackgroundResource(R.drawable.join_gray_rounded_corner);

            } else {
                next_btn.setEnabled(true);
                nickname_check_btn.setEnabled(true);
                next_btn.setBackgroundResource(R.drawable.join_yellow_rounded_corner);
                nickname_check_btn.setBackgroundResource(R.drawable.join_yellow_rounded_corner);
            }
        }
    };
}
