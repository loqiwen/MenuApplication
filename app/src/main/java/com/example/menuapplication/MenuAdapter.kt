package com.example.menuapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val menuItems: MutableList<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.iconImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.icon.setImageResource(menuItem.icon)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        menuItems.removeAt(position)
        notifyDataSetChanged()
    }
}
