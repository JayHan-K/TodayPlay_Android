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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

public class JoinIdentificationActivityVer2 extends AppCompatActivity {
    EditText user_name_et, user_birth_et, user_phone_et, identifi_num_et, job_et;
    Button phone_certification_btn, check_btn, next_btn;
    FirebaseAuth mAuth;
    String codeSent;
    private int counter = 60;
    int sub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_identification_ver2);
        Intent intent = getIntent();
        sub = intent.getIntExtra("sub",0);
        Log.d("sub", "onCreate: sub = " + sub);

        //sms auth init
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("ko");


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
                sendVerificationCode();

            }
        });

        check_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                verifySignInCode();
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

    private void verifySignInCode(){
        String code = identifi_num_et.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //인증 성공한 이후 코드 작성
                            Toast.makeText(getApplicationContext(), "Verification Successful", Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }


    private void sendVerificationCode(){

        String phone = user_phone_et.getText().toString();

        if(phone.isEmpty()){
            user_phone_et.setError("Phone number is required");
            user_phone_et.requestFocus();
            return;
        }

        if(phone.length() < 10 ){
            user_phone_et.setError("Please enter a valid phone");
            user_phone_et.requestFocus();
            return;
        }
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+82" + phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }



    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.d("Complete", "Complete");
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d("Failed", e.toString());
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(JoinIdentificationActivityVer2.this, "code sent : " + s, Toast.LENGTH_SHORT).show();
            codeSent = s;
        }
    };



}
