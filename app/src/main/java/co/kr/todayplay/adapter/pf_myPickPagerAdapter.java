package co.kr.todayplay.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import co.kr.todayplay.fragment.Profile.ProfileIng;
import co.kr.todayplay.fragment.category.CategoryCurrent;
import co.kr.todayplay.fragment.category.CategoryMusical;
import co.kr.todayplay.fragment.category.CategoryPlay;
import co.kr.todayplay.fragment.category.CategoryTotal;


public class pf_myPickPagerAdapter extends FragmentStateAdapter {
    int num;
    int user_id;

    public pf_myPickPagerAdapter(Fragment fm, int num, int user_id){
        super(fm);
        this.num = num;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("Bundle result", "pf_myPickPagerAdapter user_id: " + user_id);
        switch (position){
            case 0:
                ProfileIng tab1 = new ProfileIng(user_id, 0);
                return tab1;

            case 1:
                ProfileIng tab2 = new ProfileIng(user_id, 1);
                return tab2;

            case 2:
                ProfileIng tab3 = new ProfileIng(user_id, 2);
                return tab3;

            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return num;
    }



}

