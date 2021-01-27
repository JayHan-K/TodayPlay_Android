package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.object.CV_Item;

public class SettingsListAdapter extends BaseAdapter {
    private ArrayList<CV_Item> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public CV_Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.settings_list,viewGroup,false);
        }
        TextView tv_name = (TextView)view.findViewById(R.id.textView64);
        CV_Item cvItem =  getItem(i);

        tv_name.setText(cvItem.getTitleStr());

        return view;
    }

    public void addItem(String name){
        CV_Item mItem = new CV_Item();
        mItem.setTitleStr(name);
        mItems.add(mItem);
    }
}
