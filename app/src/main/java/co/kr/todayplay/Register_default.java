package co.kr.todayplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Register_default extends AppCompatActivity {

    String email_input = null , password_input = null , password_input2 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_default);
        final Button register_btn = findViewById(R.id.register_btn);
        Button login_go_home3 = findViewById(R.id.login_go_back3);
        final EditText get_email = findViewById(R.id.get_email);
        final EditText get_password = findViewById(R.id.get_password);
        final EditText get_password2 = findViewById(R.id.get_password2);
        final InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        ConstraintLayout linearlayout30 = findViewById(R.id.linearLayout30);

        linearlayout30.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                get_email.clearFocus();
                get_password.clearFocus();
                get_password2.clearFocus();
                imm.hideSoftInputFromWindow(get_email.getWindowToken(),0);
                return false;
            }
        });

        login_go_home3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register_btn.setEnabled(false);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinIdentificationActivity.class);
                startActivity(intent);
            }
        });

        get_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String get_email_st = get_email.getText().toString();
                String get_password_st = get_password.getText().toString();
                String get_password_st2 = get_password2.getText().toString();
                email_input = get_email_st;
                password_input = get_password_st;
                password_input2 = get_password_st2;
                if(email_input != null && password_input != null && password_input2 != null){
                    register_btn.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        get_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String get_email_st = get_email.getText().toString();
                String get_password_st = get_password.getText().toString();
                String get_password_st2 = get_password2.getText().toString();
                email_input = get_email_st;
                password_input = get_password_st;
                password_input2 = get_password_st2;
                if(email_input != null && password_input != null && password_input2 != null){
                    register_btn.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        get_password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String get_email_st = get_email.getText().toString();
                String get_password_st = get_password.getText().toString();
                String get_password_st2 = get_password2.getText().toString();
                email_input = get_email_st;
                password_input = get_password_st;
                password_input2 = get_password_st2;
                if(email_input != null && password_input != null && password_input2 != null){
                    register_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
























    }
}
