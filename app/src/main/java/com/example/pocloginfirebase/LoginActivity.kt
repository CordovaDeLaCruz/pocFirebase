package com.example.pocloginfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 423
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        googleLogin()
    }

    fun googleLogin(){

        // Choose authentication providers
        val providers = arrayListOf(
            //AuthUI.IdpConfig.EmailBuilder().build(),
            //AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())
            //AuthUI.IdpConfig.FacebookBuilder().build())
            //AuthUI.IdpConfig.TwitterBuilder().build())


        google_button.setOnClickListener {
            // Create and launch sign-in intent
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Log.i("email", user!!.email.toString())
            Log.i("displayName", user!!.displayName.toString())
            Log.i("photoURL", user!!.photoUrl.toString())
            Log.i("phoneNumber", user!!.phoneNumber.toString())
            Log.i("isEmailVerified", user!!.isEmailVerified.toString())
            val intent = Intent(this, PrincipalActivity::class.java)
            intent.putExtra("email", user.email)
            intent.putExtra("displayName", user.displayName)
            intent.putExtra("photoURL", user.photoUrl)
            intent.putExtra("phoneNumber", user.phoneNumber)
            intent.putExtra("isEmailVerified", user.isEmailVerified)
            Toast.makeText(this, "Bienvenido ${user.displayName}", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            Toast.makeText(this, "Bienvenido ${response!!.error!!.errorCode}", Toast.LENGTH_SHORT).show()
        }
    }

}