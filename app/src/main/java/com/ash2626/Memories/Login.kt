package com.ash2626.Memories

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.ash2626.memories.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var spinner: Spinner
    private lateinit var loginNumber: EditText
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d("memories","Login Created")

        loginNumber = findViewById(R.id.loginNumber)
        loginNumber.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
            })

        spinner = findViewById(R.id.eventSpinner)
        spinner.setOnItemSelectedListener(this)
       // spinner.prompt="Select Event"

        // Create an ArrayAdapter using a simple spinner layout and
        val aa = ArrayAdapter.createFromResource(this,R.array.Events, android.R.layout.simple_spinner_item)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa

        anonymousSignin()

    }

    private fun anonymousSignin(){
        // Initialize Firebase Auth
        auth = Firebase.auth
        //Check if user is already signed in, if not show sign in page
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.d("memories", "User Already Signed In:success")
        }
        else {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("memories", "signInAnonymously:success")
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("memories", "signInAnonymously:failure", task.exception)
                    }
                }
        }
    }

    private fun startActivity(){
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(count>0) {
            Log.d("memories", "Spinner item selected = " + spinner.selectedItemPosition.toString())
            startActivity()
        }
        count++
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Log.d("memories", "Spinner item selected = " + spinner.selectedItemPosition.toString())
        startActivity()
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
}