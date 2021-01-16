package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;

public class PerformDetailStaffAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<StaffItem> data = new ArrayList<>();
    private ArrayList<StaffHolder> itemController = new ArrayList<>();
    public static class StaffItem{
        private int drawable;
        private String job;
        private String name;

        public StaffItem(){}
        public StaffItem(int drawable, String job, String name){
            this.drawable = drawable;
            this.job = job;
            this.name = name;
        }
        public int getDrawable(){
            return drawable;
        }
        public String getJob(){
            return job;
        }
        public String getName(){
            return name;
        }
    }

    public class StaffHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView job_tv;
        private TextView name_tv;
        public StaffHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.staff_iv);
            this.job_tv = itemView.findViewById(R.id.staff_job_tv);
            this.name_tv = itemView.findViewById(R.id.staff_name_tv);
        }
    }

    public PerformDetailStaffAdapter(ArrayList<StaffItem> data){
        super();
        this.data = data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.perform_staff_item, parent, false);
        StaffHolder staffHolder = new StaffHolder(view);
        itemController.add(staffHolder);
        return staffHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StaffItem item = data.get(position);
        StaffHolder itemController = (StaffHolder)holder;
        itemController.name_tv.setText(item.getName());
        itemController.imageView.setImageResource(item.getDrawable());
        itemController.job_tv.setText(item.getJob());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
