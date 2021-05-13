package co.kr.todayplay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LogOutPopupFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG_EVENT_DIALOG = "dialog_event";
    Point size;
    Button logout;

    public LogOutPopupFragment() {
    }

    public static LogOutPopupFragment getInstance() {
        LogOutPopupFragment e = new LogOutPopupFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.logout_popup_fragment, container);
        logout = v.findViewById(R.id.logout);
        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreference.removeAttribte(getContext(), "userId");
                Intent intent5 = new Intent(getContext(), Login_Home_Activity.class);
                startActivity(intent5);
                getActivity().finish();
            }
        });
        return v;
    }
    @Override
    public void onResume() {
        int width = (int) (size.x*0.9);
        int height = (int) (size.y*0.3);
        getDialog().getWindow().setLayout(width,height);
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }
}
