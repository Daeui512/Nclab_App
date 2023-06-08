package com.example.n_clab.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.n_clab.R
import com.example.n_clab.ui.board.BoardListItem

class BoardListAdapter(private val items: MutableList<BoardListItem>): BaseAdapter() {


    override fun getCount(): Int = items.size

    override fun getItem(position: Int): BoardListItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView = view
        if(convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item, parent, false)

        val item: BoardListItem = items[position]
        convertView!!.findViewById<TextView>(R.id.title).text = item.title
        convertView.findViewById<TextView>(R.id.reg_user).text = item.regUser
        convertView.findViewById<TextView>(R.id.reg_date).text = item.regDate
        convertView.findViewById<TextView>(R.id.view_cnt).text = "조회수 " + item.viewCnt

        return convertView
    }

}