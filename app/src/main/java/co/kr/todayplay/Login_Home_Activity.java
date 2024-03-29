package co.kr.todayplay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.HashMap;
import java.util.Map;

public class Login_Home_Activity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInOptions gso;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;



    public static final int sub = 1001;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home);

        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id_string))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Button button6 = findViewById(R.id.button6);
        Button email_regit = findViewById(R.id.button4);
        Button google_login_bt = findViewById(R.id.google_login_bt);

        google_login_bt.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Login_Home_Activity", "Google Login Start");
                Intent googleSignInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(googleSignInIntent, RC_SIGN_IN);
            }
        }));

        email_regit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register_default.class);
                startActivityForResult(intent,sub);
                finish();
            }
        }));

        button6.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login_Actitivty.class);
                startActivityForResult(intent,sub);
                finish();
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("Login", "firebaseAuthWithGoogle" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

            }catch (ApiException e){
                Log.w("Login_Home_Activity", "Google Sign in Failed", e);

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Login_Home_Activity", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email = user.getEmail();
                            Log.d("Login_Home_Activity", email.toString());

                            String result = postData(email, new VolleyCallback() {
                                @Override
                                public void onSuccess(String data) {
//                                    Toast.makeText(getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                                    String email = user.getEmail();
                                    Log.d("emailfirst","email="+email);
                                    if (data.equals("1")) {
                                        String result = get_user_id_from_google(email, new VolleyCallback() {
                                            @Override
                                            public void onSuccess(String data) {
                                                Log.d("Login_Home_Activity", "user_id : " + data);
                                                String result2 = postData2(data, new VolleyCallback() {
                                                    @Override
                                                    public void onSuccess(String data2) {
                                                        Log.d("data2",data2);
                                                        String my_nickname = data2.split("nickname\": \"")[1].split("\"")[0];
                                                        my_nickname = StringEscapeUtils.unescapeJava(my_nickname);

                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        intent.putExtra("userId", data);
                                                        intent.putExtra("nickname",my_nickname);
                                                        SharedPreference.setAttribute(getApplicationContext(),"nickname",my_nickname);

                                                        MainActivity mainActivity = (MainActivity)MainActivity.MainActivity;
                                                        mainActivity.finish();
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
//                                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                                                intent.putExtra("userId", data);
//
//                                                startActivity(intent);
//                                                finish();
                                            }
                                        });


                                    } else {
                                        Intent intent = new Intent(getApplicationContext(), JoinIdentificationActivityVer2.class);
                                        intent.putExtra("email", email);
                                        intent.putExtra("password", "goog");
                                        startActivityForResult(intent, sub);
                                        finish();
                                    }
                                }

                            });

                            Log.d("Login_Home_Activity", result);

                            // 여기서 email 주소를 가지고 서버에서 url 요청을 합니다.
                            // http://183.111.253.75/request_user_email_duplicate/
                            // request POST에 email 이란 항목으로 email 주소를 보내셔야 중복 확인이 가능합니다.
                            // 이메일 회원가입에서도 위 방식으로 중복확인해주세요
                            // 위 요청으로 중복이 있으면 홈으로 이동
                            // 없으면 회원가입창 이동해주시고 email 정보를 같이 보내주세요.


                        } else {
                            Log.d("Login", "signInWithCredential:failurer");
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        MainActivity mainActivity = (MainActivity)MainActivity.MainActivity;
        mainActivity.finish();
        startActivity(intent);
        finish();
    }

    public String postData(String email, VolleyCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://211.174.237.197/request_user_email_duplicate/";

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

    public String postData2(String user_id2,VolleyCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://211.174.237.197/request_user_pic_nickname_by_id/";
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
                    params.put("user_id",user_id2);
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

    public String get_user_id_from_google(String email, VolleyCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://211.174.237.197/request_user_id_by_google/";

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
interface VolleyCallback{
    void onSuccess(String data);
}

