package com.birdushenin.ucontacts

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import java.util.*

class ContactInputDialog(context: Context, private val onContactAdded: (Contact) -> Unit) : Dialog(context) {
    init {
        setContentView(R.layout.input_contact_layout)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextNumber = findViewById<EditText>(R.id.editTextNumber)
        val buttonAddContact = findViewById<Button>(R.id.buttonAddContact)

        buttonAddContact.setOnClickListener {
            val name = editTextName.text.toString()
            val number = editTextNumber.text.toString()

            if (name.isNotEmpty() && number.isNotEmpty()) {
                val newContact = Contact(UUID.randomUUID().toString(), name, number)
                onContactAdded(newContact)
                dismiss()
            } else {

            }
        }
    }
}