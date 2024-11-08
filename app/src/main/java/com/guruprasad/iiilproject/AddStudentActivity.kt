package com.guruprasad.iiilproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.guruprasad.iiilproject.databinding.ActivityAddStudentBinding

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddStudentBinding
    private lateinit var database : FirebaseDatabase

    private fun valid(name: String, prn: String, division: String, rollNo: String, branch: String): Boolean {

        if (name.isEmpty())
        {
            binding.name.error = "Name is required"
            return false
        }
        if (prn.isEmpty())
        {
            binding.prn.error = "PRN is required"
            return false
        }
        if (division.isEmpty())
        {
            binding.division.error = "Division is required"
            return false
        }
        if (rollNo.isEmpty())
        {
            binding.rollNo.error = "Roll No is required"
            return false
        }
        if (branch.isEmpty())
        {
            binding.branch.error = "Branch is required"
            return false
        }

        return true

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FirebaseDatabase.getInstance()


        binding.submit.setOnClickListener{

            val name = binding.name.text.toString()
            val prn = binding.prn.text.toString()
            val division = binding.division.text.toString()
            val rollNo = binding.rollNo.text.toString()
            val branch = binding.branch.text.toString()

            if (valid(name,prn,division,rollNo,branch))
            {
                addStudent(name,prn,division,rollNo,branch)
            }

        }



    }

    private fun addStudent(name: String, prn: String, division: String, rollNo: String, branch: String) {

        val student = StudentModel(name,prn,division,rollNo,branch)
        try {
            database.reference.child("Students").child(System.currentTimeMillis().toString()).setValue(student)
                .addOnSuccessListener {
                    binding.name.text?.clear()
                    binding.prn.text?.clear()
                    binding.division.text?.clear()
                    binding.rollNo.text?.clear()
                    binding.branch.text?.clear()
                    finish()
                }
                .addOnFailureListener{
                    it.printStackTrace()
                }
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }
}