package com.birdushenin.ucontacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val contacts: List<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val id: TextView  = view.findViewById(R.id.id)
        val nameA: TextView = view.findViewById(R.id.name)
        val numberQ: TextView = view.findViewById(R.id.number)

        fun bind(contact: Contact){
            nameA.text = contact.name
            numberQ.text = contact.phoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[position])
    }
    override fun getItemCount(): Int {
        return contacts.size
    }

}