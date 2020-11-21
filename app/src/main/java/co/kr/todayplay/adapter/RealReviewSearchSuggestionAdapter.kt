package co.kr.todayplay.adapter

import android.content.Context
import android.database.AbstractCursor
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cursoradapter.widget.SimpleCursorAdapter
import co.kr.todayplay.ItemClickListener
import co.kr.todayplay.R
import co.kr.todayplay.`object`.Show
import java.util.*


class RealReviewSearchSuggestionAdapter(context: Context, visibleColumnNamesArray : Array<out String>, idsArray: IntArray, itemClickListener: ItemClickListener) : SimpleCursorAdapter(context, R.layout.real_review_suggestion_item, null, visibleColumnNamesArray, idsArray, 0){
    private val columnNamesArray =
        arrayOf("_id", "showName", "imageSource")
    private val visibleColumnNamesArray =
        arrayOf<String>("showName")
    var context = context
    var layoutInflater : LayoutInflater? = null
    var itemClickListener = itemClickListener

    var tempTitleArray : ArrayList<String> = ArrayList()
    var tempTagArray : ArrayList<String> = ArrayList()
    var tempDateArray : ArrayList<String> = ArrayList()
    var tempImageResourceArray : ArrayList<Int> = ArrayList()

    init {
        layoutInflater = LayoutInflater.from(context)

        tempTitleArray.add("뮤지컬 <오페라의 유령>")
        tempTitleArray.add("뮤지컬 <마술피리>")
        tempTagArray.add("로맨스, 스릴러")
        tempTagArray.add("판타지, 고전극, 로맨스")
        tempDateArray.add("2020.05.10~08.15")
        tempDateArray.add("2020.09.26~10.28")
        tempImageResourceArray.add(R.drawable.poster_sample11)
        tempImageResourceArray.add(R.drawable.poster_sample18)
    }

    override fun runQueryOnBackgroundThread(constraint: CharSequence?): Cursor {
        return SuggestionCursor(constraint)
    }

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        val inflater =
            context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View =
            inflater.inflate(R.layout.real_review_suggestion_item, parent, false)
        val viewHolder = ViewHolder(rowView)
        rowView.tag = viewHolder
        return rowView
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        //super.bindView(view, context, cursor)
        var viewHolder : ViewHolder = view!!.tag as ViewHolder
        //var url = cursor!!.getString(2)
        //var name = cursor!!.getString(3)
        var pos = cursor!!.position
        viewHolder.realReviewSuggestionTitleTextView.setText(tempTitleArray.get(pos))
        viewHolder.realReviewSuggestionTagTextView.setText(tempTagArray.get(pos))
        viewHolder.realReviewSuggestionDateTextView.setText(tempDateArray.get(pos))
        viewHolder.realReviewSuggestionImageView.setImageResource(tempImageResourceArray.get(pos))
        view.setOnClickListener(View.OnClickListener {
            itemClickListener.onItemClicked(viewHolder, "", pos)
        })
        //Glide.with(context).load(referenceUrl).into(viewHolder.categoryImageView)
    }

    class SuggestionCursor(constraint: CharSequence?) : AbstractCursor(){
        val findCatagoriesList: List<Show> = ArrayList()
        var constraint = constraint
        init {
            if (constraint != null) {

            }
        }

        override fun getString(column: Int): String {

            return "test"
        }

        override fun getLong(column: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getColumnNames(): Array<String> {
            return arrayOf("_id", "showName","imageSource")
        }

        override fun getShort(column: Int): Short {
            return 0
        }

        override fun getFloat(column: Int): Float {
            return 0F
        }

        override fun getDouble(column: Int): Double {
            return 0.0
        }

        override fun isNull(column: Int): Boolean {
            return false
        }

        override fun getInt(column: Int): Int {
            return 0
        }

    }

    class ViewHolder(view: View) {
        var realReviewSuggestionImageView : ImageView = view.findViewById(R.id.real_review_suggestion_iv)
        var realReviewSuggestionTitleTextView : TextView = view.findViewById(R.id.real_review_suggestion_title_tv)
        var realReviewSuggestionTagTextView : TextView = view.findViewById(R.id.real_review_suggestion_tag_tv)
        var realReviewSuggestionDateTextView : TextView = view.findViewById(R.id.real_review_suggestion_date_tv)
    }

}