package co.kr.todayplay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login_Home_Activity extends AppCompatActivity {

    public static final int sub = 1001;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home);
        Button button6 = findViewById(R.id.button6);
        Button button4 = findViewById(R.id.button4);

        button4.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register_default.class);
                startActivityForResult(intent,sub);
            }
        }));

        button6.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login_Actitivty.class);
                startActivityForResult(intent,sub);
            }
        }));
    }
}
