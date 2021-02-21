package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import co.kr.todayplay.R;
import co.kr.todayplay.object.Noticedata;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeHolder> {
    ArrayList<Noticedata> notices;
    Context context;

    public NoticeAdapter(ArrayList<Noticedata> notices, Context context){
        this.notices = notices;
        this.context = context;
    }
    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.notice_list_item, parent, false);
        NoticeHolder vh = new NoticeHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        Noticedata notice = notices.get(position);
        holder.noticeTitleTextView.setText(notice.title);

    }

    @Override
    public int getItemCount() {
        return notices.size();
    }
}

class NoticeHolder extends RecyclerView.ViewHolder {
    ImageView importantNoticeImageView;
    TextView noticeTitleTextView;
    TextView noticeInformationTextView;
    RelativeLayout noticeRelativeLayout;
    public NoticeHolder(@NonNull View itemView) {
        super(itemView);
        importantNoticeImageView = itemView.findViewById(R.id.important_notice_iv);
        noticeTitleTextView = itemView.findViewById(R.id.notice_title_tv);
        noticeInformationTextView = itemView.findViewById(R.id.notice_info_tv);
        noticeRelativeLayout = itemView.findViewById(R.id.notice_rl);
    }
}