package com.swenson.basemodule.data.local.prefrences

interface Preferences {
    fun putString(key: String, value: String)
    fun putInt(key: String, value: Int)
    fun putBoolean(key: String, value: Boolean)
    fun getString(key: String, defValue: String): String?
    fun getInt(key: String, defValue: Int): Int
    fun getBoolean(key: String, defValue: Boolean): Boolean
    fun putObject(key: String, model: Any?)
    fun <T> getObject(key: String, classType: Class<T>): T?
    fun <T> putList(key: String, value: List<T>)
    fun <T> getList(key: String, classType: Class<T>): List<T>?
    fun clear()
}