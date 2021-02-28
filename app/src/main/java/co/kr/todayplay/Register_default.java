package co.kr.todayplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

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
                Log.d("clicked","Clicked");
                String get_email_st = get_email.getText().toString();
                Log.d("clicked","Clicked"+get_email_st);
                String result = postData(get_email_st, new VolleyCallback() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("data","data="+data);
                        if(data.equals("0")){
                            //중복없음
                            String get_password_st1 = get_password.getText().toString();
                            String get_email_st = get_email.getText().toString();
                            String get_password_st2 = get_password2.getText().toString();

                            if(!get_password_st1.equals(get_password_st2)) {
                                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }else if(get_password_st1.length()<8 || get_password_st1.length()>20){
                                Toast.makeText(getApplicationContext(), "비밀번호를 8-20자로 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String sha256_password = SHA256Util.sha256(get_password_st1);
                                Intent intent = new Intent(getApplicationContext(), JoinIdentificationActivityVer2.class);
                                intent.putExtra("email", get_email_st);
                                intent.putExtra("password", sha256_password);
                                startActivity(intent);
                                finish();
                            }


                        }else{
                            Toast.makeText(getApplicationContext(), "이미 사용중인 이메일입니다. 다른 이메일을 사용해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Log.d("clicked","Clicked"+result);



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
                if(!email_input.equals("") && !password_input.equals("") && !password_input2.equals("")){
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
                if(!email_input.equals("") && !password_input.equals("") && !password_input2.equals("")){
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
                if(!email_input.equals("") && !password_input.equals("") && !password_input2.equals("")){
                    register_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    public String postData(String email,VolleyCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://183.111.253.75/request_user_email_duplicate/";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {


                    String data = response;
                    Log.d("Login_Home", data);
                    resposeData[0] = data;
                    callback.onSuccess(data);


                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Login_Home", error.toString());
                }
            })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json");
                    params.put("email", email);
                    return params;
                }
            };
            queue.add(stringRequest);
            return resposeData[0];


        }catch(Exception e){
            Log.d("Login_Home", e.toString());

        }
        return "0";
    }
}
