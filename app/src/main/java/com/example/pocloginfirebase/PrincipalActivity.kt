package com.example.pocloginfirebase

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.inject.Deferred
import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val bundle = intent.extras

        val email = bundle?.getString("email")
        val displayName = bundle?.getString("displayName")
        val photoURL = bundle?.getString("photoURL")
        val phoneNumber = bundle?.getString("phoneNumber")
        val isEmailVerified = bundle?.getString("isEmailVerified")
        TextViewEmail.setText(email)
        TextViewDisplayName.setText(displayName)
        TextViewPhoneNumber.setText(phoneNumber)
        Glide.with(this).load(photoURL).into(ImageViewPhotoURL)

        buttonSession.setOnClickListener(){
            signOut();
        }
    }

    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        // [END auth_fui_signout]
    }

}