package co.kr.todayplay.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import co.kr.todayplay.R;
import co.kr.todayplay.SharedPreference;

public class PopupActivity extends DialogFragment implements View.OnClickListener {

    public static final String TAG_EVENT_DIALOG = "dialog_event";
    Point size;
    ImageView imageView18;

    public PopupActivity() {
    }

    public static PopupActivity getInstance() {
        PopupActivity e = new PopupActivity();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.popup_activity,container);
        imageView18 = (ImageView)v.findViewById(R.id.imageView18);
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        setCancelable(false);
        imageView18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String never ="1";
                SharedPreference.setAttribute(getContext(),"never",never);
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onClick(View v){
        dismiss();
    }
    @Override
    public void onResume() {
        int width = (int) (size.x*0.9);
        int height = (int) (size.y*0.7);
        getDialog().getWindow().setLayout(width,height);
        super.onResume();
    }

}
//    TextView txtText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        //타이틀바 없애기
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        layoutParams.dimAmount = 0.7f;
//        getWindow().setAttributes(layoutParams);
//        setContentView(R.layout.popup_activity);
//        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//
//        int width = (int) (size.x*0.9);
//        int height = (int) (size.y*0.7);
//
//        getWindow().getAttributes().width = width;
//        getWindow().getAttributes().height = height;
//
//
//        //UI 객체생성
////        txtText = (TextView)findViewById(R.id.txtText);
//
//        //데이터 가져오기
////        Intent intent = getIntent();
////        String data = intent.getStringExtra("data");
////        txtText.setText(data);
//    }
//
//    public void mOnClose(View v){
//        //데이터 전달하기
//        String never ="1";
////        Intent intent = new Intent();
////        intent.putExtra("result", "Close Popup");
////        setResult(RESULT_OK, intent);
//        SharedPreference.setAttribute(getApplicationContext(),"never",never);
//        //액티비티(팝업) 닫기
//        finish();
//    }
//
//    @Override
//    public void onBackPressed() {
//        //안드로이드 백버튼 막기
//    }
//}
