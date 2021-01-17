package co.kr.todayplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import co.kr.todayplay.fragment.CategoryFragment;
import co.kr.todayplay.fragment.CommunityFragment;
import co.kr.todayplay.fragment.HomeFragment;
import co.kr.todayplay.fragment.JournalFragment;
import co.kr.todayplay.fragment.ProfileFragment;
import co.kr.todayplay.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private Long mBackwait = 0L;
    private final HomeFragment homeFragment = new HomeFragment();// 선언해서 밑에서 작업할시 이동이 안돼는 현상이 나타나서 밑에 따로 선언해주었습니다.
    private final CategoryFragment categoryFragment = new CategoryFragment();
    private final SearchFragment searchFragment = new SearchFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();
    private final CommunityFragment communityFragment = new CommunityFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView main_bottomNavigationView = findViewById(R.id.main_bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
        main_bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.bottom_home:
                        transaction.replace(R.id.main_frameLayout, new HomeFragment()).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_category:
                        transaction.replace(R.id.main_frameLayout, new CategoryFragment()).commitAllowingStateLoss();
                        break;

                    case R.id.bottom_search:
                        transaction.replace(R.id.main_frameLayout, new SearchFragment()).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_community:
                        transaction.replace(R.id.main_frameLayout, new JournalFragment()).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_profile:
                        transaction.replace(R.id.main_frameLayout, new ProfileFragment()).commitAllowingStateLoss();
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