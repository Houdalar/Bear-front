package tn.esprit.baby_track

import android.os.Bundle

import android.util.Patterns
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.baby_track.utils.RetrofitInterface


class SignUp : AppCompatActivity() {

    lateinit var retrofitInterface: RetrofitInterface


    lateinit var parentName: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var okBtn: Button
    lateinit var errorName: TextInputLayout
    lateinit var errorEmail: TextInputLayout
    lateinit var errorPassword: TextInputLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        okBtn =findViewById(R.id.OkBtn)
        parentName =findViewById(R.id.ParentNameText)
        email = findViewById(R.id.EmailText)
        password = findViewById(R.id.PasswordText)

        errorName=findViewById(R.id.ParentNameTextField)
        errorEmail=findViewById(R.id.EmailTextField)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        okBtn.setOnClickListener { signUp() }
    }

    private fun signUp() {
        if (validate()) {
            val retrofitInterface = RetrofitInterface.create()
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

            retrofitInterface.executeSignup(
                parentName.text.toString(),
                email.text.toString(),
                password.text.toString()
            ).enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.code() == 200) {
                        Toast.makeText(
                            this@SignUp,
                            "Signed up successfully", Toast.LENGTH_LONG
                        ).show()
                    } else if (response.code() == 400) {
                        Toast.makeText(
                            this@SignUp,
                            "Already registered", Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    Toast.makeText(
                        this@SignUp, t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
}
    private fun validate(): Boolean {
        var Name=true
        var Email=true
        var Pwd=true

        errorName?.error =null
        errorEmail?.error =null
        errorPassword?.error =null

        if(parentName?.text!!.isEmpty())
        {
            errorName?.error="This field must not be empty !"
            Name=false
        }

        if(email?.text!!.isEmpty())
        {
            errorEmail?.error="This field must not be empty !"
            Email=false
        }

        if(password?.text!!.isEmpty())
        {
            errorPassword?.error="Must not be empty !"
            Pwd=false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email?.text!!).matches() && parentName?.text!!.isNotEmpty())
        {

            errorEmail?.error="Email not valid !"
            Email= false
        }
        if (Name===false || Email===false||Pwd===false )
        {
            return false
        }
        return true
    }
}

