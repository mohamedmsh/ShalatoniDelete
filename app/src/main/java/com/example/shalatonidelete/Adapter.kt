package com.example.shalatonidelete

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shalatonidelete.databinding.ItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyView>() {
    val db = Firebase.firestore
    var count = 0
    inner class MyView(private val binding: ItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    count = result.size()
                    var doc =result.documents[pos]
                    binding.textName.text = doc.data!!["name"].toString()
                    binding.textPhone.text = doc.data!!["phone"].toString()
                    binding.textAddress.text = doc.data!!["address"].toString()
                    binding.remove.setOnClickListener {
                        db.collection("users").document(doc.id).delete()
                        count--
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("hshm", "Error getting documents.", exception)
                }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return count
    }

    fun addItem(name: String, phone: String, address: String) {
        this.count++
    }

    fun removeItem() {
        if(this.count > 0) this.count--

    }
}