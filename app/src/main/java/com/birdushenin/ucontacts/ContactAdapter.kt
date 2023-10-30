package com.birdushenin.ucontacts

import android.app.Dialog
import android.provider.ContactsContract.Contacts
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(var contacts: List<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    var onItemLongClickListener: ((Contact) -> Unit)? = null
    var onItemClickListener: ((Contact) -> Unit)? = null


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val idS: TextView  = view.findViewById(R.id.id)
        val nameA: TextView = view.findViewById(R.id.name)
        val numberQ: TextView = view.findViewById(R.id.number)

        init {
            view.setOnClickListener {
                val contact = contacts[adapterPosition]
                onItemClickListener?.invoke(contact)
            }
            view.setOnLongClickListener {
                val contact = contacts[adapterPosition]
                onItemLongClickListener?.invoke(contact)
                Log.d("Long","Y")
                true
            }
        }

        fun bind(contact: Contact){
            idS.text = contact.id
            nameA.text = contact.name
            numberQ.text = contact.phoneNumber
        }
    }

    fun updateContacts(newContacts: List<Contact>){
        val diffResult = DiffUtil.calculateDiff(ContactDiffUtil(contacts, newContacts))
        contacts = newContacts
        diffResult.dispatchUpdatesTo(this)
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
