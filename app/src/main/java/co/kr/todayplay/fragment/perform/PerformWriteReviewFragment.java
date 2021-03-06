package co.kr.todayplay.fragment.perform;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformReviewImageAdapter;

public class PerformWriteReviewFragment extends Fragment {
    EditText good_et, bad_et, tip_et;
    Button photo_btn, save_btn;
    RecyclerView photo_rv;
    ImageView good_more30_iv, bad_more30_iv;
    TextView good_num_text_tv, bad_num_text_tv, tip_num_text_tv;
    RatingBar star_rb;
    float rate;
    private static final int DIALOG_REQUEST_CODE = 1234;
    String imgpath="";
    Bitmap bitmap;

    public PerformWriteReviewFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_write_review,container,false);
        show();


        star_rb = (RatingBar)viewGroup.findViewById(R.id.star_rb);
        star_rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rate = ratingBar.getRating();
                Log.d("star", "onRatingChanged: rate = " + rate);
                if(rate > 0){
                    if(good_et.getText().toString().length() >= 30 && bad_et.getText().toString().length() >= 30){
                        save_btn.setEnabled(true);
                        save_btn.setBackgroundResource(R.drawable.rounded_border_4dp_ffe34e);
                    }
                    else{
                        save_btn.setEnabled(false);
                        save_btn.setBackgroundResource(R.drawable.round_border_4dp_fefefe);
                    }
                }
                else{
                    save_btn.setEnabled(false);
                    save_btn.setBackgroundResource(R.drawable.round_border_4dp_fefefe);
                }
            }
        });

        save_btn = (Button)viewGroup.findViewById(R.id.save_btn);
        save_btn.setEnabled(false);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformRecommendDialogFragment performRecommendDialogFragment = new PerformRecommendDialogFragment();
                performRecommendDialogFragment.show(getActivity().getSupportFragmentManager(),"PerformWriteReviewFragment");
            }
        });

        good_et = (EditText)viewGroup.findViewById(R.id.good_et);
        good_more30_iv = (ImageView)viewGroup.findViewById(R.id.good_more30_iv);
        good_num_text_tv = (TextView)viewGroup.findViewById(R.id.good_num_text_tv);
        good_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                good_num_text_tv.setText(good_et.getText().toString().length() + "/1000");
                if(good_et.getText().toString().length() >= 30){
                    good_more30_iv.setImageResource(R.drawable.write_review_more30_yellow);
                    if(bad_et.getText().toString().length() >= 30){
                        if(rate > 0){
                            save_btn.setEnabled(true);
                            save_btn.setBackgroundResource(R.drawable.rounded_border_4dp_ffe34e);
                        }
                        else{
                            save_btn.setEnabled(false);
                            save_btn.setBackgroundResource(R.drawable.round_border_4dp_fefefe);
                        }
                    }
                    else{
                        save_btn.setEnabled(false);
                        save_btn.setBackgroundResource(R.drawable.round_border_4dp_fefefe);
                    }
                }
                else{
                    save_btn.setEnabled(false);
                    save_btn.setBackgroundResource(R.drawable.round_border_4dp_fefefe);
                    good_more30_iv.setImageResource(R.drawable.write_review_more30_gray);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bad_et = (EditText)viewGroup.findViewById(R.id.bad_et);
        bad_num_text_tv = (TextView)viewGroup.findViewById(R.id.bad_num_text_tv);
        bad_more30_iv = (ImageView)viewGroup.findViewById(R.id.bad_more30_iv);
        bad_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                bad_num_text_tv.setText(bad_et.getText().toString().length() + "/1000");
                if(bad_et.getText().toString().length() >= 30){
                    bad_more30_iv.setImageResource(R.drawable.write_review_more30_yellow);
                    if(good_et.getText().toString().length() >= 30){
                        if(rate > 0){
                            save_btn.setEnabled(true);
                            save_btn.setBackgroundResource(R.drawable.rounded_border_4dp_ffe34e);
                        }
                        else{
                            save_btn.setEnabled(false);
                            save_btn.setBackgroundResource(R.drawable.round_border_4dp_fefefe);
                        }
                    }
                    else{
                        save_btn.setEnabled(false);
                        save_btn.setBackgroundResource(R.drawable.round_border_4dp_fefefe);
                    }
                }
                else{
                    save_btn.setEnabled(false);
                    save_btn.setBackgroundResource(R.drawable.round_border_4dp_fefefe);
                    bad_more30_iv.setImageResource(R.drawable.write_review_more30_gray);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tip_et = (EditText)viewGroup.findViewById(R.id.tip_et);
        tip_num_text_tv = (TextView)viewGroup.findViewById(R.id.tip_num_text_tv);
        tip_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                tip_num_text_tv.setText(tip_et.getText().toString().length() + "/1000");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        photo_btn = (Button)viewGroup.findViewById(R.id.photo_btn);
        photo_rv = (RecyclerView)viewGroup.findViewById(R.id.photo_rv);

        ArrayList<Integer> image_data = new ArrayList<>();
        image_data.add(R.drawable.poster_sample1);
        image_data.add(R.drawable.poster_sample2);
        image_data.add(R.drawable.poster_sample3);
        image_data.add(R.drawable.poster_sample4);
        image_data.add(R.drawable.poster_sample5);
        image_data.add(R.drawable.poster_sample6);

        final PerformReviewImageAdapter performReviewImageAdapter = new PerformReviewImageAdapter(image_data);
        photo_rv.setAdapter(performReviewImageAdapter);
        photo_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        return viewGroup;
    }
    void show(){
        DialogFragment certificatePhotoDialogFragment = new CertificatePhotoDialogFragment();
        certificatePhotoDialogFragment.setTargetFragment(this,
                DIALOG_REQUEST_CODE);
//        certificatePhotoDialogFragment.show(getActivity().getSupportFragmentManager(),"PerformWriteReviewFragment");
        certificatePhotoDialogFragment.show(getParentFragmentManager(),"dialog");
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.d("imgpath", "imgpath in fragment=" );
        Log.d("requestCode","requestCode="+requestCode+" "+DIALOG_REQUEST_CODE);
        Log.d("resultcode","resultcode="+resultCode+" "+Activity.RESULT_OK);
            if (requestCode == DIALOG_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    imgpath = data.getExtras().getString("img_path");
                    bitmap = (Bitmap)data.getParcelableExtra("bitmap");
                    Log.d("imgpath", "imgpath in fragment=" + imgpath);
                    Log.d("bitmap", "bitmap in fragment=" + bitmap.toString());
                }
            }
        }

}
