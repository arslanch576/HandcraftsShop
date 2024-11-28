package com.coderobust.constructioncosttracker.adapters

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.coderobust.handcraftsshop.databinding.ItemProjectBinding
import com.coderobust.handcraftsshop.ui.HandCraft
import com.coderobust.handcraftsshop.ui.viewHolders.ProjectViewHolder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.math.truncate

class ProjectAdapter(val items: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        if (viewType == 0) {
            val binding =
                ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProjectViewHolder(binding)
//        }
//        return ProjectViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (items.get(position) is HandCraft) return 0
        return 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ProjectViewHolder) {
            val project = items.get(position) as HandCraft
            holder.binding.title.text = project.title
            holder.binding.desc.text = project.description
            holder.binding.budget.text = "RS: " + project.price

            holder.itemView.setOnClickListener {
//                holder.itemView.context.startActivity(
//                    Intent(
//                        holder.itemView.context,
//                        ProjectDetailsActivity::class.java
//                    ).putExtra("id", project.id)
//                )
            }


        }

    }
}