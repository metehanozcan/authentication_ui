package com.dag.odev2fmss

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dag.odev2fmss.databinding.ActivitySignupBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    lateinit var dataStoreManager: DataStoreManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this@SignupActivity)
        signUp()
    }


    /**
     * Method that takes member data from View ->
     * if the data is valid, it calls the Save Data method for send it to the DataManager
     *
     *
     */
    fun signUp() {
        binding.signupButton.setOnClickListener {
            var name = binding.createUsernameTextInput.text.toString()
            var email = binding.createEmailTextInput.text.toString()
            var password = binding.createPasswordTextInput.text.toString()
            if (isValid()) {
                Toast.makeText(
                    baseContext, "Enter Valid Username Or Password", Toast.LENGTH_LONG
                ).show()
            } else {
                saveData()

                Toast.makeText(
                    baseContext,
                    "Signup Successful $name,\n Details sent to $email. Welcome! ",
                    Toast.LENGTH_LONG
                ).show()
                onBackPressed()
                finish()
            }

        }

        binding.backButton.setOnClickListener {
            onBackPressed()

        }
    }

    /**
     *Saves User Data to dataStoreManager
     *
     */
    private fun saveData() {
        GlobalScope.launch(Dispatchers.IO) {
            dataStoreManager.savetoDataStore(
                user = User(
                    name = binding.createUsernameTextInput.text.toString(),
                    email = binding.createEmailTextInput.text.toString(),
                    password = binding.createPasswordTextInput.text.toString()
                )
            )
        }
    }

    /**
     * Checks All User Register Data
     *
     * @returns Bool --> Is User Data Valid
     */
    fun isValid(): Boolean {
        var isvalidusername = isValidUsername()
        var isvalidpassword = isValidPassword()
        var isvalidemail = isValidEmail()
        return isvalidusername || isvalidpassword || isvalidemail
    }

    /**
     * checks if username valid is valid
     * if not setsError message in TextInput
     * @returns Bool --> Is Username Valid
     */
    fun isValidUsername(): Boolean {
        if (binding.createUsernameTextInput.text.toString().length < 8) {
            binding.createUsernameTextInput.error = "Enter at least 8 Digit Username"
            return true
        }
        return false
    }

    /**
     * checks if password is valid
     * if not setsError message in TextInput
     * @returns Bool--> Is Password Valid
     */
    fun isValidPassword(): Boolean {
        if (binding.createPasswordTextInput.text.toString().length < 6) {
            binding.createPasswordTextInput.error = "Enter at least 6 Digit Password"
            return true
        }
        return false
    }

    /**
     * checks if email is valid
     * if not setsError message in TextInput
     * @returns Bool--> Is email Valid
     */
    fun isValidEmail(): Boolean {
        if (binding.createEmailTextInput.text.toString().length < 8) {
            binding.createEmailTextInput.error = "Enter valid Email"
            return true
        }
        return false
    }

}


