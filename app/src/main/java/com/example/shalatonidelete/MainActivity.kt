package com.example.shalatonidelete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.shalatonidelete.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var lngList: ArrayList<String>
    lateinit var callRV: RecyclerView

    val listAdapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Firebase.firestore

        binding.btnAdd.setOnClickListener {
            var name = binding.name.text.toString()
            var phone = binding.phone.text.toString()
            var address = binding.address.text.toString()
            // Create a new user with a first and last name
            val user = hashMapOf(
                "name" to name,
                "phone" to phone,
                "address" to address
            )

            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "secs", Toast.LENGTH_SHORT).show()
                    Log.d("hshm", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "no secs", Toast.LENGTH_SHORT).show()
                    Log.w("hshm", "Error adding document", e)
                }
            listAdapter.addItem(name,phone,address)
            listAdapter.notifyItemChanged(changingConfigurations)

        }
//        binding.btnRemove.setOnClickListener {
//            Toast.makeText(applicationContext, "Remove Item", Toast.LENGTH_SHORT).show()
//            listAdapter.removeItem()
//            listAdapter.notifyItemChanged(changingConfigurations)
//
//        }

        binding.recyclerview01.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        binding.refreshLayout01.setOnRefreshListener {
            listAdapter.notifyDataSetChanged()
            Toast.makeText(applicationContext, "List Refresh", Toast.LENGTH_SHORT).show()
            binding.refreshLayout01.isRefreshing = false
        }

    }
}