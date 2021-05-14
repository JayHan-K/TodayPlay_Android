package co.kr.todayplay.fragment.perform;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;


public class WriteReviewDialogFragment2 extends DialogFragment {
    Button cancel_btn, write_btn;

    public WriteReviewDialogFragment2(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_write_review2,null);
        cancel_btn = (Button)view.findViewById(R.id.cancel_button);
        write_btn = (Button)view.findViewById(R.id.write_btn);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BackStack 처리 필요
                Intent intent = new Intent(getContext(), MainActivity.class);
                MainActivity mainActivity = (MainActivity)MainActivity.MainActivity;
                mainActivity.finish();
                startActivity(intent);
                dismiss();
            }
        });
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }


}
