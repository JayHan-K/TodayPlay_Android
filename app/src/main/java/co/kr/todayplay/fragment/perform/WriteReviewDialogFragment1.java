package co.kr.todayplay.fragment.perform;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.HomeFragment;

import static android.app.Activity.RESULT_OK;


public class WriteReviewDialogFragment1 extends DialogFragment {
    Button cancel_btn, write_btn;

    public WriteReviewDialogFragment1(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_write_review1,null);
        cancel_btn = (Button)view.findViewById(R.id.cancel_button);
        write_btn = (Button)view.findViewById(R.id.write_btn);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                //BackStack 처리 필요
                Intent intent = new Intent(getContext(), MainActivity.class);
                MainActivity mainActivity = (MainActivity)MainActivity.MainActivity;
                mainActivity.finish();
                startActivity(intent);
                dismiss();
                 */
                HomeFragment homeFragment = new HomeFragment();
                ((MainActivity)view.getContext()).replaceFragmentHome(homeFragment);
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
