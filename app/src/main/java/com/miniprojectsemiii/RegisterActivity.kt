package com.miniprojectsemiii

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var prg: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        tv_login.setOnClickListener {
            onBackPressed()
        }

        firebaseAuth = FirebaseAuth.getInstance()
        prg = ProgressDialog(this)

        btn_register.setOnClickListener {
            when {
                TextUtils.isEmpty(et_name.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_phone_no.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter Mobile no.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your Email Id",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val name: String = et_name.text.toString()
                    val phoneNumber: String = et_register_phone_no.text.toString().trim { it <= ' ' }
                    val email: String = et_register_email.text.toString().trim { it <= ' ' }
                    val password: String = et_register_password.text.toString().trim { it <= ' ' }
                    val auth = FirebaseAuth.getInstance()


                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            //if the registration is successfully done

                            if (task.isSuccessful) {

                                //Firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Registration Successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                                checkEmail()

                            } else {
                                //If the registering is not successful then show error message
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                }
            }
        }
    }

    fun checkEmail() {
        val firebaseUser = firebaseAuth?.currentUser
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this,
                    "Verification Email sent",
                Toast.LENGTH_SHORT
                ).show()

                /**
                 * Here the new user is automatically signed in
                 * so we sign out and send the user to the Login screen
                 */

                firebaseAuth?.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                Toast.makeText(this,
                    "Error Occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

/*if (task.isSuccessful) {

    //Firebase registered user
    val firebaseUser: FirebaseUser = task.result!!.user!!

    Toast.makeText(
        this@RegisterActivity,
        "Registration Successful",
        Toast.LENGTH_SHORT
    ).show()

    /**
     * Here the new user is automatically signed in
     * so we sign out and send the user to main screen with user id
     * and email that the user has used for registration
     */

    val intent =
        Intent(this@RegisterActivity, MainActivity::class.java)
    intent.flags =
        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    intent.putExtra("user_id", firebaseUser.uid)
    intent.putExtra("email_id", email)
    startActivity(intent)
    finish()
} else {
    //If the registering is not successful then show error message
    Toast.makeText(
        this@RegisterActivity,
        task.exception!!.message.toString(),
        Toast.LENGTH_SHORT
    ).show()
}*/