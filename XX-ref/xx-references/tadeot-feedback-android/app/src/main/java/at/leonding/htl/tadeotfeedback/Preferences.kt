package at.leonding.htl.tadeotfeedback

import android.content.Context
import android.util.Log

const val PREFERENCE_FILENAME = "Feedback"
const val IP_ADRESS_KEY = "IPADRESS"
const val PORT_KEY = "PORT"


object Preferences {
    private val LOG_TAG = Preferences::class.java.simpleName

    var ipAdress = "10.0.0.0"
    var port = 80

    lateinit var _context : Context

    fun setContext(context : Context){
        _context = context
        read()
    }

    fun read() {
        if (_context == null ) return
        val preferences = _context.getSharedPreferences(
                PREFERENCE_FILENAME, Context.MODE_PRIVATE)
        ipAdress = preferences.getString(IP_ADRESS_KEY, "10.0.0.0")
        port = preferences.getInt(PORT_KEY,80)
    }

    fun save(ipAdress :String, port :Int = 0){
        if (_context == null ) return
        val preferences = _context.getSharedPreferences(
                PREFERENCE_FILENAME, Context.MODE_PRIVATE)
        val preferenceEditor = preferences.edit()
        if (ipAdress.length > 0){
            preferenceEditor.putString(IP_ADRESS_KEY, ipAdress)
        }
        if (port > 0){
            preferenceEditor.putInt(PORT_KEY, port)
        }
        preferenceEditor.commit()
        Log.d(LOG_TAG, "onSave(), IpAdress: ${ipAdress}, Port: ${port}")
    }

}