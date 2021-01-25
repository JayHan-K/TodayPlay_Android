package co.kr.todayplay.fragment.faq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.customer_service_Adapter;

public class faq_event extends Fragment {
    private ListView faq_list;
    private customer_service_Adapter adapter;

   public faq_event(){}

   @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
       ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.faq_related,container,false);
       adapter = new customer_service_Adapter();

       faq_list = viewGroup.findViewById(R.id.faq_list);
       faq_list.setAdapter(adapter);

       adapter.addItem("커뮤니티 가이드라인");
       adapter.addItem("오늘의 공연 운영정책");
       adapter.addItem("오늘의 공연 광고 운영정책");
       adapter.addItem("신고는 어떻게 하나요?");
       adapter.addItem("에디터 참여는 어떻게 하나요?");
       adapter.addItem("궁금한 공연 상식이나 고민을 요청해도 되나요?");

       return viewGroup;

    }

}
