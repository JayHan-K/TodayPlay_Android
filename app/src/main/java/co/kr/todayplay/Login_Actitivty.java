package co.kr.todayplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
                Intent intent = new Intent(getApplicationContext(), JoinIdentificationActivityVer2.class);
                intent.putExtra("sub", find_id_sub);
                startActivity(intent);
            }
        });

        find_pw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinIdentificationActivityVer2.class);
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
                String password_st = password.getText().toString();
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
                String password_st = password.getText().toString();
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
                String email_st = email.getText().toString();
                String password_st = password.getText().toString();
                String salt ="todayplay";
                String newPassword =SHA256Util.sha256(password_st);
                Log.d("newpassword","newpassword="+newPassword+" "+email_st);

                String result = postData(email_st, password_st, new VolleyCallback() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("data","data="+data);

                        if(data.equals("-1")){
                            Toast.makeText(getApplicationContext(),"로그인 정보가 틀렸습니다",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent (getApplicationContext(),Login_Home_Activity.class);
//                            startActivity(intent);
//                            finish();
                            //현재 페이지에 고정후 알림만 제공
                        }else {
                            SharedPreference.setAttribute(getApplicationContext(),"userId", data);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("userId", data);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

//                if(email_st.equals("todayplay") && password_st.equals("0000")){
//                String result = postData(email_st, newPassword, new VolleyCallback() {
//                    @Override
//                    public void onSuccess(String data) {
//                        Toast.makeText(getApplicationContext(),"Result"+data,Toast.LENGTH_SHORT).show();
//                        if(data =="1"){
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }else{
//
//                        }
//                    }
//                });
            }
        });

    }

    public String postData(String email,String password,VolleyCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://211.174.237.197/request_login_state/";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {


                    String data = response;
                    Log.d("Login_Home", data);
                    resposeData[0] = data;

                    callback.onSuccess(data);


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Login_Home", error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("email", email);
                    params.put("password",password);
                    return params;
                }
            };
            queue.add(stringRequest);
            return resposeData[0];


        } catch (Exception e) {
            Log.d("Login_Home", e.toString());

        }
        return "0";
    }
}
