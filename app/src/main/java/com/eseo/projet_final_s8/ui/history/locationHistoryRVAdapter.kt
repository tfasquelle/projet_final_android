package com.eseo.projet_final_s8.ui.history

import android.location.Location
import android.view.View
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.eseo.projet_final_s8.R

data class LocationItem(val coord: String, val onClick: (() -> Unit))

class LocationItemViewHolder(itemView: View): ViewHolder(itemView) {
    val coord: TextView = itemView.findViewById(R.id.location_history_item_text)
}