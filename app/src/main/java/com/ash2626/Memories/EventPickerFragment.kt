package com.ash2626.Memories

import android.content.Context
import android.icu.util.TimeUnit
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.graphics.convertTo
import androidx.core.util.TimeUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ash2626.memories.R
import java.lang.String.format
import java.lang.System.currentTimeMillis
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration
import kotlin.time.toDuration

class EventPickerFragment : Fragment() {

    private val viewModel: EventPickerModel by activityViewModels()
    private lateinit var eventList: MutableMap<String, String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("memories-d", "Called updateEventsLIst")
        viewModel.updateEventsList()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment and find ref to UI component
        val view = inflater.inflate(R.layout.fragment_event_picker, container, false)
        val numericPassword = view.findViewById<EditText>(R.id.numberPassword)

        //get from shared prefs the last successful login
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val lastLogin = sharedPref?.getLong("lastLogin",0)

        //Work out time since last succesful togin
        val diff = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - lastLogin!!)
        val loginExpiration: String = getString(R.string.loginExpiration)
        val expirationInt: Int = loginExpiration.toInt()

        if (diff<=expirationInt) {
            //Navigate to camera fragment
            Log.d("memories-d", "Event already Logged In")
            val navController = findNavController()
            navController.navigate(R.id.cameraFragment)
        } else {
            numericPassword?.setOnEditorActionListener() { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    eventList = viewModel.getEventList()

                    if (eventList.containsKey(numericPassword.text.toString())) {
                        Log.d("memories-d", "Login Success")

                        //Save current event details to shared preferences
                        with(sharedPref!!.edit()) {
                            putString("Event", eventList.getValue(numericPassword.text.toString()))
                            putLong("lastLogin", System.currentTimeMillis())
                            apply()
                        }

                        //Navigate to camera fragment
                        val action =
                            EventPickerFragmentDirections.actionEventPickerFragmentToCameraFragment()
                        view.findNavController().navigate(action)

                    } else {
                        Log.d("memories-d", "Login Failed")
                        Toast.makeText(requireContext(),
                            "Login Failed. Please re-enter your event code.",
                            Toast.LENGTH_LONG).show()
                    }
                }
                false
            }
        }
        return view
    }
}