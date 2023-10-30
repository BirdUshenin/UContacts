package com.birdushenin.ucontacts

import android.Manifest.permission.READ_CONTACTS
import android.annotation.SuppressLint
import android.app.appsearch.SetSchemaRequest.READ_CONTACTS
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.birdushenin.ucontacts.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.READ_CONTACTS"), MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        } else {
            Log.d("App","ERROR")
            val contacts = getContactsFromPhone()
            val adapter = ContactAdapter(contacts)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val contacts = getContactsFromPhone()
                    val adapter = ContactAdapter(contacts)
                    recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = adapter
                } else {

                }
                return
            }
        }
    }

    @SuppressLint("Range")
    private fun getContactsFromPhone(): List<Contact> {
        val contactsList = mutableListOf<Contact>()

        val contentResolver = contentResolver
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                // Проверка на null
                if (name != null) {
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )

                    var phoneNumber = ""

                    if (phoneCursor != null && phoneCursor.moveToNext()) {
                        phoneNumber =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        phoneCursor.close()
                    }
                    Log.d("App", "Name: $name, Phone: $phoneNumber, id: $id")
                    contactsList.add(Contact(id, name, phoneNumber))
                }
            }
            cursor.close()
        }
        return contactsList
    }
}