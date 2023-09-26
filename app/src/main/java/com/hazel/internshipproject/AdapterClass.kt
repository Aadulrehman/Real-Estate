package com.hazel.internshipproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hazel.internshipproject.databinding.UserlayoutBinding

class AdapterClass(private val dataList: ArrayList<UserData>):RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {

    private var listener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(user: UserData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserlayoutBinding.inflate(inflater,parent,false)
        return ViewHolderClass(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem=dataList[position]
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentItem)
        }
    }
    inner class ViewHolderClass(private val binding: UserlayoutBinding):RecyclerView.ViewHolder(binding.root) {
       fun bind(item: UserData){
           binding.name.text=item.name
           binding.email.text=item.email
           binding.executePendingBindings()
       }
    }
}