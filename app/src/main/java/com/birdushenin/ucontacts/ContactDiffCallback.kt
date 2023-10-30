package com.birdushenin.ucontacts

import androidx.recyclerview.widget.DiffUtil

class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}