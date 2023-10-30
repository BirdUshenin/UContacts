package com.birdushenin.ucontacts

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout

class EditContactDialog(private val context: Context, private val contact: Contact, private val onEditClickListener: (String, String) -> Unit) {

    fun show() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Редактировать контакт")

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val editName = EditText(context)
        editName.hint = "Имя"
        editName.setText(contact.name)

        val editNumber = EditText(context)
        editNumber.hint = "Номер телефона"
        editNumber.setText(contact.phoneNumber)

        layout.addView(editName)
        layout.addView(editNumber)

        alertDialog.setView(layout)

        alertDialog.setPositiveButton("Сохранить") { _, _ ->
            val editedName = editName.text.toString()
            val editedNumber = editNumber.text.toString()
            onEditClickListener(editedName, editedNumber)
        }

        alertDialog.setNegativeButton("Отмена") { _, _ -> }

        alertDialog.show()
    }
}