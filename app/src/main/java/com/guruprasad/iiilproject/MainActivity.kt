package com.guruprasad.iiilproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.guruprasad.iiilproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.progressBar.visibility = View.VISIBLE

        binding.recyclerView.layoutManager = WrapContentLinearLayoutManager(this@MainActivity)

        val query = FirebaseDatabase.getInstance().reference.child("Students")
        val options = FirebaseRecyclerOptions.Builder<StudentModel>()
            .setQuery(query, StudentModel::class.java)
            .build()

        adapter = object : StudentAdapter(options) {
            override fun onDataChanged() {
                super.onDataChanged()
                binding.progressBar.visibility = View.GONE
            }

            override fun onError(error: DatabaseError) {
                super.onError(error)
                binding.progressBar.visibility = View.GONE
                error.toException().printStackTrace()
            }
        }

        binding.recyclerView.adapter = adapter

        binding.add.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddStudentActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
