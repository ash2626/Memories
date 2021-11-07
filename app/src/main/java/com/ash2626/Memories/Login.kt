package com.ash2626.Memories

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.ash2626.memories.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("memories","Login Created")

        /*spinner = findViewById(R.id.eventSpinner)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("memories", "Spinner item selected = " + spinner.selectedItemPosition.toString())
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(count>0){
                    Log.d("memories", "Spinner item selected = " + spinner.selectedItemPosition.toString())
                    event = spinner.selectedItem.toString()
                }
                count++
            }
        }

        // Create an ArrayAdapter using a simple spinner layout and
        val aa = ArrayAdapter.createFromResource(this,R.array.Events, android.R.layout.simple_spinner_item)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa*/

        auth = Firebase.auth
        //Check if user is already signed in, if not show sign in page
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.d("memories", "User Already Signed In:success")
            launchNextFragment()
        }
        else {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build())

            // Create and launch sign-in intent
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)
        }
    }

    /*private fun dbEventsList() {

        Log.d("memories", " LoginActivityModel Created")

        val db = Firebase.firestore

        db.collection("events")
            .get()
            .addOnSuccessListener { result ->
                Log.d("memories", " Size " + result.size())

                for (document in result) {
                    Log.d("memories", "${document.id} => ${document.data}")
                    //viewModel.setEventsList(document.get("Event").toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }*/

    private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            Log.d("memories", "User Sign In:success")
            launchNextFragment()
        } else {
            Log.w("memories", "signInFailed:failure")
        }
    }

    private fun launchNextFragment(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}