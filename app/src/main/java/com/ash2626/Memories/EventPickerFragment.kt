package com.ash2626.Memories

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
import com.ash2626.memories.R

class EventPickerFragment : Fragment() {

    private lateinit var viewModel: EventPickerModel
    private lateinit var eventList: MutableMap<String,String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("memories-d", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(EventPickerModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment and find ref to UI component
        var view = inflater.inflate(R.layout.fragment_event_picker, container, false)
        var numericPassword = view.findViewById<EditText>(R.id.numberPassword)





        numericPassword?.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                //check numeric code and launch startActivity() if correct

                eventList = viewModel.getEventList()
                for ((event,identifier) in eventList) {
                    Log.d("memories-d","value of $event is $identifier")
                    Log.d("memories-d","value of numeric password is "+ numericPassword.text)
                    if(identifier == numericPassword.text.toString())
                    {
                        Log.d("memories-d", "Login Success")
                        viewModel.event = event
                        val action = EventPickerFragmentDirections.actionEventPickerFragmentToCameraFragment()
                        view.findNavController().navigate(action)
                    }
                    else{
                        Log.d("memories-d", "Login Failed")
                    }
                }
                true
            }
            false
        }

        return view

    }



}