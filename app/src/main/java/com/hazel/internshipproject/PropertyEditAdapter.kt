package com.hazel.internshipproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hazel.internshipproject.databinding.PropertylistLayoutBinding
import com.hazel.internshipproject.databinding.UserlayoutBinding

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
        val inflater = LayoutInflater.from(parent.context)
        val binding = PropertylistLayoutBinding.inflate(inflater,parent,false)
        return ViewHolderClass(binding)
    }

    override fun getItemCount(): Int {
        return propertyList.size
    }

    override fun onBindViewHolder(holder: PropertyEditAdapter.ViewHolderClass, position: Int) {
        val currentItem=propertyList[position]
        holder.bind(currentItem, position)
    }

    inner class ViewHolderClass (private val binding: PropertylistLayoutBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item: PropertyDetailsData, position: Int){
            binding.city.text=item.city
            binding.address.text=item.address
            binding.rooms.text=item.rooms
            binding.floor.text=item.floor
            binding.kitchen.text=item.floor
            binding.bath.text=item.bath
            binding.area.text=item.area
            binding.interior.text=item.interior
            binding.purpose.text=item.purpose

            binding.mybtnEdit.setOnClickListener{

                editBtnListener?.onEditButtonClick(item)
            }
            binding.mybtnDelete.setOnClickListener{
                deleteBtnListener?.onDeleteButtonClick(item)
                propertyList.removeAt(position)
                notifyItemRemoved(position)
            }
        }

    }

}