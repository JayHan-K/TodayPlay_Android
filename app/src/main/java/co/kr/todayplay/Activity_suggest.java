package co.kr.todayplay;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import co.kr.todayplay.adapter.AdapterSpinner;

public class Activity_suggest extends AppCompatActivity {

    private Spinner spinner2;
    String suggest_input = null;
    ArrayList<String> arrayList;
    AdapterSpinner adapterSpinner;
    Button button8;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        final Button login_btn2 = findViewById(R.id.login_btn2);

        final EditText suggest = findViewById(R.id.editText3);
        ConstraintLayout linearlayout26 = findViewById(R.id.linearLayout26);
        final InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        linearlayout26.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                suggest.clearFocus();
                imm.hideSoftInputFromWindow(suggest.getWindowToken(),0);
                return false;
            }
        });

        login_btn2.setEnabled(false);
        suggest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String suggest_st = suggest.getText().toString();
                suggest_input = suggest_st;
                if(suggest_st != null){
                    login_btn2.setEnabled(true);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        arrayList = new ArrayList<>();
        arrayList.add("이용 문의");
        arrayList.add("이벤트 문의");
        arrayList.add("기타 문의");
        arrayList.add("제안하기");
        arrayList.add("오류사항");

        adapterSpinner = new AdapterSpinner(this,arrayList);

        spinner2 = (Spinner)findViewById(R.id.spinner);
        spinner2.setAdapter(adapterSpinner);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button8= (Button)findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }



}
