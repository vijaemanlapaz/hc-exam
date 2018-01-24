package com.vj.mykotlinapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import vj.com.myapplication.models.City
import vj.com.myapplication.models.Weather

/**
 *
 * Created by VJ on 1/19/2018.
 */
class SearchListAdapter(private var context: Context?, private var cities: List<Any> = emptyList()) : BaseAdapter() {
    private val TAG = this.javaClass.simpleName

    fun setPersonList(cities: List<Any>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return cities.size
    }

    override fun getItem(i: Int): Any {
        return cities[i]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(android.R.layout.simple_list_item_2, viewGroup, false)
            holder = ViewHolder(view)
            view?.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val person = getItem(i)
        if (person is City) {
            holder.name.text = String.format("%s", person.name)
            holder.country.text = String.format("%s", person.country)

        } else if(person is Weather) {
            holder.name.text = String.format("%s", person.name)
            holder.country.text = String.format("%s", person.sys?.country)
        }

        return view
    }

    inner class ViewHolder(itemView: View) {
        @BindView(android.R.id.text1) lateinit var name: TextView
        @BindView(android.R.id.text2) lateinit var country: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}