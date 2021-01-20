package co.kr.todayplay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class JoinIdentificationActivity extends AppCompatActivity {
    Spinner telecom_spinner;
    ImageButton radioButton;
    EditText user_name_et, user_birth_et, user_phone_et;
    Button phone_certification_btn, next_btn;
    boolean radio_flag = false;
    int sub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_identification);
        Intent intent = getIntent();
        sub = intent.getIntExtra("sub",0);
        Log.d("sub", "onCreate: sub = " + sub);
        radioButton = (ImageButton) findViewById(R.id.radio_btn);
        user_name_et = (EditText)findViewById(R.id.join_user_name_et);
        user_birth_et = (EditText)findViewById(R.id.join_user_birth_et);
        user_phone_et = (EditText)findViewById(R.id.user_phone_et);
        phone_certification_btn = (Button)findViewById(R.id.phone_certification_btn);
        next_btn = (Button)findViewById(R.id.next_btn);

        ArrayList telecom_array = new ArrayList();
        telecom_array.add("SKT");
        telecom_array.add("KT");
        telecom_array.add("LG U+");
        telecom_spinner = (Spinner)findViewById(R.id.telecom_spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, telecom_array);
        telecom_spinner.setAdapter(arrayAdapter);

        user_phone_et.addTextChangedListener(watcher);
        user_birth_et.addTextChangedListener(watcher);
        user_name_et.addTextChangedListener(watcher);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sub==0){
                    Intent intent = new Intent(getApplicationContext(), JoinSettingProfileActivity.class);
                    intent.putExtra("sub", sub);
                    startActivity(intent);
                }
                //id 찾기
                else if(sub==1002){

                }
                //pw 찾기
                else if(sub==1003){

                }

            }
        });

        phone_certification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!radio_flag){
                    radio_flag = true;
                    radioButton.setBackgroundResource(R.drawable.join_radio_checked);
                }
                else{
                    radio_flag = false;
                    radioButton.setBackgroundResource(R.drawable.join_radio_unchecked);
                }
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
            if (radio_flag == false || user_name_et.getText().toString().length() == 0 || user_birth_et.getText().toString().length() == 0 ||
                    user_phone_et.getText().toString().length() == 0) {
                next_btn.setEnabled(false);
                phone_certification_btn.setEnabled(false);
                next_btn.setBackgroundResource(R.drawable.join_gray_rounded_corner);
                phone_certification_btn.setBackgroundResource(R.drawable.join_gray_rounded_corner);

            } else {
                phone_certification_btn.setEnabled(true);
                phone_certification_btn.setBackgroundResource(R.drawable.join_yellow_rounded_corner);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (radio_flag == false || user_name_et.getText().toString().length() == 0 || user_birth_et.getText().toString().length() == 0 ||
                    user_phone_et.getText().toString().length() == 0) {
                next_btn.setEnabled(false);
                phone_certification_btn.setEnabled(false);
                next_btn.setBackgroundResource(R.drawable.join_gray_rounded_corner);
                phone_certification_btn.setBackgroundResource(R.drawable.join_gray_rounded_corner);

            } else {
                next_btn.setEnabled(true);
                phone_certification_btn.setEnabled(true);
                next_btn.setBackgroundResource(R.drawable.join_yellow_rounded_corner);
                phone_certification_btn.setBackgroundResource(R.drawable.join_yellow_rounded_corner);
            }
        }
    };
}
