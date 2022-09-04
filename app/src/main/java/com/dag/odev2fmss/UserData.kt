package com.dag.odev2fmss

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val DataStore_NAME = "USER_DATASTORE"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStore_NAME)


/**
 * DATA STORE FOR USERS
 *
 * @property context
 */
class DataStoreManager(val context: Context) {

    companion object {

        val NAME = stringPreferencesKey("NAME")
        val EMAIL = stringPreferencesKey("EMAIL")
        val PASSWORD = stringPreferencesKey("PASSWORD")

    }

    /**
     * SAVES USER DATA
     *
     * @param user
     */
    suspend fun savetoDataStore(user: User) {
        context.dataStore.edit { users ->

            users[NAME] = user.name
            users[EMAIL] = user.email
            users[PASSWORD] = user.password

        }
    }

    /**
     * USER DATA GETTER
     *
     * @return
     */
    fun getFromDataStore() = context.dataStore.data.map { user ->
        User(
            name = user[NAME] ?: "",
            email = user[EMAIL] ?: "",
            password = user[PASSWORD] ?: ""
        )
    }
}






