package kazmi.dev.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

object PrefDatastoreUtil {

    private const val DATA_STORE_NAME = "My App Preference"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

    private suspend fun <T> Context.editPreference(
        key: Preferences.Key<T>,
        value: T
    ) = withContext(Dispatchers.IO){
        kotlin.runCatching {
            dataStore.edit {pref->
                pref[key] = value
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    private suspend fun <T> Context.getPreference(
        key: Preferences.Key<T>,
        defaultValue: T
    ): T = withContext(Dispatchers.IO){
        val preference = dataStore.data.firstOrNull()
        preference?.get(key)?: defaultValue
    }

    //save and get boolean
    suspend fun Context.saveBoolean( key: String, value: Boolean) = editPreference(booleanPreferencesKey(key), value)

    suspend fun Context.getBoolean(key: String, defaultValue: Boolean): Boolean = getPreference(booleanPreferencesKey(key), defaultValue)



    //save and get String
    suspend fun Context.saveString(key: String, value: String) = editPreference(stringPreferencesKey(key), value)

    suspend fun Context.getString(key: String, defaultValue: String): String = getPreference(stringPreferencesKey(key), defaultValue)



    //save and get Int
    suspend fun Context.saveInt(key: String, value: Int) = editPreference(
        intPreferencesKey(key), value
    )

    suspend fun Context.getInt(key: String, defaultValue: Int): Int = getPreference(
        intPreferencesKey(key), defaultValue
    )



    //save and get Double
    suspend fun Context.saveDouble(key: String, value: Double) = editPreference(doublePreferencesKey(key), value)

    suspend fun Context.getDouble(key: String, defaultValue: Double): Double = getPreference(doublePreferencesKey(key), defaultValue)



    //save and get Float
    suspend fun Context.saveFloat(key: String, value: Float) = editPreference(floatPreferencesKey(key), value)

    suspend fun Context.getFloat(key: String, defaultValue: Float): Float = getPreference(floatPreferencesKey(key), defaultValue)

}