package co.kr.todayplay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class JoinSettingProfileActivity extends AppCompatActivity {
    ImageView profile_iv;
    EditText nickname_et;
    Button next_btn, nickname_check_btn, total_certification_btn;
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
        setContentView(R.layout.activity_join_setting_profile);
        profile_iv = (ImageView)findViewById(R.id.user_profile_iv);
        nickname_et = (EditText)findViewById(R.id.user_nickname_et);
        next_btn = (Button)findViewById(R.id.next_btn);
        nickname_check_btn = (Button)findViewById(R.id.nickname_check_btn);
        total_certification_btn = (Button)findViewById(R.id.total_certification_btn);
        nickname_check_btn.setEnabled(false);
        next_btn.setEnabled(false);
        Intent intent = getIntent();
        sub =intent.getIntExtra("sub",0);
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        birth = intent.getStringExtra("birth");
        phone = intent.getStringExtra("phone");
        job = intent.getStringExtra("job");

        Log.d("btn enabel","enabled"+nickname_check_btn.isEnabled());

        nickname_et.addTextChangedListener(watcher);


        Log.d("btn enabel","enabled"+nickname_check_btn.isEnabled());

        nickname_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = nickname_et.getText().toString();
                Log.d("nickname","nickname="+nickname);
                String result = postData(nickname,new VolleyCallback(){
                    @Override
                    public void onSuccess(String data){
                        Log.d("success volley??",data);
                        if(data.equals("0")){
                            Toast.makeText(getApplicationContext(),"중복없음",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"중복있음",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname = nickname_et.getText().toString();
                Log.d("emailthird","email="+email);
                Log.d("nickname","nickname="+nickname);
                Log.d("birth","birth="+birth);
                Intent intent = new Intent(getApplicationContext(), JoinPreferenceAnalysisGuideActivity.class);
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


        Log.d("btn enabel","enabled"+next_btn.isEnabled());

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


    public String postData(String nickname, VolleyCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://183.111.253.75/request_user_nickname_duplicate/";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {


                    String data = response;
                    Log.d("DB_response", data);
                    resposeData[0] = data;

                    callback.onSuccess(data);


                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("volleyerror", error.toString());
                }
            })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json");
                    params.put("nickname", nickname);
                    return params;
                }
            };
            queue.add(stringRequest);
            return resposeData[0];


        }catch(Exception e){
            Log.d("excoption e", e.toString());

        }
        return "0";
    }




}

