package com.swenson.basemodule.data.local.prefrences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesImpl(context: Context) : Preferences {
    private val settings: SharedPreferences
    private val PREF_FILE = "swenson_SALES_PREFS"
    private val gson: Gson

    init {
        settings = context.getSharedPreferences(PREF_FILE, 0)
        gson = Gson()
    }

    override fun putString(key: String, value: String) {
        val editor = settings.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun putInt(key: String, value: Int) {
        val editor = settings.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    override fun putBoolean(key: String, value: Boolean) {
        val editor = settings.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun getString(key: String, defValue: String): String? {
        return settings.getString(key, defValue)
    }


    override fun getInt(key: String, defValue: Int): Int {

        return settings.getInt(key, defValue)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return settings.getBoolean(key, defValue)
    }

    override fun putObject(key: String, model: Any?) {
        if (model != null) {
            val serializedObject = gson.toJson(model)
            settings.edit().putString(key, serializedObject).apply()
        } else {
            settings.edit().remove(key).apply()
        }
    }

    override fun <T> getObject(
        key: String,
        classType: Class<T>
    ): T? {
        return if (settings.contains(key)) {
            gson.fromJson(settings.getString(key, ""), classType)
        } else null
    }

    override fun <T> putList(key: String, value: List<T>) {
        val jsonArray = gson.toJsonTree(value).asJsonArray
        settings.edit().putString(key, jsonArray.toString()).apply()
    }

    override fun <T> getList(key: String, classType: Class<T>): List<T>? {
        val typeToken = TypeToken.getParameterized(ArrayList::class.java, classType).getType()
        return gson.fromJson<List<T>>(settings.getString(key, "[]"), typeToken)
    }

    override fun clear() {
        settings.edit().clear().apply()
    }
}