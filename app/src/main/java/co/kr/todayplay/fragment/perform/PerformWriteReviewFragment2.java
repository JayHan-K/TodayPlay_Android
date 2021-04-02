package co.kr.todayplay.fragment.perform;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformReviewImageAdapter;
import co.kr.todayplay.fragment.HomeFragment;

import static android.app.Activity.RESULT_OK;

public class PerformWriteReviewFragment2 extends Fragment {
    EditText good_et, bad_et, tip_et;
    Button photo_btn, save_btn;
    ImageButton back_btn;
    RecyclerView photo_rv;
    ImageView good_more30_iv, bad_more30_iv, photo_icon;
    TextView good_num_text_tv, bad_num_text_tv, tip_num_text_tv, photo_tv;
    RatingBar star_rb;

    DialogFragment writeReviewDialogFragment2 = new WriteReviewDialogFragment2();

    float rate;
    private static final int DIALOG_REQUEST_CODE = 1234;
    Bitmap bitmap;
    int CODE_ALBUM_REQUEST = 111;

    int play_id = -1;
    int user_id = -1;
    int certification_type = -1;
    String certification_imgpath = "";
    Bitmap certification_bitmap;

    public PerformWriteReviewFragment2(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_write_review,container,false);
        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            certification_type = bundle.getInt("certification_type");
            certification_imgpath = bundle.getString("certification_imgpath");
            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id + " | certification_type = " + certification_type + " certification_imgpath = " + certification_imgpath);
        }
        back_btn = (ImageButton) viewGroup.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(writeReviewDialogFragment2);
            }
        });

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
                Bundle bundle1 = new Bundle();
                bundle1.putInt("play_id", play_id);
                bundle1.putInt("user_id", user_id);
                bundle1.putString("review_good", good_et.getText().toString());
                bundle1.putString("review_bad", bad_et.getText().toString());
                bundle1.putString("review_tip", tip_et.getText().toString());
                bundle1.putString("certification_imgpath", certification_imgpath);
                bundle1.putInt("num_of_star", (int)rate);
                performRecommendDialogFragment.setArguments(bundle1);
                performRecommendDialogFragment.setCancelable(false);
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
        photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                intent.putExtra(intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), CODE_ALBUM_REQUEST);
            }
        });


        return viewGroup;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //갤러리 이미지 가져오기
        if(requestCode == CODE_ALBUM_REQUEST && resultCode == RESULT_OK && data != null){
            ArrayList<Uri> uriList = new ArrayList<>();
            if(data.getClipData() != null){
                ClipData clipData = data.getClipData();
                Log.d("WriteReviewFragment2", "picked photo num = " + clipData.getItemCount());
                if(clipData.getItemCount() > 10){
                    Toast.makeText(getActivity().getApplicationContext(), "사진은 최대 10개까지 선택 가능 합니다.", Toast.LENGTH_SHORT).show();
                    uriList.clear();
                    return;
                }else if(clipData.getItemCount() == 1){
                    Uri filePath = clipData.getItemAt(0).getUri();
                    Log.d("WriteReviewFragment2", "Uri = " + clipData.getItemAt(0).getUri());
                    uriList.add(filePath);
                }else if(clipData.getItemCount() > 1 && clipData.getItemCount() <= 10){
                    for(int i=0; i<clipData.getItemCount(); i++){
                        uriList.add(clipData.getItemAt(i).getUri());
                        Log.d("WriteReviewFragment2", "Uri " + i + " = " + clipData.getItemAt(i).getUri());
                    }
                }
            }
            // 리사이클러뷰에 띄우기
            final PerformReviewImageAdapter performReviewImageAdapter = new PerformReviewImageAdapter(uriList, getContext());
            photo_rv.setAdapter(performReviewImageAdapter);
            photo_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        }
    }

    void showDialog(DialogFragment dialogFragment){
        dialogFragment.setCancelable(false);
        dialogFragment.show(getParentFragmentManager(),"writeReviewDialogFragment");
    }

}