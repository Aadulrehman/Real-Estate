package com.hazel.internshipproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PropertyEditAdapter(private val propertyList: MutableList<PropertyDetailsData>):RecyclerView.Adapter<PropertyEditAdapter.ViewHolderClass>(){

    private var editBtnListener: PropertyEditAdapter.EditButtonClickListener? = null
    private var deleteBtnListener: PropertyEditAdapter.DeleteButtonClickListener? = null
    interface EditButtonClickListener {
        fun onEditButtonClick(item: PropertyDetailsData)
    }
    interface DeleteButtonClickListener {
        fun onDeleteButtonClick(item: PropertyDetailsData)
    }

    fun setOnEditClickListener(listener: PropertyEditAdapter.EditButtonClickListener) {
        editBtnListener= listener
    }
    fun setOnDeleteClickListener(listener: PropertyEditAdapter.DeleteButtonClickListener) {
        deleteBtnListener= listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyEditAdapter.ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.propertylist_layout, parent, false)
        return PropertyEditAdapter.ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return propertyList.size
    }

    override fun onBindViewHolder(holder: PropertyEditAdapter.ViewHolderClass, position: Int) {
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

        holder.rvEditBtn.setOnClickListener{

            editBtnListener?.onEditButtonClick(currentItem)
        }
        holder.rvDeleteBtn.setOnClickListener{
            deleteBtnListener?.onDeleteButtonClick(currentItem)
            propertyList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class ViewHolderClass (itemView: View):RecyclerView.ViewHolder(itemView){
        val rvCity: TextView =itemView.findViewById(R.id.city)
        val rvAddress: TextView =itemView.findViewById(R.id.address)
        val rvRooms: TextView =itemView.findViewById(R.id.rooms)
        val rvFloor: TextView =itemView.findViewById(R.id.floor)
        val rvKitchen: TextView =itemView.findViewById(R.id.kitchen)
        val rvBath: TextView =itemView.findViewById(R.id.bath)
        val rvArea: TextView =itemView.findViewById(R.id.area)
        val rvInterior: TextView =itemView.findViewById(R.id.interior)
        val rvPurpose: TextView =itemView.findViewById(R.id.purpose)
        val rvEditBtn: Button = itemView.findViewById(R.id.mybtnEdit)
        val rvDeleteBtn: Button = itemView.findViewById(R.id.mybtnDelete)
    }

}