package com.example.n_clab.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.n_clab.R

class UserListAdapter(private val items: MutableList<UserListItem>): BaseAdapter() {


    override fun getCount(): Int = items.size

    override fun getItem(position: Int): UserListItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView = view
        if(convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.user_list_item, parent, false)

        val item: UserListItem = items[position]
        convertView!!.findViewById<CheckBox>(R.id.emp_name).text = item.empName
        convertView.findViewById<TextView>(R.id.emp_no).text = item.empNo
        convertView.findViewById<TextView>(R.id.token).text = item.token

        return convertView
    }

}