package com.dag.odev2fmss

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dag.odev2fmss.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreManager = DataStoreManager(this@LoginActivity)
        view_actions()
    }

    /**
     * Login View Action Methods
     * Contains;
     * registerTextButton -> Goes To SignupActivity
     * forgotPasswordButton
     *
     */
    fun view_actions() {
        binding.registerTextButton.setOnClickListener {
            binding.enterUserNamePasswordText.text = "Enter Your Username & Password"
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.forgotPasswordButton.setOnClickListener {
            Toast.makeText(
                baseContext,
                "Reset Password Instructions Sent to Your Email",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.loginButton.setOnClickListener {
            getLogin()
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    /**
     * LOGIN METHOD
     * RETRIEVES USER DATA FROM DATASTORE
     * COMPARES LOGIN INFORMATION WITH USER DATA
     * IF VALID -> LOGIN
     */
    fun getLogin() {
        val user = binding.usernameTextInput.text.toString()
        val password = binding.passwordTextInput.text.toString()
        GlobalScope.launch(
            Dispatchers.IO
        ) {
            dataStoreManager.getFromDataStore().catch { e ->
                e.printStackTrace()
            }.collect {
                withContext(Dispatchers.Main) {
                    if (user == (it.name) && password == (it.password)) {
                        Toast.makeText(
                            baseContext,
                            "WELCOME BACK! Login Successful ${it.name} ",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.enterUserNamePasswordText.text = "${it.name}"

                    } else {
                        binding.usernameTextInput.error = "Enter Valid Username"
                        binding.passwordTextInput.error = "Enter Valid Password"
                    }
                }
            }
        }
    }
}
