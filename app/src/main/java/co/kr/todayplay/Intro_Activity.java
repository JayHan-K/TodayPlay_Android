package co.kr.todayplay;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import java.util.Arrays;

import co.kr.todayplay.DBHelper.CrewDB.CrewDBHelper;
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;

public class Intro_Activity extends AppCompatActivity {


//    static final int PERMISSION_REQUEST_CODE = 1;
//    String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};
//    private File outputFile; //파일명까지 포함한 경로
//    private File path;//디렉토리경로

//    private boolean hasPermissions(String[] permissions) {
//        int res = 0;
//        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
//        for (String perms : permissions){
//            res = checkCallingOrSelfPermission(perms);
//            if (!(res == PackageManager.PERMISSION_GRANTED)){
//                //퍼미션 허가 안된 경우
//                return false;
//            }
//
//        }
//        //퍼미션이 허가된 경우
//        return true;
//    }


//    private void requestNecessaryPermissions(String[] permissions) {
//        //마시멜로( API 23 )이상에서 런타임 퍼미션(Runtime Permission) 요청
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
//        }
//    }

    VideoView videoView;

    //journal all DB
    JournalDBHelper journalDBHelper;
    String journal_all_jsonString;
    String all_journal_result_url = "http://211.174.237.197/request_journal_info/";
    JSONArray journal_all_jsonArray;

    //play all DB
    PlayDBHelper playDBHelper;
    String play_all_jsonString;
    String all_play_result_url = "http://211.174.237.197/request_play_info/";
    JSONArray play_all_jsonArray;

    //crew all DB
    CrewDBHelper crewDBHelper;
    String crew_all_jsonString;
    String all_crew_result_url = "http://211.174.237.197/all_crew_list/";
    JSONArray crew_all_jsonArray;

    public ImageView imgView;
    public ProgressBar progressBar;
    int cnt;

    GetFileName getPlayFileName = new GetFileName();
    GetFileName getJournalFileName = new GetFileName();
    GetFileName getFileFileName = new GetFileName();
    GetFileName getCrewFileName = new GetFileName();
    GetFileName getPlayJournalFileName = new GetFileName();
    UpdateJournalDB updateJournalDB = new UpdateJournalDB();
    UpdatePlayDB updatePlayDB = new UpdatePlayDB();
    UpdateCrewDB updateCrewDB = new UpdateCrewDB();
    DownloadFilesTask lastDownload;
    String userId;
    String nickname;


    @Override
    protected void onCreate(Bundle savedInstanceStare){
        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_intro);

        //userId를 받아옴
        userId = SharedPreference.getAttribute(getApplicationContext(),"userId");
        nickname =SharedPreference.getAttribute(getApplicationContext(),"nickname");

//        if (!hasPermissions(PERMISSIONS)) { //퍼미션 허가를 했었는지 여부를 확인
//            requestNecessaryPermissions(PERMISSIONS);//퍼미션 허가안되어 있다면 사용자에게 요청
//        } else {
//            //이미 사용자에게 퍼미션 허가를 받음.
//        }

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
        String[] play = {"play", "http://211.174.237.197/db_api/v1/resource_list/play"};

        getPlayFileName.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, play);

        //Journal 리소스 다운로드
        String[] journal = {"journal","http://211.174.237.197/db_api/v1/resource_list/journal/"};
        getJournalFileName.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, journal);

        //File 리소스 다운로드
        String[] file = {"file","http://211.174.237.197/db_api/v1/resource_list/file/"};
        getFileFileName.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,file);

        //Crew 리소스 다운로드
        String[] crew = {"crew","http://211.174.237.197/db_api/v1/resource_list/crew"};
        getCrewFileName.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,crew);

        //play_journal 리소스 다운로드
        String[] play_journal = {"play_journal","http://211.174.237.197/db_api/v1/resource_list/play_journal"};
        getPlayJournalFileName.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,play_journal);

        //Journal DB Update
        journalDBHelper = new JournalDBHelper(getApplicationContext(), "Journal.db", null, 1);
        updateJournalDB.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //Play DB Update
        playDBHelper = new PlayDBHelper(getApplicationContext(), "Play.db", null, 1);
        updatePlayDB.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //crew DB Update
        crewDBHelper = new CrewDBHelper(getApplicationContext(), "Crew.db", null, 1);
        updateCrewDB.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //Update 완료 체크
        if(getPlayJournalFileName.getStatus() == AsyncTask.Status.FINISHED && getCrewFileName.getStatus() == AsyncTask.Status.FINISHED && getPlayFileName.getStatus() == AsyncTask.Status.FINISHED && getFileFileName.getStatus() == AsyncTask.Status.FINISHED && getJournalFileName.getStatus() == AsyncTask.Status.FINISHED && updateJournalDB.getStatus() == AsyncTask.Status.FINISHED && updateCrewDB.getStatus()
         == AsyncTask.Status.FINISHED && updatePlayDB.getStatus() == AsyncTask.Status.FINISHED){
            Intent intent = new Intent (getApplicationContext(),MainActivity.class);
            if(userId == null){
//                        Intent intent = new Intent (getApplicationContext(),Login_Home_Activity.class);
//                        startActivity(intent);
//                        finish();
            }else{
                intent.putExtra("userId",userId);
            }
            startActivity(intent);
            finish();
        }

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
//            File path = new File(getFilesDir() + "/" + strings[0][0]);
            JSONObject jsonObject;
            try {
                //파일 주소 폴더명은 strings[0][0] 폴더가 존재하지 않으면 mkdirs로 만들어 준다.
//                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + strings[0][0]);
//                if(!dir.exists()){
//                    dir.mkdirs();
//                }
                File dir = getFilesDir();
                File path = dir;

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
                        String fileURL = "http://211.174.237.197/db_api/download/" + strings[0][0] + "/" + filename;
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
            String resource_group = "";
            if(list_size > 0){
                for(int i=0; i < list_size; i++){
                    final DownloadFilesTask downloadFilesTask = new DownloadFilesTask(Intro_Activity.this);
                    lastDownload = downloadFilesTask;
                    resource_group = download_list.get(i)[2];
                    downloadFilesTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, download_list.get(i)[0], download_list.get(i)[1], download_list.get(i)[2]);
                }
                /*
                cnt++;
                if(cnt==6){
                    Intent intent = new Intent (getApplicationContext(),Login_Home_Activity.class);
                    startActivity(intent);
                    finish();
                }
                */
                //Update 완료 체크
                if(getPlayJournalFileName.getStatus() == AsyncTask.Status.FINISHED && getCrewFileName.getStatus() == AsyncTask.Status.FINISHED && getPlayFileName.getStatus() == AsyncTask.Status.FINISHED && getFileFileName.getStatus() == AsyncTask.Status.FINISHED && getJournalFileName.getStatus() == AsyncTask.Status.FINISHED && updateJournalDB.getStatus() == AsyncTask.Status.FINISHED && updateCrewDB.getStatus()
                        == AsyncTask.Status.FINISHED && updatePlayDB.getStatus() == AsyncTask.Status.FINISHED){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    if(userId == null){
//                        Intent intent = new Intent (getApplicationContext(),Login_Home_Activity.class);
//                        startActivity(intent);
//                        finish();
                    }else{
                        intent.putExtra("userId",userId);
                    }
                    startActivity(intent);
                    finish();
                }

            }

            else {
//                progressBar.setVisibility(View.GONE);
//                /*
//                ArrayList<String> test_data = new ArrayList<>();
//                for(int i=0; i<play_filename_jsonArray.length(); i++){
//                    try {
//                        String filename = play_filename_jsonArray.get(i).toString();
//                        String imgpath = getFilesDir() + "/"  + filename;
//                        test_data.add(imgpath);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                recyclerView.setAdapter(new ImgTestAdapter(test_data));
//
/*
                cnt++;
                if(cnt==6){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
*/
                cnt = 10;
            }
            //Update 완료 체크
            Log.d("DownLoaded", "Finish " + resource_group + " files download");

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
//            mWakeLock.acquire(10*60*1000L /*10 minutes*/);
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
//                String path = getFilesDir() + File.separator + download_info[2];
//                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + download_info[2]);
                File dir = getFilesDir();
                File path = dir;
                File outputFile= new File(path, download_info[0].toString()); //파일명까지 포함함 경로의 File 객체 생성
                Log.d("outputFile Path",outputFile.getPath());
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
//                mWakeLock.release();
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
            if(this == lastDownload){
                cnt = 10;
                if(getPlayJournalFileName.getStatus() == AsyncTask.Status.FINISHED && getCrewFileName.getStatus() == AsyncTask.Status.FINISHED && getPlayFileName.getStatus() == AsyncTask.Status.FINISHED && getFileFileName.getStatus() == AsyncTask.Status.FINISHED && getJournalFileName.getStatus() == AsyncTask.Status.FINISHED && updateJournalDB.getStatus() == AsyncTask.Status.FINISHED && updateCrewDB.getStatus()
                        == AsyncTask.Status.FINISHED && updatePlayDB.getStatus() == AsyncTask.Status.FINISHED){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    if(userId == null){
//                        Intent intent = new Intent (getApplicationContext(),Login_Home_Activity.class);
//                        startActivity(intent);
//                        finish();
                    }else{
                        intent.putExtra("userId",userId);
                    }
                    startActivity(intent);
                    finish();
                }
            }
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
                        journalDBHelper.insert_journal(journal_id, journal_object.get("journal_title").toString(), journal_object.get("journal_subtitle").toString(), journal_object.get("journal_editor").toString(), journal_object.get("journal_category").toString(), 0, "", 0, journal_object.get("journal_relation_play").toString(), journal_object.get("journal_relation_journal").toString(), journal_object.get("journal_keyword").toString(), journal_object.get("journal_banner_img").toString(), journal_object.get("journal_thumbnail1_img").toString(), journal_object.get("journal_thumbnail2_img").toString(), journal_object.get("journal_file").toString());
                    }
                    else{
                        journalDBHelper.update_journal(journal_id, journal_object.get("journal_title").toString(), journal_object.get("journal_subtitle").toString(), journal_object.get("journal_editor").toString(), journal_object.get("journal_category").toString(), 0, "", 0, journal_object.get("journal_relation_play").toString(), journal_object.get("journal_relation_journal").toString(), journal_object.get("journal_keyword").toString(), journal_object.get("journal_banner_img").toString(), journal_object.get("journal_thumbnail1_img").toString(), journal_object.get("journal_thumbnail2_img").toString(), journal_object.get("journal_file").toString());
                    }
                }
                Log.d("journal_done?","journal_done");
/*
                cnt++;
                if(cnt==6){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
*/

            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Update 완료 체크
            Log.d("JournalUpdated", "Finish JournalUpdate" + " now cnt = " + cnt);
            if(cnt == 10 && getPlayJournalFileName.getStatus() == AsyncTask.Status.FINISHED && getPlayFileName.getStatus() == AsyncTask.Status.FINISHED && getFileFileName.getStatus() == AsyncTask.Status.FINISHED && getJournalFileName.getStatus() == Status.FINISHED && updateCrewDB.getStatus()
                    == AsyncTask.Status.FINISHED && updatePlayDB.getStatus() == AsyncTask.Status.FINISHED){
                Log.d("Finish", "All update Finished!!");
                Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                if(userId == null){
//                        Intent intent = new Intent (getApplicationContext(),Login_Home_Activity.class);
//                        startActivity(intent);
//                        finish();
                }else{
                    intent.putExtra("userId",userId);
                }
                startActivity(intent);
                finish();
            }
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
                    String play_crew = play_object.get("play_crew").toString();
                    if(!playDBHelper.isExistPlayID(play_id)){
                        playDBHelper.insert_Play(play_id, play_object.get("play_title").toString(), play_object.get("play_category").toString(), play_object.get("play_main_poster").toString(), play_object.get("play_main_journal_file").toString(), play_object.get("play_journal_thumbnail1_img").toString(), play_object.get("play_journal_thumbnail2_img").toString(), play_object.get("play_keyword").toString(), play_object.get("play_youtube_links").toString(), play_crew.replaceAll("'","\''"), 0,  play_object.get("relative_journal").toString(),  play_object.get("play_history").toString());
                    }
                    else{
                        playDBHelper.update_Play(play_id, play_object.get("play_title").toString(), play_object.get("play_category").toString(), play_object.get("play_main_poster").toString(), play_object.get("play_main_journal_file").toString(), play_object.get("play_journal_thumbnail1_img").toString(), play_object.get("play_journal_thumbnail2_img").toString(), play_object.get("play_keyword").toString(), play_object.get("play_youtube_links").toString(), play_crew.replaceAll("'","\''"), 0, play_object.get("relative_journal").toString(),  play_object.get("play_history").toString());
                    }
                }
                Log.d("play_done?","play_done");
/*
                cnt++;
                if(cnt==6){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
*/
            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Update 완료 체크
            Log.d("PlayUpdated", "Finish PlayUpdate" + " now cnt = " + cnt);

            if(cnt == 10 && getPlayJournalFileName.getStatus() == AsyncTask.Status.FINISHED && getCrewFileName.getStatus() == AsyncTask.Status.FINISHED && getPlayFileName.getStatus() == Status.FINISHED && getFileFileName.getStatus() == AsyncTask.Status.FINISHED && getJournalFileName.getStatus() == AsyncTask.Status.FINISHED && updateJournalDB.getStatus() == AsyncTask.Status.FINISHED && updateCrewDB.getStatus()
                    == AsyncTask.Status.FINISHED){
                Log.d("Finish", "All update Finished!!");
                Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                if(userId == null){
//                        Intent intent = new Intent (getApplicationContext(),Login_Home_Activity.class);
//                        startActivity(intent);
//                        finish();
                }else{
                    intent.putExtra("userId",userId);
                }
                startActivity(intent);
                finish();
            }
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

                    String crew_name =  crew_object.get("crew_name").toString();

                    if (!crewDBHelper.isExistCrewID(crew_id)) {
                        crewDBHelper.insert_crew(crew_id, crew_name.replaceAll("'","\''"), crew_object.get("crew_position").toString(), crew_object.get("crew_pic").toString());
                    } else{
                        crewDBHelper.update_crew(crew_id, crew_name.replaceAll("'","\''"), crew_object.get("crew_position").toString(), crew_object.get("crew_pic").toString());
                    }
                }
                Log.d("crew_done?","crew_done");
/*
                cnt++;
                if(cnt==6){
                    Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
*/
            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Update 완료 체크
            Log.d("CrewUpdated", "Finish CrewUpdate" + " now cnt = " + cnt);
            if(cnt == 10 && getPlayJournalFileName.getStatus() == AsyncTask.Status.FINISHED && getCrewFileName.getStatus() == AsyncTask.Status.FINISHED && getPlayFileName.getStatus() == AsyncTask.Status.FINISHED && getFileFileName.getStatus() == AsyncTask.Status.FINISHED && getJournalFileName.getStatus() == AsyncTask.Status.FINISHED && updateJournalDB.getStatus() == AsyncTask.Status.FINISHED && updatePlayDB.getStatus() == AsyncTask.Status.FINISHED){
                Log.d("Finish", "All update Finished!!");
                Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                if(userId == null){
//                        Intent intent = new Intent (getApplicationContext(),Login_Home_Activity.class);
//                        startActivity(intent);
//                        finish();
                }else{
                    intent.putExtra("userId",userId);
                }
                startActivity(intent);
                finish();
            }
        }
    }

    //-- UpdateCrewDB Asynctask End --\

//     if(userId == null){
//        Intent intent = new Intent (getApplicationContext(),Login_Home_Activity.class);
//        startActivity(intent);
//        finish();
//    }else{
//        Intent intent = new Intent (getApplicationContext(),MainActivity.class);
//        intent.putExtra("userId",userId);
//        startActivity(intent);
//        finish();
//    }



    //-- Pemission 관련 --

//    @Override
//    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){
//        switch(permsRequestCode){
//
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0) {
//                    boolean readAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                        if ( !readAccepted || !writeAccepted  )
//                        {
//                            showDialogforPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");
//                            return;
//                        }
//                    }
//                }
//                break;
//        }
//    }
//
//    private void showDialogforPermission(String msg) {
//
//        final AlertDialog.Builder myDialog = new AlertDialog.Builder(  Intro_Activity.this);
//        myDialog.setTitle("알림");
//        myDialog.setMessage(msg);
//        myDialog.setCancelable(false);
//        myDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface arg0, int arg1) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
//                }
//
//            }
//        });
//        myDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface arg0, int arg1) {
//                finish();
//            }
//        });
//        myDialog.show();
//    }

}
