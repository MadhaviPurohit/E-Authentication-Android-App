package com.miniprojectsemiii

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_logout
import kotlinx.android.synthetic.main.activity_otp_main.*

class OtpMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_main)

        val phone = intent.getStringExtra("phone_no")

        tv_phone_no.text = "Phone No. : $phone"

        btn_logout.setOnClickListener {
            //Log out form the app
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@OtpMainActivity, LoginActivity:: class.java))
            finish()
        }
    }
}