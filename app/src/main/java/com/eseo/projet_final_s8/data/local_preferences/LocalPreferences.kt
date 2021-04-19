package com.eseo.projet_final_s8.data.local_preferences

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import kotlin.coroutines.coroutineContext

class LocalPreferences private constructor(val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    fun saveStringValue(yourValue: String?) {
        sharedPreferences.edit().putString("saveStringValue", yourValue).apply()
    }

    fun getSaveStringValue(): String? {
        return sharedPreferences.getString("saveStringValue", null)
    }


    fun getHistory(): MutableSet<String>? {
        val history = sharedPreferences.getStringSet("location_history", emptySet())
        return history
    }


    fun addToHistory(newEntry: String){
        val history = this.getHistory()
        val newHistory = history?.plus(newEntry)
        sharedPreferences.edit().putStringSet("location_history", newHistory).apply()
    }

    fun eraseHistory(){
        sharedPreferences.edit().clear().apply()
    }


    companion object {
        private var INSTANCE: LocalPreferences? = null

        fun getInstance(context: Context): LocalPreferences {
            return INSTANCE?.let {
                INSTANCE
            } ?: run {
                INSTANCE = LocalPreferences(context)
                return INSTANCE!!
            }
        }
    }



}
