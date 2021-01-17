package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.kr.todayplay.R;

public class pf_change_info_Activity extends Fragment {


    public pf_change_info_Activity(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.profile_info_change, container, false);
        ImageView info_to_proflie = rootView.findViewById(R.id.info_to_proflie);

        final Button button_rewrite = rootView.findViewById(R.id.button_rewrite);
        final EditText rewrite_nick_name = rootView.findViewById(R.id.rewrite_nick_name);
        final Button button3 = rootView.findViewById(R.id.button3);
        final TextView textView24 = rootView.findViewById(R.id.textView24);

        button_rewrite.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rewrite_nick_name.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
                button_rewrite.setVisibility(View.GONE);
            }
        }));



        rewrite_nick_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                button3.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rewrite_nick_name.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button_rewrite.setVisibility(View.VISIBLE);
                        textView24.setText(rewrite_nick_name.getText().toString());
                    }
                }));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
