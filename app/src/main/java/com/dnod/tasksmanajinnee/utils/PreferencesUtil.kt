package com.dnod.tasksmanajinnee.utils

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson

object PreferencesUtil {

    private lateinit var sSharedPreferences: SharedPreferences
    private lateinit var gson: Gson

    fun init(context: Context) {
        sSharedPreferences = context.getSharedPreferences(context.packageName,
                Context.MODE_PRIVATE)
        gson = Gson()
    }

    fun putString(key: String, value: String) {
        sSharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return getString(key, null)
    }

    fun getString(key: String, defValue: String? = null): String? {
        return sSharedPreferences.getString(key, defValue)
    }
}
