package com.guruprasad.iiilproject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.guruprasad.iiilproject.databinding.StudentLayoutBinding

open class StudentAdapter(options: FirebaseRecyclerOptions<StudentModel>) : FirebaseRecyclerAdapter<StudentModel, StudentAdapter.onViewHolder>(
    options
) {

    class onViewHolder(val binding : StudentLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onViewHolder {
        val view = StudentLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return onViewHolder(view)
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }



    override fun onBindViewHolder(holder: onViewHolder, position: Int, model: StudentModel) {
        if (position in 0 until snapshots.size) {
            holder.binding.apply {
                name.text = model.name
                prn.text = model.prn
                div.text = model.division
                rollNo.text = model.rollNo
                branch.text = model.branch
                delete.setOnClickListener {
                    getRef(position).removeValue()
                }
            }
        } else {
            Log.e("StudentAdapter", "Attempted to bind view holder with an invalid position: $position")
        }
    }
}