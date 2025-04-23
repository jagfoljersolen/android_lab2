package com.example.lab2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class FormActivity : AppCompatActivity() {


    private lateinit var manufacturer: EditText
    private lateinit var model: EditText
    private lateinit var version: EditText
    private lateinit var webpage: EditText
    private lateinit var phoneViewModel : PhoneViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_activity)

        manufacturer = findViewById(R.id.edit_manufacturer)
        model = findViewById(R.id.edit_model)
        version = findViewById(R.id.edit_version)
        webpage = findViewById(R.id.edit_www)

        phoneViewModel = ViewModelProvider(this).get(PhoneViewModel::class.java)

        val cancelButton: View = findViewById(R.id.cancel_button)
        cancelButton.setOnClickListener {
            finish()
        }


        val saveButton: Button = findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            val phone = Phone(
                producer = manufacturer.text.toString(),
                model = model.text.toString(),
                androidVersion = version.text.toString(),
                webpage = webpage.text.toString(),
                id = 0
            )
            phoneViewModel.insert(phone)
            finish()
        }
    }
}