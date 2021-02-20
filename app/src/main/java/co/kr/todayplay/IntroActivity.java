package co.kr.todayplay;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.adapter.ImgTestAdapter;
import co.kr.todayplay.adapter.PerformDetailJournalAdapter;

public class IntroActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String url = "http://183.111.253.75/db_api/v1/resource_list/play/";
    String jsonString;
    public ImageView imgView;
    public ProgressBar progressBar;
    File outputFile;
    File path;
    JSONArray jsonArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        recyclerView = (RecyclerView)findViewById(R.id.img_test_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        progressBar.setIndeterminate(true);
        path = getFilesDir();
        GetFileName getFileName = new GetFileName();
        getFileName.execute();

    }
    public static String getJsonFromServer(String url) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(5000);
        dc.setReadTimeout(5000);

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));

        // read the JSON results into a string
        String jsonResult = inputStream.readLine();
        return jsonResult;
    }

    public class GetFileName extends AsyncTask<Void, Void, Integer>{
        ArrayList<String[]> download_list = new ArrayList<String[]>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                jsonString = getJsonFromServer(url);
                Log.d("jsonString", jsonString);
                JSONObject jsonObject = new JSONObject(jsonString);
                //JSONParser parser = new JSONParser();
                //Object obj = parser.parse(jsonString);
                Log.d("jsonObject", jsonObject.toString());
                jsonArray = jsonObject.getJSONArray("filename");
                for(int i=0; i<jsonArray.length(); i++){
                    String filename = jsonArray.get(i).toString();
                    System.out.println(jsonArray.get(i));
                    outputFile = new File(path, filename);
                    if(outputFile.exists()){
                        Log.d("already", "doInBackground: " + filename + "파일 존재");
                    }
                    else{
                        String fileURL = "http://183.111.253.75/db_api/download/play/"+filename;
                        download_list.add(new String[]{filename, fileURL});
                    }
                }

            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return download_list.size();
        }

        @Override
        protected void onPostExecute(Integer list_size) {
            super.onPostExecute(list_size);
            if(list_size > 0){
                for(int i=0; i < list_size; i++){
                    final DownloadFilesTask downloadFilesTask = new DownloadFilesTask(IntroActivity.this);
                    downloadFilesTask.execute(download_list.get(i)[0], download_list.get(i)[1]);
                }
            }
            else {
                progressBar.setVisibility(View.GONE);
                ArrayList<String> test_data = new ArrayList<>();
                for(int i=0; i<jsonArray.length(); i++){
                    try {
                        String filename = jsonArray.get(i).toString();
                        String imgpath = getFilesDir() + "/"  + filename;
                        test_data.add(imgpath);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recyclerView.setAdapter(new ImgTestAdapter(test_data));
            }
        }
    }

    public class DownloadFilesTask extends AsyncTask<Object, Void, Long> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadFilesTask(Context context){
            this.context =context;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,getClass().getName());
            mWakeLock.acquire();
        }

        @Override
        protected Long doInBackground(Object... download_info) {
            int count;
            long FileSize = -1;
            InputStream input = null;
            OutputStream output = null;
            URLConnection connection = null;

            try{
                URL url = new URL(download_info[1].toString());
                connection = url.openConnection();
                connection.connect();

                FileSize = connection.getContentLength();
                input = new BufferedInputStream(url.openStream(),8192);
                path = getFilesDir();
                outputFile= new File(path, download_info[0].toString()); //파일명까지 포함함 경로의 File 객체 생성

                // SD카드에 저장하기 위한 Output stream
                output = new FileOutputStream(outputFile);

                byte data[] = new byte[1024];
                long downloadedSize = 0;
                while ((count = input.read(data)) != -1) {
                    //사용자가 BACK 버튼 누르면 취소가능
                    if (isCancelled()) {
                        input.close();
                        return Long.valueOf(-1);
                    }
                    downloadedSize += count;
                    if (FileSize > 0) {
                        float per = ((float)downloadedSize/FileSize) * 100;
                        String str = "Downloaded " + downloadedSize + "KB / " + FileSize + "KB (" + (int)per + "%)";
                    }
                    //파일에 데이터를 기록합니다.
                    output.write(data, 0, count);
                }
                // Flush output
                output.flush();

                // Close streams
                output.close();
                input.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                mWakeLock.release();
            }
            return FileSize;
        }

        @Override
        protected void onPostExecute(Long size) { //5
            super.onPostExecute(size);
            if ( size > 0) {
                Toast.makeText(getApplicationContext(), "다운로드 완료되었습니다. 파일 크기=" + size.toString(), Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(getApplicationContext(), "다운로드 에러", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

}
