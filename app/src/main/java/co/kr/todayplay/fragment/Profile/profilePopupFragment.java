package co.kr.todayplay.fragment.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.kr.todayplay.Login_Home_Activity;
import co.kr.todayplay.R;

public class profilePopupFragment extends Fragment {
    public profilePopupFragment(){

    }
    ImageView imageView21;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.profile_popup_fragment, container, false);
        imageView21 = viewGroup.findViewById(R.id.imageView21);
        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getContext(), Login_Home_Activity.class);
                startActivity(intent);
            }
        });



        return viewGroup;
    }

}
