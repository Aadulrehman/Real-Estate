package com.hazel.internshipproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchPropertyAdapter(private var propertyList: MutableList<PropertyDetailsData>):RecyclerView.Adapter<SearchPropertyAdapter.ViewHolderClass>() {
    private var originalItemList: MutableList<PropertyDetailsData> = propertyList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_property_layout, parent, false)
        return SearchPropertyAdapter.ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return propertyList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem=propertyList[position]
        holder.rvArea.text=currentItem.area
        holder.rvCity.text=currentItem.city
        holder.rvAddress.text=currentItem.address
        holder.rvBath.text=currentItem.bath
        holder.rvFloor.text=currentItem.floor
        holder.rvInterior.text=currentItem.interior
        holder.rvRooms.text=currentItem.rooms
        holder.rvKitchen.text=currentItem.kitchen
        holder.rvPurpose.text=currentItem.purpose
    }

    class ViewHolderClass(itemView: View):RecyclerView.ViewHolder(itemView) {
        val rvCity: TextView =itemView.findViewById(R.id.city)
        val rvAddress: TextView =itemView.findViewById(R.id.address)
        val rvRooms: TextView =itemView.findViewById(R.id.rooms)
        val rvFloor: TextView =itemView.findViewById(R.id.floor)
        val rvKitchen: TextView =itemView.findViewById(R.id.kitchen)
        val rvBath: TextView =itemView.findViewById(R.id.bath)
        val rvArea: TextView =itemView.findViewById(R.id.area)
        val rvInterior: TextView =itemView.findViewById(R.id.interior)
        val rvPurpose: TextView =itemView.findViewById(R.id.purpose)
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