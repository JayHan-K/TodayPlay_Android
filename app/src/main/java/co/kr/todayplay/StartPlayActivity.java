package co.kr.todayplay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class StartPlayActivity extends AppCompatActivity {
    int sub;
    String email;
    String password;
    String name;
    String birth;
    String phone;
    String job;
    String nickname;
    String keyword;
    Button submit_btn;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_play_activity);
        submit_btn = findViewById(R.id.start_main);

        Intent intent = getIntent();
        sub =intent.getIntExtra("sub",0);
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        birth = intent.getStringExtra("birth");
        phone = intent.getStringExtra("phone");
        job = intent.getStringExtra("job");
        nickname = intent.getStringExtra("nickname");
        keyword = intent.getStringExtra("keyword");


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = keyword.replaceFirst(".&","");
                Log.d("keyword","keyword="+keyword);
                Log.d("birth","birth="+birth);

//                String newpassword = SHA256Util.getEncrypt(password,"todayplay");
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                intent.putExtra("name",name);
//                intent.putExtra("birth",birth);
//                intent.putExtra("phone",phone);
//                intent.putExtra("job",job);
//                intent.putExtra("nickname",nickname);
//                intent.putExtra("keyword",keyword);
//                startActivity(intent);



                String result = postData(email, password, birth, phone, job, nickname, keyword, new VolleyCallback() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("final data","data="+data);
                        if(data == "-1"){
                            Log.d("something failed","failed"+data);
                        }else{
                            SharedPreference.setAttribute(getApplicationContext(),"userId", data);
                            SharedPreference.setAttribute(getApplicationContext(),"nickname",nickname);
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("userId", data);
                            intent.putExtra("nickname",nickname);
//                        intent.putExtra("name",name);
//                        intent.putExtra("birth",birth);
//                        intent.putExtra("phone",phone);
//                        intent.putExtra("job",job);
//                        intent.putExtra("nickname",nickname);
//                        intent.putExtra("keyword",keyword);
                            startActivity(intent);
                            finish();
                        }


                    }
                });
            }
        });

    }



    public String postData(String email, String password, String birth, String phone, String job, String nickname,
                           String keyword,VolleyCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://211.174.237.197/request_save_user/";
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
                    params.put("password", password);
                    params.put("birth", birth);
                    params.put("phone", phone);
                    params.put("job", job);
                    params.put("nickname", nickname);
                    params.put("keyword", keyword);
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
