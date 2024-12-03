package com.coderobust.constructioncosttracker.adapters

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.coderobust.handcraftsshop.R
import com.coderobust.handcraftsshop.databinding.ItemHandCraftBinding
import com.coderobust.handcraftsshop.databinding.ItemOrderBinding
import com.coderobust.handcraftsshop.ui.HandCraft
import com.coderobust.handcraftsshop.ui.Order
import com.coderobust.handcraftsshop.ui.handcraft.HandCraftDetailsActivity
import com.coderobust.handcraftsshop.ui.viewHolders.HandCraftViewHolder
import com.coderobust.handcraftsshop.ui.viewHolders.OrderViewHolder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlin.math.truncate

class HandCraftAdapter(val items: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val binding =
                ItemHandCraftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HandCraftViewHolder(binding)
        }else{
            val binding =
                ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return OrderViewHolder(binding)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (items.get(position) is HandCraft) return 0
        if (items.get(position) is Order) return 1
        return 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HandCraftViewHolder) {
            val HandCraft = items.get(position) as HandCraft
            holder.binding.title.text = HandCraft.title
            holder.binding.desc.text = HandCraft.description
            holder.binding.price.text = "RS: " + HandCraft.price
            holder.binding.rating.text = String.format("⭐ %.1f", HandCraft.rating)

            holder.itemView.setOnClickListener {
                holder.itemView.context.startActivity(
                    Intent(
                        holder.itemView.context,
                        HandCraftDetailsActivity::class.java
                    ).putExtra("data", Gson().toJson(HandCraft))
                )
            }


        }

        else if (holder is OrderViewHolder) {
            val order = items.get(position) as Order
            holder.binding.productTitle.text = order.item?.title
            holder.binding.productPrice.text = "$${order.item?.price!!*order.quantity}"
            holder.binding.productQuantity.text = "Quantity: ${order.quantity}"

            holder.binding.status.text =order.status

            holder.itemView.setOnClickListener {
//                holder.itemView.context.startActivity(
//                    Intent(
//                        holder.itemView.context,
//                        OrderDetailsActivity::class.java
//                    ).putExtra("id", HandCraft.id)
//                )
            }


        }

    }
}