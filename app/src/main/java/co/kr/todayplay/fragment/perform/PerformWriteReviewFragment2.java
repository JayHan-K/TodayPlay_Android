package co.kr.todayplay.fragment.perform;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformReviewImageAdapter;
import co.kr.todayplay.fragment.HomeFragment;
import co.kr.todayplay.object.Sim_uri;

import static android.app.Activity.RESULT_OK;

public class PerformWriteReviewFragment2 extends Fragment {
    EditText good_et, bad_et, tip_et;
    Button photo_btn, save_btn;
    ImageButton back_btn;
    RecyclerView photo_rv;
    ImageView good_more30_iv, bad_more30_iv, photo_icon;
    TextView good_num_text_tv, bad_num_text_tv, tip_num_text_tv, photo_tv;
    RatingBar star_rb;
    ArrayList<Uri> images ;

    DialogFragment writeReviewDialogFragment2 = new WriteReviewDialogFragment2();

    float rate;
    private static final int DIALOG_REQUEST_CODE = 1234;
    Bitmap bitmap;
    int CODE_ALBUM_REQUEST = 111;

    int play_id = -1;
    int user_id = -1;
    String certification_type = "-1";
    String certification_imgpath = "";
    String review_certified_pic = "";
    String[] review_pic  = {"", "", ""};

    public PerformWriteReviewFragment2(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_write_review,container,false);
        Bundle bundle = getArguments();
        Log.d("WriteReview2", "onCreateView: ");
        if(bundle != null){
//            images = (ArrayList<Sim_uri>)bundle.getSerializable("image");
//            images = (ArrayList<Sim_uri>)getArguments().get("image");
            images =bundle.getParcelableArrayList("image");
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            certification_type = bundle.getString("certification_type");
            certification_imgpath = bundle.getString("certification_imgpath");
            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id + " | certification_type = " + certification_type + " certification_imgpath = " + certification_imgpath);
            Log.d("images Size = ",Integer.toString(images.size()));
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
                //수정
                bundle1.putString("review_pic1", review_pic[0]);
                bundle1.putString("review_pic2", review_pic[1]);
                bundle1.putString("review_pic3", review_pic[2]);
                Log.d("WriteReview2", "review_pic1 = " + review_pic[0] + " review_pic2 = " + review_pic[1] + "review_pic3 = " + review_pic[2]);
                bundle1.putString("certification_type", certification_type);
                bundle1.putString("certification_imgpath", certification_imgpath);
                bundle1.putInt("num_of_star", (int)rate);
                bundle1.putParcelableArrayList("images",images);
                performRecommendDialogFragment.setArguments(bundle1);
                performRecommendDialogFragment.setCancelable(false);
                performRecommendDialogFragment.show(getActivity().getSupportFragmentManager(),"PerformWriteReviewFragment");
                Log.d("images_size",Integer.toString(images.size()));
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
                /*
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                intent.putExtra(intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "사진 애플리케이션을 선택해주세요."), CODE_ALBUM_REQUEST);
            */


                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "사진 애플리케이션을 선택해주세요."), CODE_ALBUM_REQUEST);


            }
        });


        return viewGroup;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("WriteReviewFragment2", "onActivityResult: ");
        //갤러리 이미지 가져오기
        if(requestCode == CODE_ALBUM_REQUEST && resultCode == RESULT_OK && data != null && data.getClipData() != null){
            ArrayList<Uri> uriList = new ArrayList<>();
            //review_pic 초기화
            for(int i=0; i<review_pic.length; i++){
                review_pic[i] = "";
            }
            // 리사이클러뷰 초기화
            final PerformReviewImageAdapter performReviewImageAdapter = new PerformReviewImageAdapter(uriList, getContext());
            photo_rv.setAdapter(performReviewImageAdapter);
            photo_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            //Log.d("get_data",data.getData().toString());
            Log.d("data_ClipData",data.getClipData().toString());
            if(data.getClipData() != null){
                ClipData clipData = data.getClipData();
                Log.d("WriteReviewFragment2", "picked photo num = " + clipData.getItemCount());
                if(clipData.getItemCount() > 3){
                    Toast.makeText(getActivity().getApplicationContext(), "사진은 최대 3개까지 선택 가능 합니다.", Toast.LENGTH_SHORT).show();
                    for(int i =1;i<images.size();i++){
                        images.remove(i);
                    }
                    Log.d("images_Size",Integer.toString(images.size()));
                    uriList.clear();
                    return;
                }else if(clipData.getItemCount() == 1){
                    Uri filePath = clipData.getItemAt(0).getUri();
                    data.getData();
                    review_pic[0] = getRealPathFromUri(filePath);
                    Log.d("WriteReviewFragment2", "Uri = " + clipData.getItemAt(0).getUri());
                    images.add(filePath);
                    Log.d("images_confirm",images.get(1).toString());
                    uriList.add(filePath);
                }else if(clipData.getItemCount() > 1 && clipData.getItemCount() <= 3){
                    for(int i=0; i<clipData.getItemCount(); i++){
                        images.add(clipData.getItemAt(i).getUri());
                        uriList.add(clipData.getItemAt(i).getUri());
                        review_pic[i] = getRealPathFromUri(clipData.getItemAt(i).getUri());
                        Log.d("WriteReviewFragment2", "Uri " + i + " = " + clipData.getItemAt(i).getUri());
                        Log.d("images_confirm",images.get(i).toString());
                    }
                }
                performReviewImageAdapter.notifyDataSetChanged();
            }
            else if(data.getData() != null){
                review_pic[0] = data.getData().toString();
                Log.d("WriteReviewFragment2", "Uri = " + data.getData());
                images.add(data.getData());
                uriList.add(data.getData());
                performReviewImageAdapter.notifyDataSetChanged();
            }

        }
    }

    //Uri를 절대경로로 바꿔서 리턴해주는 함수
    String getRealPathFromUri(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getActivity().getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    void showDialog(DialogFragment dialogFragment){
        dialogFragment.setCancelable(false);
        dialogFragment.show(getParentFragmentManager(),"writeReviewDialogFragment");
    }

}
