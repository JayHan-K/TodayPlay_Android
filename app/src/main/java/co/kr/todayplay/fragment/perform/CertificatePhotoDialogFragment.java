package co.kr.todayplay.fragment.perform;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
//import kotlinx.coroutines.channels.Send;

import static android.app.Activity.RESULT_OK;

public class CertificatePhotoDialogFragment extends DialogFragment {
    private Fragment parentFragment;
    private final int GALLERY_CODE=1112;
    Bitmap bitmap;
    String imagePath = "";

    public CertificatePhotoDialogFragment (){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_up_certificate_photo,null);
        checkSelfPermission();
        Button upload_btn = (Button)view.findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //권한 체크
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                Log.d("DialogFragment", "onClicked");
                startActivityForResult(intent, GALLERY_CODE);


            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    //권한에 대한 응답이 있을때 작동하는 함수
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if(requestCode == 1){
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    Log.d("MainActivity","권한 허용 : " + permissions[i]);
                }
            }
        }
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
            Log.d("DialogFragment", "Checked Permission");
        }
    }

    private void sendResult() {
        Log.d("started","sendResult");

//        ((MainActivity)getActivity()).onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_CODE){
                sendPicture(data.getData());
                if(bitmap!=null)  Log.d("DialogFragment", "result bitmap = " + bitmap.toString() + " img_path = " + imagePath);
                Log.d("DialogFragment", "intent result bitmap = " + bitmap.toString() + " img_path = " + imagePath);
                Intent data2 = new Intent();
                data2.putExtra("img_path", imagePath);
                data2.putExtra("bitmap",bitmap);
                Log.d("img_path","img_path dialog="+imagePath);
                getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, data2);
                dismiss();
//                    sendResult();


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
