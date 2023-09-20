package com.hazel.internshipproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterClass(private val dataList: ArrayList<UserData>):RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {

    private var listener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(user: UserData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.userlayout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem=dataList[position]
        holder.rvName.text=currentItem.name
        holder.rvEmail.text=currentItem.email

        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentItem)
        }

    }
    class ViewHolderClass(itemView: View):RecyclerView.ViewHolder(itemView) {
        val rvName:TextView=itemView.findViewById(R.id.name)
        val rvEmail:TextView=itemView.findViewById(R.id.email)

    }
}