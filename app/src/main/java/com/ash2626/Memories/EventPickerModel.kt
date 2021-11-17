package com.ash2626.Memories

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventPickerModel: ViewModel() {

    private var _eventList = mutableMapOf<String,String>()
    lateinit var event: String

    init{
        Log.d("memories-d","EventPickerViewModel Created")
        viewModelScope.launch { dbEventsList() }
    }


   fun setEventList(event: String, identifier: String){
        _eventList.put(event, identifier)
   }

   fun getEventList(): MutableMap<String,String> {
       return _eventList
   }

    suspend fun dbEventsList() {

        Log.d("memories-d", " DatabaseEventsList update started")

        withContext(Dispatchers.IO){

            val db = Firebase.firestore

            db.collection("events")
                .get()
                .addOnSuccessListener { result ->
                    Log.d("memories-d", " Size " + result.size())

                    for (document in result) {
                        Log.d("memories-d", "${document.id} => ${document.data}")
                        setEventList(document.get("Event").toString(), document.get("identifier").toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                }
        }

    }
}