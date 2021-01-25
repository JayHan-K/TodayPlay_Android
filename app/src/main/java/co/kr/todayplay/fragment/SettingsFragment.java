package co.kr.todayplay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import co.kr.todayplay.Customer_Service_Activity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.SettingsListAdapter;

public class SettingsFragment extends Fragment {
    ListView mListView;

    public SettingsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_settings,container,false);
        mListView = (ListView)rootView.findViewById(R.id.listview);
        Button set_to_profile = (Button)rootView.findViewById(R.id.login_go_back5);
        dataSetting();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        Intent intent = new Intent(getContext(), Customer_Service_Activity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        set_to_profile.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ProfileFragment parentFrag = (ProfileFragment) SettingsFragment.this.getParentFragment() ;
                parentFrag.BackToHome();
            }
        }));
        return rootView;
    }

    private void dataSetting(){
        SettingsListAdapter adapter = new SettingsListAdapter();
        adapter.addItem("공지사항");
        adapter.addItem("고객센터");
        adapter.addItem("버전정보");
        adapter.addItem("개인정보 처리 방침");
        adapter.addItem("이용약관");
        adapter.addItem("로그아웃");
        mListView.setAdapter(adapter);
    }
}
