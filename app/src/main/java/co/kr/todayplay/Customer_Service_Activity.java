package co.kr.todayplay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import co.kr.todayplay.adapter.customer_service_Adapter;
import co.kr.todayplay.fragment.ProfileFragment;

public class Customer_Service_Activity extends AppCompatActivity {
    private ListView service_lv;
    private customer_service_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        adapter = new customer_service_Adapter();
        Button back_profile = (Button)findViewById(R.id.back_profile);
        back_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        service_lv = (ListView) findViewById(R.id.service_lv);
        service_lv.setAdapter(adapter);
        service_lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position,long id){

                switch (position){
                    case 0:
                        Intent intent = new Intent(Customer_Service_Activity.this,Activity_FAQ.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(Customer_Service_Activity.this,Activity_suggest.class);
                        startActivity(intent1);
                        break;
                }
            }
        });

        adapter.addItem("FAQ");
        adapter.addItem("문의/제안하기");
    }
}
