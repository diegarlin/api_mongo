import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private lateinit var sharedPreferences: SharedPreferences

    private const val SHARED_PREFS_FILE_NAME = "MyAppSharedPreferences"
    private const val ACCESS_TOKEN_KEY = "access_token"
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun saveTokenToSharedPreferences(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(ACCESS_TOKEN_KEY, token)
        editor.apply()
    }

    fun getTokenFromSharedPreferences(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    fun clearTokenFromSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(ACCESS_TOKEN_KEY)
        editor.apply()
    }
}
