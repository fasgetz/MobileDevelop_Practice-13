package com.example.retrofitkotlin.ApiGetTestRecycle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlin.R
import kotlinx.android.synthetic.main.row.view.*

class UsersAdapter(private val user: List<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount()= user.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userPosition = user[position]
        Log.d("Ankan", "phone number? ${userPosition.phone}")
        holder.name.text = userPosition.name
        holder.email.text = userPosition.email
    }

    class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.first_name
        val email:TextView = itemView.last_name
    }
}
