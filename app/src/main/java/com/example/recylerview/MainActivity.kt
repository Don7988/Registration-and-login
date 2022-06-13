package com.example.recylerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recylerview.databinding.ItemListBinding
import com.example.recylerview.adapter.AnimalsAdapter
import com.example.recylerview.model.AnimalData
import com.example.recylerview.view.NewActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var backpressed : Long = 0
    override fun onBackPressed() {
        backPressed()
    }

    private fun backPressed() {
        if (backpressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finish()
        }
        else{
            Toast.makeText(this, "Press once again to exit",Toast.LENGTH_SHORT).show()
            backpressed = System.currentTimeMillis()
        }
    }

    lateinit var mDataBase: DatabaseReference
    private lateinit var animaList: ArrayList<AnimalData>
    private lateinit var mAdapter: AnimalsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animaList = ArrayList()
        mAdapter = AnimalsAdapter(this,animaList)
        recyclerAnimals.layoutManager = LinearLayoutManager(this)
        recyclerAnimals.setHasFixedSize(true)

        getAnimalsData()
    }

    private fun getAnimalsData() {

        mDataBase = FirebaseDatabase.getInstance().getReference("Animals")
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (animalSnapshot in snapshot.children) {
                        val animal = animalSnapshot.getValue(AnimalData::class.java)
                        animaList.add(animal!!)
                    }
                    recyclerAnimals.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity,
                    error.message, Toast.LENGTH_SHORT
                ).show()
            }

        })



    }
}
