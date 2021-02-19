package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.kr.todayplay.R;

public class AdapterSpinner2 extends BaseAdapter {
    Context mContext;
    List<String> Data;
    LayoutInflater Inflater;

    public AdapterSpinner2(Context context, List<String> data){
        this.mContext = context;
        this.Data = data;
        Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
        if(Data!=null) return Data.size();
        else return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = Inflater.inflate(R.layout.spinner_custom2,parent,false);
        }

        if(Data!=null){
            String text = Data.get(position);
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) { //클릭 후 보여지는 레이아웃
        if(convertView==null){
            convertView = Inflater.inflate(R.layout.spinner_getview2, parent, false);
        }

        String text = Data.get(position);
        ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
