package com.example.apprestaurante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.apprestaurante.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize FireBase//

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        //Assign methods to buttons//
        binding.login.setOnClickListener{
        login()
        }

        binding.register.setOnClickListener{
            register()
        }



    }


    private fun update (user: FirebaseUser?){
        if (user!= null){
            val intent = Intent (this, Principal::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart(){
        super.onStart()
        val user = auth.currentUser
        update(user)
    }


    private fun login (){
          val email = binding.editTextTextEmailAddress.text.toString()
          val password = binding.editTextTextEmailAddress.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
          task ->
          if (task.isSuccessful){
              val user = auth.currentUser
              Log.d("User login", "Success")
              update(user)

          }else{
              Log.d("User login", "Fail")
              Toast.makeText(baseContext, "Fail", Toast.LENGTH_LONG).show()
              update(null)

          }
        }
    }

    private fun register(){
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextEmailAddress.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->
            if (task.isSuccessful){
                val user = auth.currentUser
                Log.d("User Register", "Success")
                update(user)

            }else{
                Log.d("User Register", "Fail")
                Toast.makeText(baseContext, "Fail", Toast.LENGTH_LONG).show()
                update(null)
            }
        }
    }
}