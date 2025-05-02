package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder




class MainActivity : AppCompatActivity(), PhoneAdapter.OnItemClickListener {

    private lateinit var phoneViewModel : PhoneViewModel
    private lateinit var phoneAdapter : PhoneAdapter
    private lateinit var recyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // inicjalizacja RecyclerView
        recyclerview = findViewById(R.id.phone_recycler_view)

        // Inicjalizacja adaptera
        phoneAdapter = PhoneAdapter(this, this)
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


        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val phoneList = phoneAdapter.getPhones()
                    val phone = phoneList[position]
                    phoneViewModel.delete(phone)
                }
            })
        mIth.attachToRecyclerView(recyclerview)
    }

    override fun onItemClickListener(phone: Phone) {
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra("id", phone.id)
        intent.putExtra("producer", phone.producer)
        intent.putExtra("model", phone.model)
        intent.putExtra("version", phone.androidVersion)
        intent.putExtra("www", phone.webpage)
        startActivity(intent)
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