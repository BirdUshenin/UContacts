package com.birdushenin.ucontacts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    var contacts: List<Contact>

) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    var onItemLongClickListener: ((Contact) -> Unit)? = null
    var onItemClickListener: ((Contact) -> Unit)? = null
    var viewHolders = mutableListOf<ViewHolder>()
    var isLongPressPussy: Boolean = false
    val CheckedItems = mutableMapOf<Int, Boolean>()


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idS: TextView = view.findViewById(R.id.id)
        val nameA: TextView = view.findViewById(R.id.name)
        val numberQ: TextView = view.findViewById(R.id.number)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)

        init {
            view.setOnClickListener {
                val contact = contacts[adapterPosition]
                onItemClickListener?.invoke(contact)
            }

            view.setOnLongClickListener {
                val contact = contacts[adapterPosition]
                onItemLongClickListener?.invoke(contact)

                checkBox.visibility = View.VISIBLE
                true
            }

            checkBox.setOnCheckedChangeListener { _, isChecking ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    CheckedItems[position] = isChecking
                }
            }
        }

        fun bind(contact: Contact) {
            idS.text = contact.id
            nameA.text = contact.name
            numberQ.text = contact.phoneNumber
        }

        fun setCheckboxChecked(checked: Boolean) {
            checkBox.isChecked = checked
        }
    }


    fun CheckBox() {
        viewHolders.forEach { holder ->
            holder.checkBox
        }
    }

    fun pressToAll(contact: Contact) {
        viewHolders.forEach { holder ->
            holder.setCheckboxChecked(checked = true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contact = contacts[position]
        holder.bind(contact)

        if (isLongPressPussy) {
            holder.checkBox.visibility = View.VISIBLE
            holder.checkBox.isChecked = CheckedItems[position] ?: false
        } else {
            holder.checkBox.visibility = View.INVISIBLE
        }
        viewHolders.add(holder)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun updateContacts(newContacts: List<Contact>) {
        val diffResult = DiffUtil.calculateDiff(ContactDiffUtil(contacts, newContacts))
        contacts = newContacts
        diffResult.dispatchUpdatesTo(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteSelectedContacts() {
        val positionsToRemove = CheckedItems.entries.filter { it.value }.map { it.key }
        val contactsToRemove = positionsToRemove.mapNotNull { contacts.getOrNull(it) }
        val newContacts = contacts.toMutableList()
        newContacts.removeAll(contactsToRemove)
        updateContacts(newContacts)
        CheckedItems.clear()
        isLongPressPussy = false
        notifyDataSetChanged()
    }
}