package com.dag.odev2fmss

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dag.odev2fmss.databinding.ActivityMainBinding

/**
 * MainActivity --> LoginActivity
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.joinButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}