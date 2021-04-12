package com.eseo.projet_final_s8.ui.settings

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.eseo.projet_final_s8.R

// Définition de la Class qui sera dans notre RecyclerView
data class SettingsItem(val name: String, val icon: Int, val onClick: (() -> Unit))


class SettingsItemViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.text_view)
    val icon: ImageView = itemView.findViewById(R.id.icon)
}
