package com.ash2626.Memories

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ash2626.memories.R

class EventPickerFragment : Fragment() {

    private lateinit var viewModel: EventPickerModel
    private lateinit var eventList: MutableMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("memories-d", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(EventPickerModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment and find ref to UI component
        val view = inflater.inflate(R.layout.fragment_event_picker, container, false)
        val numericPassword = view.findViewById<EditText>(R.id.numberPassword)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val status = sharedPref?.getString("Status", "0")

        if (status == "Logged In") {
            //Navigate to camera fragment
            val navController = findNavController()
            navController.navigate(R.id.cameraFragment)

        } else {
            numericPassword?.setOnEditorActionListener() { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //check numeric code and launch startActivity() if correct

                    eventList = viewModel.getEventList()

                    if (eventList.containsKey(numericPassword.text.toString())) {
                        Log.d("memories-d", "Login Success")

                        //Save current event details to shared preferences
                        with(sharedPref!!.edit()) {
                            putString("Event", eventList.getValue(numericPassword.text.toString()))
                            putString("Identifier", numericPassword.text.toString())
                            putString("Status","Logged In")
                            apply()
                        }

                        //Navigate to camera fragment
                        val action =
                            EventPickerFragmentDirections.actionEventPickerFragmentToCameraFragment()
                        view.findNavController().navigate(action)

                    } else {
                        //TODO better way of dealing with login failure
                        Log.d("memories-d", "Login Failed")
                    }
                }
                false
            }
        }
        return view
    }
}