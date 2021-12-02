package com.d2k.weatherforecasting.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.d2k.weatherforecasting.ui.home.activity.MainActivity
import com.d2k.weatherforecasting.R
import com.d2k.weatherforecasting.databinding.ActivityLogin2Binding
import com.d2k.weatherforecasting.extension.launchActivity
import com.d2k.weatherforecasting.extension.showToast
import com.d2k.weatherforecasting.extension.showToastForValid
import com.d2k.weatherforecasting.utils.Constants
import com.d2k.weatherforecasting.utils.PrefUtils
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    
    lateinit var activityLogin2Binding: ActivityLogin2Binding
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    lateinit var prefUtils : PrefUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLogin2Binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(activityLogin2Binding.root)

        prefUtils = PrefUtils(this)

        activityLogin2Binding.txtLogin.setOnClickListener {
            validateUserCredentials();
        }

        if (prefUtils.getBoolean(Constants.isLogin)!=null){
            if (prefUtils.getBoolean(Constants.isLogin)){
                launchActivity<MainActivity>(){
                    finish()
                }
            }
        }

        if (prefUtils.getString(Constants.usernameKey)!=null && prefUtils.getString(Constants.passwordKey)!=null){
            activityLogin2Binding.etUsername.setText(prefUtils.getString(Constants.usernameKey))
            activityLogin2Binding.etPassword.setText(prefUtils.getString(Constants.passwordKey))
        }
    }

    private fun validateUserCredentials() {
        if (TextUtils.isEmpty(activityLogin2Binding.etUsername.text.toString()) ||
            TextUtils.isEmpty(activityLogin2Binding.etPassword.text.toString())){
            showToast(resources.getString(R.string.please_enter_username_and_password))
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(activityLogin2Binding.etUsername.text.toString()).matches()){
            showToastForValid(resources.getString(R.string.valid_user_name))
            return
        }
        if (!Pattern.compile(PASSWORD_PATTERN).matcher(activityLogin2Binding.etPassword.text.toString()).matches()){
            showToastForValid(resources.getString(R.string.valid_password))
            return
        }

        if(activityLogin2Binding.etUsername.text.toString().equals(Constants.userName) && activityLogin2Binding.etPassword.text.toString().equals(Constants.password)){
            prefUtils.setString(Constants.usernameKey,activityLogin2Binding.etUsername.text.toString())
            prefUtils.setString(Constants.passwordKey,activityLogin2Binding.etPassword.text.toString())
            prefUtils.setBoolean(Constants.isLogin,true)
            launchActivity<MainActivity>(){
                finish()
            }
        }
        else
            showToastForValid(resources.getString(R.string.valid_username_password))
    }
}