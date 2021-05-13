package co.kr.todayplay.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper
import co.kr.todayplay.ItemClickListener
import co.kr.todayplay.MainActivity
import co.kr.todayplay.R
import co.kr.todayplay.`object`.Recommend
import co.kr.todayplay.fragment.Journal.JournalDetailFragment


class JournalAdapter2(journals: ArrayList<Recommend>, context: Context, itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<JournalAdapter2.JournalHolder>() {
    var journals : ArrayList<Recommend> = journals
    var context : Context = context
    var itemClickListener = itemClickListener
    var journalDBHelper = JournalDBHelper(context, "Journal.db", null, 1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.pf_scrap_rv_detail, parent, false)
        return JournalHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: JournalHolder, position: Int) {
        val journal: Recommend = journals.get(position)

//        val imgpath = Environment.getExternalStorageDirectory().absolutePath + "/" + "journal" + "/" +journalDBHelper.getJournalThumbnail2_img(journal.play_id);
        val imgpath = context.filesDir.toString()+"/"+ journalDBHelper.getJournalThumbnail2_img(journal.play_id)
        val bm = BitmapFactory.decodeFile(imgpath)
        System.out.println("jouranl_Adapter2" + journal.play_id);
        System.out.println("journal_Adapter2" + journalDBHelper.getJournalThumbnail2_img(journal.play_id));
        holder.journal_tv.setText(journalDBHelper.getJournalTitle(journal.play_id));
        holder.journal_sub_tv.setText(journalDBHelper.getJournalSubtitle(journal.play_id))
//        val resize = Bitmap.createScaledBitmap(bm,302,151,true);
        holder.journal_iv.setImageBitmap(bm);
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(holder, journal, position)
            val journalDetailFragment = JournalDetailFragment()
            (context as MainActivity).replaceFragment(journalDetailFragment,journal.play_id)
        }
    }

    override fun getItemCount(): Int {
        return journals.size
    }


    class JournalHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var journal_iv = itemView?.findViewById<ImageView>(R.id.pf_scrap_post_iv)
        var journal_tv = itemView?.findViewById<TextView>(R.id.pf_scrap_post_tv)
        var journal_sub_tv = itemView.findViewById<TextView>(R.id.pf_sub_scrap_post_tv)
    }
}