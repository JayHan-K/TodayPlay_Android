package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.object.PlayModel;

public class PlayCoverflowAdapter extends BaseAdapter {

    private ArrayList<PlayModel> mData = new ArrayList<>(0);
    private Context mContext;

    public PlayCoverflowAdapter(Context context){mContext = context;}
    public void setData(ArrayList<PlayModel> data){mData = data;}


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if(rowView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.play_row,null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)rowView.findViewById(R.id.pr_im);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.image.setImageResource(mData.get(position).imageResId);
        return rowView;
    }
    static class ViewHolder{
        public ImageView image;
    }
}
