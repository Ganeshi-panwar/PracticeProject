package com.ganeshi.practiceproject.ContentProvider

import android.annotation.SuppressLint
import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ganeshi.contentprovider.databinding.ContentActivityMainBinding

class ContentMainActivity : AppCompatActivity() {
    private lateinit var binding: ContentActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ContentActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.getPhoneContact.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    0
                )
            } else {
                readContacts()
            }
        }
    }

    @SuppressLint("Range", "SetTextI18n")
    private fun readContacts() {
        val contactResolver = contentResolver
        val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor: Cursor? = contactResolver.query(uri, null, null, null, null)
 //    point to a specific row of data in a result set.
        cursor?.use {
            Log.i("CONTACT_PROVIDER_DEMO", "TOTAL # of Contact ::: ${cursor.count}")

            while (cursor.moveToNext()) {
                val contactName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val contactNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                Log.d("CONTACT_PROVIDER_DEMO", "Contact Name ::: $contactName, Phone # ::: $contactNumber")
                binding.contactView.text = ("Contact Name ::: $contactName  , Phone Number ::: $contactNumber" )
            }
        }
    }
}

