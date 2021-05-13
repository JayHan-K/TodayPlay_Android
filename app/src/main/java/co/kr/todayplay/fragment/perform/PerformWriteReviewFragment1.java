package co.kr.todayplay.fragment.perform;

import android.Manifest;
import android.app.Activity;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.HomeFragment;

import static android.app.Activity.RESULT_OK;

public class PerformWriteReviewFragment1 extends Fragment {
    int play_id = -1;
    int user_id = -1;
    private final int GALLERY_CODE=1112;
    Bitmap bitmap;
    String imagePath = "";

    Boolean etc_flag = false;
    Boolean receipt_flag = false;
    Boolean ticket_flag = false;
    String certification_type = "-1";
    ImageButton back_btn;
    Button save_btn;
    ConstraintLayout ticket_btn, receipt_btn, etc_btn;

    DialogFragment writeReviewDialogFragment1 = new WriteReviewDialogFragment1();
    DialogFragment writeReviewDialogFragment2 = new WriteReviewDialogFragment2();
    PerformWriteReviewFragment2 performWriteReviewFragment2 = new PerformWriteReviewFragment2();

    public PerformWriteReviewFragment1(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_review_certification,container,false);
        back_btn = (ImageButton) viewGroup.findViewById(R.id.back_btn);
        ticket_btn = (ConstraintLayout)viewGroup.findViewById(R.id.ticket_btn);
        receipt_btn = (ConstraintLayout)viewGroup.findViewById(R.id.receipt_btn);
        etc_btn = (ConstraintLayout)viewGroup.findViewById(R.id.etc_btn);
        save_btn = (Button)viewGroup.findViewById(R.id.save_btn);

        Bundle bundle = getArguments();
        showDialog(writeReviewDialogFragment1);
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id);
        }

        etc_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
        ticket_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
        etc_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
        save_btn.setBackgroundResource(R.drawable.rounded_border_4dp_e4e4e4);
        save_btn.setEnabled(false);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("pressed","pressed=");
                //HomeFragment homeFragment = new HomeFragment();
                //((MainActivity)view.getContext()).replaceFragmentHome(homeFragment);
                showDialog(writeReviewDialogFragment2);
            }
        });

        ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ticket_flag){
                    ticket_flag = false;
                    ticket_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
                    save_btn.setEnabled(false);
                    save_btn.setBackgroundResource(R.drawable.rounded_border_4dp_e4e4e4);
                }
                else{
                    ticket_flag = true;
                    etc_flag = false;
                    receipt_flag = false;
                    ticket_btn.setBackgroundResource(R.drawable.rounded_border_4dp_fff1a6);
                    etc_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
                    receipt_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
                    save_btn.setEnabled(true);
                    save_btn.setBackgroundResource(R.drawable.rounded_border_4dp_fff1a6);
                }

            }
        });

        etc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etc_flag){
                    etc_flag = false;
                    etc_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
                    save_btn.setEnabled(false);
                    save_btn.setBackgroundResource(R.drawable.rounded_border_4dp_e4e4e4);
                }
                else{
                    ticket_flag = false;
                    etc_flag = true;
                    receipt_flag = false;
                    ticket_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
                    etc_btn.setBackgroundResource(R.drawable.rounded_border_4dp_fff1a6);
                    receipt_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
                    save_btn.setEnabled(true);
                    save_btn.setBackgroundResource(R.drawable.rounded_border_4dp_fff1a6);
                }

            }
        });

        receipt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(receipt_flag){
                    receipt_flag = false;
                    receipt_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
                    save_btn.setEnabled(false);
                    save_btn.setBackgroundResource(R.drawable.rounded_border_4dp_e4e4e4);
                }
                else{
                    ticket_flag = false;
                    etc_flag = false;
                    receipt_flag = true;
                    ticket_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
                    etc_btn.setBackgroundResource(R.drawable.rounded_border_4dp_cccccc);
                    receipt_btn.setBackgroundResource(R.drawable.rounded_border_4dp_fff1a6);
                    save_btn.setEnabled(true);
                    save_btn.setBackgroundResource(R.drawable.rounded_border_4dp_fff1a6);
                }

            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ticket_flag) certification_type = "type1";
                else if(receipt_flag) certification_type = "type2";
                else if(etc_flag) certification_type = "type3";
                Log.d("ReviewFragment1", "save_btn is Clicked");
                checkSelfPermission();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                Log.d("DialogFragment", "onClicked");
                startActivityForResult(intent, GALLERY_CODE);
            }
        });

        return viewGroup;
    }

    void showDialog(DialogFragment dialogFragment){
        dialogFragment.setCancelable(false);
        dialogFragment.show(getParentFragmentManager(),"writeReviewDialogFragment");
    }

    public void checkSelfPermission() {
        String temp = "";
        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }
        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (!TextUtils.isEmpty(temp))
        {
            // 권한 요청
            ActivityCompat.requestPermissions(getActivity(), temp.trim().split(" "),1);
        } else {
            // 모두 허용 상태
            //Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
            Log.d("WriteReviewFragment1", "Checked Permission");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_CODE){
                sendPicture(data.getData());
                if(bitmap!=null)  {
                    Log.d("DialogFragment", "result bitmap = " + bitmap.toString() + " img_path = " + imagePath);
                    ((MainActivity)getActivity()).replaceFragment(performWriteReviewFragment2, play_id, certification_type, imagePath);

                }
                else{
                    Log.d("DialogFragment", "intent result bitmap = " + bitmap.toString() + " img_path = " + imagePath);
                    Toast.makeText(getActivity().getApplicationContext(), "인증이 필요합니다.", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void sendPicture(Uri imgUri) {
        imagePath = getRealPathFromURI(imgUri);// path 경로
        bitmap = BitmapFactory.decodeFile(imagePath);
        if(bitmap!=null)  Log.d("DialogFragment", "bitmap = " + bitmap.toString() + " img_path = " + imagePath);
        //경로를 통해 비트맵으로 전환
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }
}
