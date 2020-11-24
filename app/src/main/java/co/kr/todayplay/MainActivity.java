package co.kr.todayplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import co.kr.todayplay.fragment.HomeFragment;
import co.kr.todayplay.fragment.JournalFragment;

public class MainActivity extends AppCompatActivity {

    private Long mBackwait = 0L;
    private BottomNavigationView main_bottomNavigationView;
    private HomeFragment homeFragment = new HomeFragment();
    private JournalFragment journalFragment = new JournalFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_bottomNavigationView = (BottomNavigationView)findViewById(R.id.main_bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
        main_bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.bottom_home:
                        transaction.replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_category:
                        transaction.replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
                        break;

                    case R.id.bottom_search:
                        transaction.replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_community:
                        transaction.replace(R.id.main_frameLayout, journalFragment).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_profile:
                        transaction.replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(System.currentTimeMillis() - mBackwait >= 2000){
            mBackwait = System.currentTimeMillis();
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            finish();
        }

    }
}