package co.kr.todayplay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;

public class ImgTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<String> data = new ArrayList<>();
    private ArrayList<ImgTestAdapter.Holder> itemController = new ArrayList<>();

    public class Holder extends RecyclerView.ViewHolder{
        private ImageView test_iv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.test_iv = itemView.findViewById(R.id.test_iv);
        }
    }
    public ImgTestAdapter(ArrayList<String> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.test_img_list_item, parent, false);
        ImgTestAdapter.Holder holder = new ImgTestAdapter.Holder(view);
        itemController.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String imgpath = data.get(position);
        ImgTestAdapter.Holder itemController = (ImgTestAdapter.Holder) holder;
        Bitmap bm = BitmapFactory.decodeFile(imgpath);
        itemController.test_iv.setImageBitmap(bm);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
