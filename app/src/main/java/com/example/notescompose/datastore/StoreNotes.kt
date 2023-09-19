package com.example.notescompose.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreNotes(private val context: Context) {

    //para garantir que vai ter so uma instancia vou definir um companion object
    //Criando o datastore
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val NOTES_KEY = stringPreferencesKey("notes_key")
    }

    //para recuperar a anotacao

    val getNotesTyped: Flow<String> = context.dataStore.data
        //recupera minhas preferencias
        .map { preferences ->
            preferences[NOTES_KEY] ?: "" //?: "" isso p/ caso nao consiga recuperar nao quebre
        }

    //metodo pra salvar a anotacao
    //Pra ser asssincrona tem q ser uma fun suspend

    suspend fun saveNotes(notes: String){
        //edit serve tanto pra salvar como para atualizar os dados
        context.dataStore.edit { preferences ->
            preferences[NOTES_KEY] = notes

        }
    }
}