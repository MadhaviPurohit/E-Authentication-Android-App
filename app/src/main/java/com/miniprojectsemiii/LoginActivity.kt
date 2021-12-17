package com.miniprojectsemiii

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_register.setOnClickListener {

            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        tv_forgot_password.setOnClickListener {

            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }

        btn_login.setOnClickListener {

            when{
                TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter your Email Id",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_login_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = et_login_email.text.toString().trim { it <= ' ' }
                    val password: String = et_login_password.text.toString().trim { it <= ' ' }

                    //Log in using Firebase
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            //if the registration is successfully done
                            if (task.isSuccessful) {

                                verifyEmail()


                            } else {
                                //If the login is not successful then show error message
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                }
            }
        }
        btn_proceed_otp.setOnClickListener {

            startActivity(Intent(this@LoginActivity, OtpActivity::class.java))
        }
    }
    fun verifyEmail() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val vemail = firebaseUser?.isEmailVerified
        val email: String = et_login_email.text.toString().trim { it <= ' ' }
        val password: String = et_login_password.text.toString().trim { it <= ' ' }

        startActivity(Intent(this, LoginActivity::class.java))
        if(vemail!!){

            Toast.makeText(
                this@LoginActivity,
                "Successfully Logged in",
                Toast.LENGTH_SHORT
            ).show()

            val intent =
                Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(
                "user_id",
                FirebaseAuth.getInstance().currentUser!!.uid
            )
            intent.putExtra("email_id", email)
            startActivity(intent)
            finish()
        }
        else{
            Toast.makeText(this,
                "Please verify your Email",
                Toast.LENGTH_SHORT
            ).show()
            firebaseAuth?.signOut()
        }
    }
}