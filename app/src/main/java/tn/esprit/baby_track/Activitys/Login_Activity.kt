package tn.esprit.baby_track.Activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.baby_track.R
import tn.esprit.baby_track.models.User
import tn.esprit.baby_track.utils.ApiInterface

const val PREF_NAME = "LOGIN_PREF_Bear"
const val IS_REMEMBRED = "IS_REMEMBRED"

class Login_Activity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var mailError: TextInputLayout
    lateinit var password: EditText
    lateinit var passwordError: TextInputLayout
    lateinit var loginButton: Button
    lateinit var rememberMe: CheckBox
    lateinit var forgotYourPassword : TextView
    lateinit var preference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        email = findViewById<EditText>(R.id.txtEmail)
        mailError = findViewById<TextInputLayout>(R.id.txtLayoutEmail)
        password = findViewById<EditText>(R.id.txtPassword)
        passwordError = findViewById<TextInputLayout>(R.id.txtLayoutPassword)

        loginButton = findViewById<Button>(R.id.login_button)
        rememberMe = findViewById<CheckBox>(R.id.Remember_Me)

        forgotYourPassword = findViewById(R.id.forgot_password)

        preference=getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        if (rememberMe.isChecked) {
            val editor = preference.edit()
            editor.apply {
                editor.putString("email", email.text.toString())
                editor.putString("password", password.text.toString())
                editor.putBoolean(IS_REMEMBRED, true)

            }.apply()
            clickLogin()
        }

        loginButton.setOnClickListener()
        {
            clickLogin()
        }

    }

    private fun clickLogin() {
        if (validate()) {
            val apiInterface = ApiInterface.create()
            val intent = Intent(this,Home::class.java)


            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

            apiInterface.seConnecter(email.text.toString(), password.text.toString())
                .enqueue(object : Callback<User> {

                    override fun onResponse(call: Call<User>, response: Response<User>) {


                        if (response.code()==200 )
                        {
                            Toast.makeText(this@Login_Activity, "Login Success", Toast.LENGTH_SHORT).show()
                            //startActivity(intent)
                        } else
                        {
                            Toast.makeText(this@Login_Activity, "User not found", Toast.LENGTH_SHORT)
                                .show()
                        }

                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@Login_Activity, "Connexion error!", Toast.LENGTH_SHORT)
                            .show()


                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    }

                })

        }
    }

    private fun validate():Boolean
    {
        var mail:Boolean=true
        var pswd:Boolean=true

        passwordError?.error =null
        mailError?.error =null

        if(email?.text!!.isEmpty())
        {
            mailError?.error="Please enter your e-mail !"
            mail=false
        }
        if(password?.text!!.isEmpty())
        {
            passwordError?.error="Please enter your password !"
            pswd=false
        }

        if (pswd===false || mail===false)
        {
            return false
        }
        return true
    }
}