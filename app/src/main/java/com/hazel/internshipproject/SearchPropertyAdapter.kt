package com.hazel.internshipproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hazel.internshipproject.databinding.SearchPropertyLayoutBinding

class SearchPropertyAdapter(private var propertyList: MutableList<PropertyDetailsData>):RecyclerView.Adapter<SearchPropertyAdapter.ViewHolderClass>() {

    private var originalItemList: MutableList<PropertyDetailsData> = propertyList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val inflater = LayoutInflater.from(parent.context)
        val binding=SearchPropertyLayoutBinding.inflate(inflater, parent, false)
        return ViewHolderClass(binding)
    }

    override fun getItemCount(): Int {
        return propertyList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem=propertyList[position]
        holder.bind(currentItem)
    }

    inner class ViewHolderClass(private val binding: SearchPropertyLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PropertyDetailsData){
            binding.city.text=item.city
            binding.address.text=item.address
            binding.rooms.text=item.rooms
            binding.floor.text=item.floor
            binding.kitchen.text=item.floor
            binding.bath.text=item.bath
            binding.area.text=item.area
            binding.interior.text=item.interior
            binding.purpose.text=item.purpose
        }

    }
    fun locationFilter(filterText: String) {
        filterText?.let{
            propertyList = originalItemList.filter { item ->
                item.city.contains(filterText, ignoreCase = true)
            } as MutableList<PropertyDetailsData>
        }?: run {
            propertyList = originalItemList
        }
        notifyDataSetChanged()
    }

}