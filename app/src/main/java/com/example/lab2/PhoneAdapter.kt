package com.example.lab2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// PhoneAdapter - Most między danymi a RecyclerView; zarządza tworzeniem i wiązaniem widoków z danymi.
class PhoneAdapter(
    private val context: Context,
    private val mOnItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder>() {
    interface OnItemClickListener {
        fun onItemClickListener(phone: Phone)
    }

    private var phoneList: List<Phone> = emptyList()

    // Przechowuje referencje do widoków pojedynczego elementu listy; umożliwia ponowne użycie widoków.
    inner class PhoneViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val producerView: TextView = itemView.findViewById(R.id.producer)
        val modelView: TextView = itemView.findViewById(R.id.model)


        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClickListener(phoneList[position])
                }
            }
        }
    }

    // Tworzy nowe widoki dla elementów listy; inflacja układu XML i inicjalizacja ViewHoldera.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_view, parent, false)
        return PhoneViewHolder(view)
    }

    // Łączy dane z istniejącymi widokami; ustawia wartości w odpowiednich polach (np. TextViews).
    override fun onBindViewHolder( holder: PhoneViewHolder, position: Int)
    {
        phoneList.let { phones ->
            val currentPhone = phones[position]
            holder.producerView.text = currentPhone.producer
            holder.modelView.text = currentPhone.model
        }
    }

    // Informuje RecyclerView o liczbie elementów do wyświetlenia.
    override fun getItemCount(): Int {
        return phoneList.size
    }

    fun getPhones() : List<Phone>{
        return phoneList
    }

    fun setPhones(phones: List<Phone>) {
        this.phoneList = phones
        notifyDataSetChanged()
    }
}