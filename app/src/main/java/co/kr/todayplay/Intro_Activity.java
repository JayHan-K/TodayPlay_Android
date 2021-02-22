package co.kr.todayplay;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

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

import co.kr.todayplay.DBHelper.CrewDB.CrewDBHelper;
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;

public class Intro_Activity extends AppCompatActivity {

    VideoView videoView;

    //journal all DB
    JournalDBHelper journalDBHelper;
    String journal_all_jsonString;
    String all_journal_result_url = "http://183.111.253.75/request_journal_info/";
    JSONArray journal_all_jsonArray;

    //play all DB
    PlayDBHelper playDBHelper;
    String play_all_jsonString;
    String all_play_result_url = "http://183.111.253.75/request_play_info/";
    JSONArray play_all_jsonArray;

    //crew all DB
    CrewDBHelper crewDBHelper;
    String crew_all_jsonString;
    String all_crew_result_url = "http://183.111.253.75/all_crew_list/";
    JSONArray crew_all_jsonArray;

    public ImageView imgView;
    public ProgressBar progressBar;
    int cnt;


    @Override
    protected void onCreate(Bundle savedInstanceStare){
        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_intro);
        cnt = 0;

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);

        videoView = (VideoView)findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.introvi);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
        progressBar.setVisibility(View.GONE);
        progressBar.setIndeterminate(true);

        //Play 리소스 다운로드
        GetFileName getPlayFileName = new GetFileName();
        String[] play = {"play", "http://183.111.253.75/db_api/v1/resource_list/play"};
        getPlayFileName.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, play);

        //Journal 리소스

        //Journal DB Update
        journalDBHelper = new JournalDBHelper(getApplicationContext(), "Journal.db", null, 1);
        UpdateJournalDB updateJournalDB = new UpdateJournalDB();
        updateJournalDB.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //Play DB Update
        playDBHelper = new PlayDBHelper(getApplicationContext(), "Play.db", null, 1);
        UpdatePlayDB updatePlayDB = new UpdatePlayDB();
        updatePlayDB.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //Play DB Update
        crewDBHelper = new CrewDBHelper(getApplicationContext(), "Crew.db", null, 1);
        UpdateCrewDB updateCrewDB = new UpdateCrewDB();
        updateCrewDB.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
////                videoView.setVisibility(View.GONE);
//            }
//        });

    }

    public static String getJsonFromServer(String url) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(10000);
        dc.setReadTimeout(10000);

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));

        // read the JSON results into a string
        String jsonResult = inputStream.readLine();
        return jsonResult;
    }

    //-- Resource 다운로드 AsyncTask Start --
    public class GetFileName extends AsyncTask<String[], Void, Integer> {
        ArrayList<String[]> download_list = new ArrayList<String[]>();
        @Override
        protected Integer doInBackground(String[]... strings) {
            JSONArray filename_jsonArray;
            String file_name_jsonString;
            File path = new File(getFilesDir() + "/" + strings[0][0]);
            JSONObject jsonObject;
            try {
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + strings[0][0]);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                file_name_jsonString = getJsonFromServer(strings[0][1]);
                Log.d("File_jsonString", strings[0][0] + " Folder = " + file_name_jsonString);
                jsonObject = new JSONObject(file_name_jsonString);
                Log.d("File_jsonObject", strings[0][0] + " Folder = " + jsonObject.toString());
                filename_jsonArray = jsonObject.getJSONArray("filename");
                for(int i=0; i<filename_jsonArray.length(); i++){
                    String filename = filename_jsonArray.get(i).toString();
                    System.out.println(filename_jsonArray.get(i));
                    File outputFile = new File(path, filename);
                    if(outputFile.exists()){
                        Log.d("already", strings[0][0] + "Folder: " + filename + "파일 존재");
                    }
                    else{
                        String fileURL = "http://183.111.253.75/db_api/download/" + strings[0][0] + "/" + filename;
                        download_list.add(new String[]{filename, fileURL, strings[0][0]});
                    }
                }

            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return download_list.size();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer list_size) {
            super.onPostExecute(list_size);
            if(list_size > 0){
                for(int i=0; i < list_size; i++){
                    final DownloadFilesTask downloadFilesTask = new DownloadFilesTask(Intro_Activity.this);
                    downloadFilesTask.execute(download_list.get(i)[0], download_list.get(i)[1], download_list.get(i)[2]);
                }
                cnt++;
                if(cnt==6){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
            else {
                progressBar.setVisibility(View.GONE);
                /*
                ArrayList<String> test_data = new ArrayList<>();
                for(int i=0; i<play_filename_jsonArray.length(); i++){
                    try {
                        String filename = play_filename_jsonArray.get(i).toString();
                        String imgpath = getFilesDir() + "/"  + filename;
                        test_data.add(imgpath);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setAdapter(new ImgTestAdapter(test_data));
                 */
                cnt++;
                if(cnt==6){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    public class DownloadFilesTask extends AsyncTask<Object, Void, Long> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadFilesTask(Context context){
            this.context = context;
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
                String path = getFilesDir() + File.separator + download_info[2];
                File outputFile= new File(path, download_info[0].toString()); //파일명까지 포함함 경로의 File 객체 생성
                if(outputFile == null){
                    Log.d("OutputFile", "Error!! ");
                }
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
                Toast.makeText(getApplicationContext(), "다운로드 완료되었습니다. 파일 크기=" + size.toString(), Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(), "다운로드 에러", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }
    //-- Resource 다운로드 AsyncTask End --

    //-- UpdateJournalDB AsyncTask End --
    public class UpdateJournalDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                journal_all_jsonString = getJsonFromServer(all_journal_result_url);
                Log.d("journal_all_jsonString", journal_all_jsonString);
                JSONObject jsonObject = new JSONObject(journal_all_jsonString);
                Log.d("Journal jsonObject", jsonObject.toString());
                journal_all_jsonArray = jsonObject.getJSONArray("journal");
                for(int i=0; i<journal_all_jsonArray.length(); i++){
                    JSONObject journal_object = (JSONObject) journal_all_jsonArray.get(i);
                    int journal_id = (int) journal_object.get("journal_id");
                    Log.d("JournalObject", "Object " + i + ": " + journal_all_jsonArray.get(i).toString());
                    Log.d("journal_id", "journal_id = " + journal_id);
                    if(!journalDBHelper.isExistJournalID(journal_id)){
                        journalDBHelper.insert_journal(journal_id, (String)journal_object.get("journal_title"), (String)journal_object.get("journal_subtitle"), (String)journal_object.get("journal_editor"), (String)journal_object.get("journal_category"), 0, "", 0, (String)journal_object.get("journal_relation_play"), (String)journal_object.get("journal_relation_journal"), (String)journal_object.get("journal_keyword"), (String)journal_object.get("journal_banner_img"), (String)journal_object.get("journal_thumbnail1_img"), (String)journal_object.get("journal_thumbnail2_img"), (String)journal_object.get("journal_file"));
                    }
                    else{
                        journalDBHelper.update_journal(journal_id, (String)journal_object.get("journal_title"), (String)journal_object.get("journal_subtitle"), (String)journal_object.get("journal_editor"), (String)journal_object.get("journal_category"), 0, "", 0, (String)journal_object.get("journal_relation_play"), (String)journal_object.get("journal_relation_journal"), (String)journal_object.get("journal_keyword"), (String)journal_object.get("journal_banner_img"), (String)journal_object.get("journal_thumbnail1_img"), (String)journal_object.get("journal_thumbnail2_img"), (String)journal_object.get("journal_file"));
                    }
                }
                Log.d("journal_done?","journal_done");

                cnt++;
                if(cnt==6){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    //-- UpdateJournalDB Asynctask End --

    //-- UpdatePlayDB AsyncTask End --
    public class UpdatePlayDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                play_all_jsonString = getJsonFromServer(all_play_result_url);
                Log.d("play_all_jsonString", play_all_jsonString);
                JSONObject jsonObject = new JSONObject(play_all_jsonString);
                Log.d("play jsonObject", jsonObject.toString());
                play_all_jsonArray = jsonObject.getJSONArray("play");
                for(int i=0; i<play_all_jsonArray.length(); i++){
                    JSONObject play_object = (JSONObject) play_all_jsonArray.get(i);
                    int play_id = (int) play_object.get("play_id");
                    Log.d("playObject", "playObject " + i + ": " + play_all_jsonArray.get(i).toString());
                    Log.d("play_id", "play_id = " + play_id);
                    if(!playDBHelper.isExistPlayID(play_id)){
                        playDBHelper.insert_Play(play_id, (String)play_object.get("play_title"), (String)play_object.get("play_category"), (String)play_object.get("play_main_poster"), (String)play_object.get("play_main_journal_file"), (String)play_object.get("play_journal_thumbnail1_img"), (String)play_object.get("play_journal_thumbnail2_img"), (String)play_object.get("play_keyword"), (String)play_object.get("play_youtube_links"), (String)play_object.get("play_crew"), 0, "",  (String)play_object.get("play_history"));
                    }
                    else{
                        playDBHelper.update_Play(play_id, (String)play_object.get("play_title"), (String)play_object.get("play_category"), (String)play_object.get("play_main_poster"), (String)play_object.get("play_main_journal_file"), (String)play_object.get("play_journal_thumbnail1_img"), (String)play_object.get("play_journal_thumbnail2_img"), (String)play_object.get("play_keyword"), (String)play_object.get("play_youtube_links"), (String)play_object.get("play_crew"), 0, "",  (String)play_object.get("play_history"));
                    }
                }
                Log.d("play_done?","play_done");
                cnt++;
                if(cnt==6){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }
    //-- UpdatePlayDB Asynctask End --

    //-- UpdateCrewDB AsyncTask End --
    public class UpdateCrewDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                crew_all_jsonString = getJsonFromServer(all_crew_result_url);
                Log.d("crew_all_jsonString", crew_all_jsonString);
                JSONObject jsonObject = new JSONObject(crew_all_jsonString);
                Log.d("crew jsonObject", jsonObject.toString());
                crew_all_jsonArray = jsonObject.getJSONArray("crew");
                for (int i = 0; i < crew_all_jsonArray.length(); i++) {
                    JSONObject crew_object = (JSONObject) crew_all_jsonArray.get(i);
                    int crew_id = (int) crew_object.get("crew_id");
                    Log.d("crewObject", "crewObject " + i + ": " + crew_all_jsonArray.get(i).toString());
                    Log.d("crew_id", "crew_id = " + crew_id);
                    if (!crewDBHelper.isExistCrewID(crew_id)) {
                        crewDBHelper.insert_crew(crew_id, (String) crew_object.get("crew_name"), (String) crew_object.get("crew_position"), (String) crew_object.get("crew_pic"));
                    } else{
                        crewDBHelper.update_crew(crew_id, (String) crew_object.get("crew_name"), (String) crew_object.get("crew_position"), (String) crew_object.get("crew_pic"));
                    }
                }
                Log.d("crew_done?","crew_done");
                cnt++;
                if(cnt==6){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }
    //-- UpdateCrewDB Asynctask End --

}
