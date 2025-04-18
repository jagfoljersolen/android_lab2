package com.example.lab2

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var phoneViewModel : PhoneViewModel
    private lateinit var phoneAdapter : PhoneAdapter
    private lateinit var recyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // inicjalizacja RecyclerView
        recyclerview = findViewById(R.id.phone_recycler_view)

        // Inicjalizacja adaptera
        phoneAdapter = PhoneAdapter()
        recyclerview.adapter = phoneAdapter

        // Ustawienie layout menagera (lista pionowa)
        recyclerview.layoutManager = LinearLayoutManager(this)

        // Inicjalizacja ViewModel
        phoneViewModel = PhoneViewModel(application)
        // obserwacja danych z viewModel
        phoneViewModel.allPhones.observe(this) { phones ->
            Log.d("MainActivity", "Phones observed: ${phones.size}")
            // przekazanie danych do adaptera
            phoneAdapter.setPhones(phones)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if ( id == R.id.delete_option){
            phoneViewModel.deleteAll()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}