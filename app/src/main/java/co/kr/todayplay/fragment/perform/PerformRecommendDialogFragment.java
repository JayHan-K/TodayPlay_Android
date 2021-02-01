package co.kr.todayplay.fragment.perform;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import co.kr.todayplay.R;

public class PerformRecommendDialogFragment extends DialogFragment {
    private Fragment parentFragment;
    private ConstraintLayout recommend_btn, not_recommend_btn;
    private ImageView recommend_hover_iv, not_recommend_hover_iv;
    boolean recommend_flag = false;

    public PerformRecommendDialogFragment(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_up_recommend_perform,null);
        builder.setView(view);

        recommend_hover_iv = (ImageView)view.findViewById(R.id.recomend_hover_iv);
        recommend_hover_iv.setImageResource(R.drawable.write_review_recomend_icon_gray);

        not_recommend_hover_iv = (ImageView)view.findViewById(R.id.not_recomend_hover_iv);
        not_recommend_hover_iv.setImageResource(R.drawable.write_review_not_recomend_icon_gray);

        recommend_btn = (ConstraintLayout)view.findViewById(R.id.recommend_btn);
        not_recommend_btn = (ConstraintLayout)view.findViewById(R.id.not_recommend_btn);
        not_recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recommend_flag = false;
                not_recommend_hover_iv.setImageResource(R.drawable.write_review_not_recomend_icon_yellow);
            }
        });

        recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recommend_flag = true;
                recommend_hover_iv.setImageResource(R.drawable.write_review_recomend_icon_yellow);
            }

        });

        return builder.create();
    }
}
