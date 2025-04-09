package com.example.homify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.search.SearchBar

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val searchBar = findViewById<SearchBar>(R.id.searchBar)

        // Set up search functionality
        searchBar.setOnClickListener {
            // Open search activity/fragment
        }

        // Bottom navigation setup
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Handle home click
                    true
                }
                R.id.nav_notifications -> {
                    // Handle notifications click
                    true
                }
                R.id.nav_ideabooks -> {
                    // Handle ideabooks click
                    true
                }
                R.id.nav_profile -> {
                    // Handle profile click
                    true
                }
                else -> false
            }
        }
    }
}