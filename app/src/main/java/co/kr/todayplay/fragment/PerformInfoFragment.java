package co.kr.todayplay.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import co.kr.todayplay.BlurredImage;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformPagerAdapter;
import co.kr.todayplay.adapter.PerformTagAdapter;

public class PerformInfoFragment extends Fragment {
    RecyclerView tag_rv;
    TabLayout tabLayout;
    ViewPager viewPager;
    PerformPagerAdapter performPagerAdapter;
    ConstraintLayout poster;
    GridLayoutManager gridLayoutManager;
    public PerformInfoFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_perform_prac, container, false);
        tabLayout = (TabLayout)viewGroup.findViewById(R.id.tabLayout);
        tag_rv = (RecyclerView)viewGroup.findViewById(R.id.tag_rv);
        viewPager = (ViewPager)viewGroup.findViewById(R.id.viewPager);
        poster = (ConstraintLayout)viewGroup.findViewById(R.id.poster_part);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 7;

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.poster_sample16, options);
        Bitmap newImg = BlurredImage.fastblur(this.getContext(), image, 25);
        //Bitmap scaledImg = imageZoom(newImg,10);
        //Bitmap gradientImg = addGradient(newImg, Color.TRANSPARENT, Color.argb(0,37,37,37));
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), newImg);
        poster.setBackground(bitmapDrawable);

        ArrayList<String> data = new ArrayList<>();
        data.add("세계4대뮤지컬");
        data.add("로맨스");
        data.add("드라마");
        data.add("가면");
        data.add("대극장");
        data.add("세계4대뮤지컬");
        PerformTagAdapter performTagAdapter = new PerformTagAdapter(data);
        tag_rv.setAdapter(performTagAdapter);
        gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2, LinearLayoutManager.HORIZONTAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        tag_rv.setHasFixedSize(true);

        tag_rv.setLayoutManager(gridLayoutManager);

        tabLayout.addTab(tabLayout.newTab().setText("후기 분석"));
        tabLayout.addTab(tabLayout.newTab().setText("상세 정보"));
        tabLayout.addTab(tabLayout.newTab().setText("영상 자료"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        performPagerAdapter = new PerformPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(performPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                performPagerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }

        });

        return viewGroup;
    }

    public static Bitmap imageZoom(Bitmap bitmap, double maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] b = baos.toByteArray();
        double mid = (double) (b.length / 1024);
        double i = mid / maxSize;
        if (i > 1.0D) {
            bitmap = scaleWithWH(bitmap,
                    (double) bitmap.getWidth() / Math.sqrt(i),
                    (double) bitmap.getHeight() / Math.sqrt(i));
        }

        return bitmap;
    }

    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w != 0.0D && h != 0.0D && src != null) {
            int width = src.getWidth();
            int height = src.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidth = (float) (w / (double) width);
            float scaleHeight = (float) (h / (double) height);
            matrix.postScale(scaleWidth, scaleHeight);
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix,
                    true);
        } else {
            return src;
        }
    }

    public Bitmap addGradient(Bitmap src, int color1, int color2)
    {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,0,0,h, color1, color2, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0,0,w,h,paint);

        return result;
    }
}
