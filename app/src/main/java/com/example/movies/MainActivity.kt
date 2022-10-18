package com.example.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inflo mi Main Activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
// Inicializo mi navController, fragmentos y toolbar, junto con sus acciones
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.navigation.setupWithNavController(navController)

         setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navController)

    }
}