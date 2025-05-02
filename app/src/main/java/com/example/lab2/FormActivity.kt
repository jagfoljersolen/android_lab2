package com.example.lab2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged

class FormActivity : AppCompatActivity() {


    private lateinit var manufacturer: EditText
    private lateinit var model: EditText
    private lateinit var version: EditText
    private lateinit var webpage: EditText
    private lateinit var phoneViewModel : PhoneViewModel
    private lateinit var saveButton: Button

    private var manufacturerValid = true
    private var modelValid = true
    private var versionValid = true
    private var webpageValid = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_activity)

        manufacturer = findViewById(R.id.edit_manufacturer)
        model = findViewById(R.id.edit_model)
        version = findViewById(R.id.edit_version)
        webpage = findViewById(R.id.edit_www)
        saveButton = findViewById(R.id.save_button)


        phoneViewModel = ViewModelProvider(this).get(PhoneViewModel::class.java)

        val idExtra = intent.getIntExtra("id", 0)
        var producerExtra= intent.getStringExtra("producer")
        var modelExtra = intent.getStringExtra("model")
        var versionExtra = intent.getStringExtra("version")
        var webpageExtra = intent.getStringExtra("www")

        if (producerExtra != null) manufacturer.setText(producerExtra)
        if (modelExtra != null) model.setText(modelExtra)
        if (versionExtra != null) version.setText(versionExtra)
        if (webpageExtra != null) webpage.setText(webpageExtra)

        manufacturer.doAfterTextChanged { s ->
            manufacturerValid = s?.isBlank() == false
            updateButton()
        }
        model.doAfterTextChanged { s ->
            modelValid = s?.isBlank() == false
            updateButton()
        }
        version.doAfterTextChanged { s ->
            versionValid = s?.isBlank() == false
            updateButton()
        }
        webpage.doAfterTextChanged { s ->
            webpageValid = s?.isBlank() == false
            updateButton()
        }

        manufacturer.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                manufacturerValid = manufacturer.text.isNotBlank()
                if(!manufacturerValid) {
                    manufacturer.setError(getString(R.string.empty_field))
                }
            }
            updateButton()
        }
        model.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                modelValid = model.text.isNotBlank()
                if(!modelValid) {
                    model.setError(getString(R.string.empty_field))
                }
            }
            updateButton()
        }
        version.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                versionValid = version.text.isNotBlank()
                if(!versionValid) {
                    version.setError(getString(R.string.empty_field))
                }
            }
            updateButton()
        }
        webpage.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                webpageValid = webpage.text.isNotBlank()
                if(!webpageValid) {
                    webpage.setError(getString(R.string.empty_field))
                }
            }
            updateButton()
        }

        val cancelButton: View = findViewById(R.id.cancel_button)
        cancelButton.setOnClickListener {
            finish()
        }


        saveButton.setOnClickListener {
            val phone = Phone(
                producer = manufacturer.text.toString(),
                model = model.text.toString(),
                androidVersion = version.text.toString(),
                webpage = webpage.text.toString(),
                id = idExtra
            )
            if (idExtra != 0) {
                phoneViewModel.update(phone)
            } else {
                phoneViewModel.insert(phone)
            }
            finish()
        }

        val webButton : Button = findViewById(R.id.www_button)
        webButton.setOnClickListener {
            val url = webpageExtra
            if (!url.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
    }
    private fun updateButton() {
        saveButton.visibility = if (manufacturerValid && modelValid && versionValid && webpageValid) Button.VISIBLE else Button.GONE
    }
}