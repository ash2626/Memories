package com.ash2626.Memories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.navigation.findNavController
import com.ash2626.memories.R

/**
 * A simple [Fragment] subclass.
 * Use the [EventPickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventPickerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_event_picker, container, false)
        var numericPassword = view.findViewById<EditText>(R.id.numberPassword)

        numericPassword?.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                //check numeric code and launch startActivity() if correct
                Log.d("memories", "Login Success")
                val action = EventPickerFragmentDirections.actionEventPickerFragmentToCameraFragment()
                view.findNavController().navigate(action)
                true
            } else {
                Log.d("memories", "Login Failed")
                false
            }
        }

        return view
    }

}