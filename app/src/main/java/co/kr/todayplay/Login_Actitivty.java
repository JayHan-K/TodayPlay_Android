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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Login_Actitivty extends AppCompatActivity {

    String email_input = null , password_input=null;
    public static final int find_id_sub = 1002;
    public static final int find_pw_sub = 1003;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_default);
//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.pf_scrap_rv);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        Button login_go_home = findViewById(R.id.login_go_back2);
        final Button login_btn = findViewById(R.id.login_btn);
        ConstraintLayout linearlayout19 = findViewById(R.id.linearLayout19);

        final Button find_id_btn = findViewById(R.id.find_id_btn);
        final Button find_pw_btn = findViewById(R.id.find_password_btn);

        find_id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinIdentificationActivity.class);
                intent.putExtra("sub", find_id_sub);
                startActivity(intent);
            }
        });

        find_pw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinIdentificationActivity.class);
                intent.putExtra("sub", find_pw_sub);
                startActivity(intent);
            }
        });

        final EditText email = findViewById(R.id.editText);
        final EditText password = findViewById(R.id.editText2);
        final InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        linearlayout19.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                email.clearFocus();
                password.clearFocus();
                imm.hideSoftInputFromWindow(email.getWindowToken(),0);
                return false;
            }
        });

        login_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        login_btn.setEnabled(false);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email_st = email.getText().toString();
                String password_st = email.getText().toString();
                email_input = email_st;
                password_input = password_st;
                if(email_input != null && password_input != null){
                    login_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email_st = email.getText().toString();
                String password_st = email.getText().toString();
                email_input = email_st;
                password_input = password_st;
                if(email_input != null && password_input != null){
                    login_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });




    }
}
