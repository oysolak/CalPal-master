package com.example.calpal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calpal.model.RecyclerViewFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RecyclerViewFragment())
                .commit()
        }
    }
}

