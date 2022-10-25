package com.pseedk.thenewsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pseedk.thenewsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_splash)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            setContentView(binding.root)
            binding.bottomNavMenu.setupWithNavController(
                navController = binding.navHostFragment.findNavController()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}