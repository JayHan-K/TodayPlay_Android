package co.kr.todayplay.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.VolleyMultipartRequest;
import co.kr.todayplay.VolleySingleton;


public class pf_change_info_Activity extends Fragment {
    static final int REQUESR_CODE = 1;
    CircularImageView profile_change_user_iv2;
    Uri uri;
    TextView textView28;
    TextView textView30;
    TextView textView32;
    InputMethodManager imm;
    ConstraintLayout linearLayout11;
    EditText rewrite_nick_name;
    Button button3;
    Button button_rewrite;
    int user_id;
    String imagePath = "";
    Bitmap bitmap;


    public pf_change_info_Activity(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.profile_info_change, container, false);
        Bundle bundle = getArguments();
        String nickname = bundle.getString("nickname");
        String birthday = bundle.getString("birthday");
        String phone = bundle.getString("phone");
        String email = bundle.getString("email");
        user_id = bundle.getInt("user_id");
        linearLayout11 = rootView.findViewById(R.id.linearLayout11);

        textView28 = rootView.findViewById(R.id.textView28);
        textView30 = rootView.findViewById(R.id.textView30);
        textView32 = rootView.findViewById(R.id.textView32);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


        button_rewrite = rootView.findViewById(R.id.button_rewrite);
        rewrite_nick_name = rootView.findViewById(R.id.rewrite_nick_name);
        button3 = rootView.findViewById(R.id.button3);
        final TextView textView24 = rootView.findViewById(R.id.textView24);
        Button back_profile2 = (Button)rootView.findViewById(R.id.back_profile2);
        CircularImageView circularImageView =(CircularImageView)rootView.findViewById(R.id.imageButton);
        profile_change_user_iv2 = (CircularImageView)rootView.findViewById(R.id.profile_change_user_iv2);

        textView24.setText(nickname);
        textView28.setText(email);
        textView30.setText(phone);
        textView32.setText(birthday);
        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,REQUESR_CODE);
            }
        });


        button_rewrite.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "중복확인 버튼 클릭시 중복이 없으면 바로 변경됩니다.", Toast.LENGTH_SHORT).show();
                rewrite_nick_name.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
                button_rewrite.setVisibility(View.GONE);
            }
        }));



        rewrite_nick_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                linearLayout11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rewrite_nick_name.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button_rewrite.setVisibility(View.VISIBLE);
                        imm.hideSoftInputFromWindow(rewrite_nick_name.getWindowToken(),0);
                    }
                });
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                linearLayout11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rewrite_nick_name.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button_rewrite.setVisibility(View.VISIBLE);
                        imm.hideSoftInputFromWindow(rewrite_nick_name.getWindowToken(),0);
                    }
                });

                button3.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(nickname.equals(rewrite_nick_name.getText().toString())){
                            Toast.makeText(getContext(), "현재와 같은 상태로 지정합니다.", Toast.LENGTH_SHORT).show();
                            rewrite_nick_name.setVisibility(View.GONE);
                            button3.setVisibility(View.GONE);
                            button_rewrite.setVisibility(View.VISIBLE);
                            imm.hideSoftInputFromWindow(rewrite_nick_name.getWindowToken(),0);
                        }else {
                            String nickname = rewrite_nick_name.getText().toString();
                            if (nickname.length()<=1 ){
                                Toast.makeText(getContext(),"2자이상 입력해 주세요",Toast.LENGTH_SHORT).show();
                            }else{
                                String result = postData(nickname,new VolleyCallback2(){
                                    @Override
                                    public void onSuccess(String data){
                                        Log.d("success volley??",data);
                                        if(data.equals("0")){
                                            Toast.makeText(getContext(),"중복없음",Toast.LENGTH_SHORT).show();
                                            Log.d("user_idin ",Integer.toString(user_id)+"  "+ rewrite_nick_name.getText().toString());

                                            String result2 = postData2(Integer.toString(user_id), rewrite_nick_name.getText().toString(), new VolleyCallback2() {
                                                @Override
                                                public void onSuccess(String data) {
                                                    Log.d("success volley??",data);
                                                    rewrite_nick_name.setVisibility(View.GONE);
                                                    button3.setVisibility(View.GONE);
                                                    button_rewrite.setVisibility(View.VISIBLE);
                                                    textView24.setText(rewrite_nick_name.getText().toString());

                                                    imm.hideSoftInputFromWindow(rewrite_nick_name.getWindowToken(),0);
                                                }
                                            });


                                        }else{
                                            Toast.makeText(getContext(),"중복있음",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }


                    }
                }));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        back_profile2.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                ProfileFragment parentFrag = (ProfileFragment) pf_change_info_Activity.this.getParentFragment() ;
//                parentFrag.BackToHome();
                ((MainActivity)rootView.getContext()).onBackPressed();
            }
        }));

        return rootView;
    }

    //권한에 대한 응답이 있을때 작동하는 함수
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if(requestCode == 1){
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    Log.d("MainActivity","권한 허용 : " + permissions[i]);
                }
            }
        }
    }




    public void checkSelfPermission() {
        String temp = "";
        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }
        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (!TextUtils.isEmpty(temp))
        {
            // 권한 요청
            ActivityCompat.requestPermissions(getActivity(), temp.trim().split(" "),1);
        } else {
            // 모두 허용 상태
            //Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
            Log.d("DialogFragment", "Checked Permission");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUESR_CODE){
            uri = data.getData();
            setImage(uri);
            Cursor c = getActivity().getContentResolver().query(Uri.parse(uri.toString()), null, null, null, null);
            c.moveToNext();
            String name_Str = getImageNameToUri(data.getData());
            String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
            String result = uploadImage(new VolleyCallback2() {
                @Override
                public void onSuccess(String data) {

                }
            });

        }

    }

    public void setImage(Uri uri){
        try{
            InputStream in = getActivity().getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(in);
            imagePath = getImageNameToUri(uri);
            profile_change_user_iv2.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        return imgName;
    }

    public String postData(String nickname, VolleyCallback2 callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = "http://211.174.237.197/request_user_nickname_duplicate/";
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
            Log.d("exception e", e.toString());

        }
        return "0";
    }

    public String postData2(String user_id2 ,String nickname, VolleyCallback2 callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = "http://211.174.237.197/request_change_nickname_by_user_id/";
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
                    params.put("user_id", user_id2);
                    params.put("nickname", nickname);

                    return params;
                }
            };
            queue.add(stringRequest);
            return resposeData[0];


        }catch(Exception e){
            Log.d("exception e", e.toString());

        }
        return "0";
    }

    private String uploadImage(VolleyCallback2 callback){
        // loading or check internet connection or something...
        // ... then
//        String url = "http://211.174.237.197/request_change_user_pic_by_user_id/";
        String url = "http://211.174.237.197/db_api/upload/user/";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String[] resposeData = {""};
                //                try {
//                    JSONObject result = new JSONObject(resultResponse);
                    String data = new String(response.data);
                    Log.d("DB_response", data);
                    resposeData[0] = data;

                    callback.onSuccess(data);
//                    String status = result.getString("status");
//                    String message = result.getString("message");
//
//                    if (status.equals(Constant.REQUEST_SUCCESS)) {
//                        // tell everybody you have succed upload image and post strings
//                        Log.i("Messsage", message);
//                    } else {
//                        Log.i("Unexpected", message);
//                    }
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");
//                params.put("user_id", Integer.toString(user_id));
//                params.put("user_pic",imagePath);
//                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
//                params.put("name", mNameInput.getText().toString());
//                params.put("location", mLocationInput.getText().toString());
//                params.put("about", mAboutInput.getText().toString());
//                params.put("contact", mContactInput.getText().toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                Log.d("bitmap live?",imagePath);
                Log.d("bitmap live?",bitmap.toString());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);


                params.put("file", new DataPart(imagePath,byteArrayOutputStream.toByteArray(), "image/jpeg"));
//                params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getActivity().getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));

                return params;
            }
        };

        VolleySingleton.getInstance(requireActivity().getBaseContext()).addToRequestQueue(multipartRequest);
//        AppController.getInstance().addToRequestQueue(multipartRequest);
        return "0";
    }

}

interface VolleyCallback2{
    void onSuccess(String data);
}
