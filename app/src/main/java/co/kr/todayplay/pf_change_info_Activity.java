package co.kr.todayplay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.kr.todayplay.fragment.ProfileFragment;

public class pf_change_info_Activity extends Fragment {

    public pf_change_info_Activity(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.profile_info_change, container, false);
        ImageView info_to_proflie = rootView.findViewById(R.id.info_to_proflie);

        info_to_proflie.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ProfileFragment parentFrag = (ProfileFragment) pf_change_info_Activity.this.getParentFragment() ;
                parentFrag.BackToHome();
            }
        }));

        return rootView;
    }
}
