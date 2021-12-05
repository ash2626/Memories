package com.ash2626.Memories.login

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventPickerModel : ViewModel() {

    private var _eventList = mutableMapOf<String,String>()

    fun getEventList(): MutableMap<String, String> {
        return _eventList
    }

    fun updateEventsList() {
        Log.d("memories-d", "EventPickerViewModel Created")
        viewModelScope.launch { dbEventsList() }
    }

    private suspend fun dbEventsList() {

        Log.d("memories-d", " DatabaseEventsList update started")

        withContext(Dispatchers.IO) {

            val db = Firebase.firestore

            db.collection("events")
                .get()
                .addOnSuccessListener { result ->
                    Log.d("memories-d", " Size " + result.size())

                    for (document in result) {
                        Log.d("memories-d", "${document.id} => ${document.data}")
                        document.get("identifier").toString()
                        document.get("Event").toString()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                }
        }
    }
}