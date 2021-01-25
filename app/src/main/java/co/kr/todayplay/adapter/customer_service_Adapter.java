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

public class customer_service_Adapter extends BaseAdapter {
    private TextView titleTextView;

    private ArrayList<CV_Item> cv_items = new ArrayList<CV_Item>();

    public customer_service_Adapter(){

    }
    @Override
    public int getCount(){
        return cv_items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customer_service_listview,parent,false);
        }

        titleTextView = (TextView) convertView.findViewById(R.id.textView58);

        CV_Item cvItem = cv_items.get(position);
        titleTextView.setText(cvItem.getTitleStr());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return cv_items.get(position);
    }

    public void addItem(String title){
        CV_Item item = new CV_Item();
        item.setTitleStr(title);
        cv_items.add(item);
    }


}
