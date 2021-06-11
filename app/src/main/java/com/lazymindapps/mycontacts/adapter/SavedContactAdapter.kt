package com.lazymindapps.mycontacts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazymindapps.mycontacts.`interface`.ContactClickListner
import com.lazymindapps.mycontacts.databinding.RvContactListBinding
import com.lazymindapps.mycontacts.model.Contact

class SavedContactAdapter(

        private val context:Context,
        private var clickListner: ContactClickListner,
        private val contactList:MutableList<Contact>
):RecyclerView.Adapter<SavedContactAdapter.ViewHolder>() {


    class ViewHolder(val binding: RvContactListBinding) :RecyclerView.ViewHolder(binding.root) {
        //  var num = 1
        fun bindItems(contacts:Contact){
            binding.tvSn.text = (adapterPosition+1).toString()
            //  num++
            var name = contacts.firstName.capitalize()+" "+contacts.lastName.capitalize()
            binding.tvName.text = name


        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedContactAdapter.ViewHolder {
        val binding = RvContactListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedContactAdapter.ViewHolder, position: Int) {

        holder.bindItems(contactList[position])
        holder.itemView.setOnClickListener {
            clickListner.clickedItem(contactList[position])


        }
        holder.binding.ibCall.setOnClickListener {
            val contact = contactList[position]
            clickListner.makeCall(contact.number)
        }






    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}