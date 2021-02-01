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

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JoinIdentificationActivityVer2 extends AppCompatActivity {
    EditText user_name_et, user_birth_et, user_phone_et, identifi_num_et, job_et;
    Button phone_certification_btn, check_btn, next_btn;
    int sub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_identification_ver2);
        Intent intent = getIntent();
        sub = intent.getIntExtra("sub",0);
        Log.d("sub", "onCreate: sub = " + sub);

        user_name_et = (EditText)findViewById(R.id.join_user_name_et);
        user_birth_et = (EditText)findViewById(R.id.join_user_birth_et);
        user_phone_et = (EditText)findViewById(R.id.user_phone_et);
        identifi_num_et = (EditText)findViewById(R.id.identifi_num_et);
        job_et = (EditText)findViewById(R.id.job_et);

        check_btn = (Button)findViewById(R.id.check_btn);
        next_btn = (Button)findViewById(R.id.next_btn);
        phone_certification_btn = (Button)findViewById(R.id.phone_certification_btn);
        phone_certification_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
        check_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
        next_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);

        user_name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if(user_name_et.getText().toString().length() > 0 && user_birth_et.getText().toString().length() > 0 && user_phone_et.getText().toString().length() > 0){
                    phone_certification_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                }
                else{
                    phone_certification_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        user_birth_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if(user_name_et.getText().toString().length() > 0 && user_birth_et.getText().toString().length() > 0 && user_phone_et.getText().toString().length() > 0){
                    phone_certification_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                }
                else{
                    phone_certification_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        user_phone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if(user_name_et.getText().toString().length() > 0 && user_birth_et.getText().toString().length() > 0 && user_phone_et.getText().toString().length() > 0){
                    phone_certification_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                }
                else{
                    phone_certification_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                }
                check_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                next_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        identifi_num_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if(identifi_num_et.getText().toString().length() > 0 && user_name_et.getText().toString().length() > 0 && user_birth_et.getText().toString().length() > 0 && user_phone_et.getText().toString().length() > 0){
                    check_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                }
                else{
                    check_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                }
                phone_certification_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                next_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        job_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if(job_et.getText().toString().length() > 0 && identifi_num_et.getText().toString().length() > 0 && user_name_et.getText().toString().length() > 0 && user_birth_et.getText().toString().length() > 0 && user_phone_et.getText().toString().length() > 0){
                    check_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    phone_certification_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                    next_btn.setBackgroundResource(R.drawable.rounded_border_8dp_fed700);
                }
                else{
                    next_btn.setBackgroundResource(R.drawable.rounded_border_8dp_d3d3d3);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        phone_certification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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



    }
}
