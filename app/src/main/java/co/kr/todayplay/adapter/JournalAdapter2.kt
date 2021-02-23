package co.kr.todayplay.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper
import co.kr.todayplay.ItemClickListener
import co.kr.todayplay.R
import co.kr.todayplay.`object`.Journal
import co.kr.todayplay.`object`.Recommend


class JournalAdapter2(journals: ArrayList<Recommend>, context: Context, itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<JournalAdapter2.JournalHolder>() {
    var journals : ArrayList<Recommend> = journals
    var context : Context = context
    var itemClickListener = itemClickListener
    var journalDBHelper = JournalDBHelper(context, "Journal.db", null, 1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.journal_list_item, parent, false)
        return JournalHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: JournalHolder, position: Int) {
        val journal: Recommend = journals.get(position)

        val imgpath = Environment.getExternalStorageDirectory().absolutePath + "/" + "journal" + "/" +journalDBHelper.getJournalThumbnail2_img(journal.play_id);
        val bm = BitmapFactory.decodeFile(imgpath)
        System.out.println("jouranl_Adapter2"+journal.play_id);
        System.out.println("journal_Adapter2"+journalDBHelper.getJournalThumbnail2_img(journal.play_id));
        holder.journal_tv.setText(journalDBHelper.getJournalComments(journal.play_id));
        val resize = Bitmap.createScaledBitmap(bm,302,151,true);
        holder.journal_iv.setImageBitmap(resize);
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(holder, journal, position)
        }
    }

    override fun getItemCount(): Int {
        return journals.size
    }


    class JournalHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var journal_tv = itemView?.findViewById<TextView>(R.id.journal_tv)
        var journal_iv = itemView?.findViewById<ImageView>(R.id.journal_iv);
    }
}