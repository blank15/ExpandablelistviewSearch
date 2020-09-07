package id.kampung.expandablelistviewsearch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import id.kampung.expandablelistviewsearch.model.TitleModel
import android.widget.ExpandableListView
import id.kampung.expandablelistviewsearch.model.ChildModel


class ExpandableAdapter(private val context: Context, private val titleList: MutableList<TitleModel>) :
    BaseExpandableListAdapter(), Filterable {

    var itemsCopy: MutableList<TitleModel> = titleList
    var expanAll = false
    override fun getGroup(groupPosition: Int): Any {
        return itemsCopy[groupPosition].name
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    @SuppressLint("InflateParams")
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {

            convertView

            val title = getGroup(groupPosition) as String
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.header_layout, null)
            val listTitle = convertView!!.findViewById<TextView>(R.id.listTitle)
            listTitle.setTypeface(null, Typeface.BOLD)
            listTitle.text = title
        }


        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return itemsCopy[groupPosition].dataChild.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return itemsCopy[groupPosition].dataChild[childPosition].name
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val expandedListText = getChild(groupPosition, childPosition) as String
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.child_layout, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.expandedListItem)
        expandedListTextView.text = expandedListText
        return convertView

    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return itemsCopy.size
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()

                if (charString.isEmpty()) {
                    itemsCopy = titleList
                } else {

                     val filteredList = arrayListOf<TitleModel>()
                    expanAll = true
                    for (row in titleList.indices) {

                        val cities = titleList[row].dataChild
                        val citiesnew :MutableList<ChildModel> = mutableListOf<ChildModel>()

                        for(city in cities){
                            if(city.name.toLowerCase().contains(charString.toLowerCase())){
                                citiesnew.add(city)
                            }
                        }

                        if(citiesnew.size > 0){
                            filteredList.add(TitleModel(titleList[row].name,citiesnew))
                        }

                    }

                    itemsCopy = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = itemsCopy
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                itemsCopy = filterResults.values as MutableList<TitleModel>
                notifyDataSetChanged()
            }
        }
    }
}