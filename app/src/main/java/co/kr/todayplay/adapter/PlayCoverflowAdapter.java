package co.kr.todayplay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.object.CategoryRe;
import co.kr.todayplay.object.PlayModel;

public class PlayCoverflowAdapter extends BaseAdapter {

    private ArrayList<CategoryRe> mData = new ArrayList<>(0);
    private Context mContext;

    public PlayCoverflowAdapter(Context context){mContext = context;}
    public void setData(ArrayList<CategoryRe> data){mData = data;}


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
        String imagepath = rowView.getContext().getFilesDir()+"/"+mData.get(position).getposter();
        Bitmap bm = BitmapFactory.decodeFile(imagepath);
        holder.image.setImageBitmap(bm);
        return rowView;
    }
    static class ViewHolder{
        public ImageView image;
    }
}
