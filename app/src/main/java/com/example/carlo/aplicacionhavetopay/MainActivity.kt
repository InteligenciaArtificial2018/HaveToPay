package com.example.carlo.aplicacionhavetopay

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener {
            val intent = Intent(this, Agregar::class.java)
            startActivity(intent)
            Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show()
        }

    }
}
