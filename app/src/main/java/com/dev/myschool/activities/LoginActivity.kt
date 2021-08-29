package com.dev.myschool.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.dev.myschool.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnGoogleLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var tvForgotPwd: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPwd: EditText
    private lateinit var tvWarning: TextView
    private lateinit var progress: CircularProgressIndicator
    private lateinit var layout: ScrollView

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var database: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.decfault_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btnLogin.setOnClickListener {
            login()
        }

        tvForgotPwd.setOnClickListener {
            showPasswordRecoverDialog()
        }

        btnGoogleLogin.setOnClickListener {
//            signIn()
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initViews() {
        btnLogin = findViewById(R.id.signIn_login_btn)
        btnGoogleLogin = findViewById(R.id.signin_googleLogin_btn)
        tvRegister = findViewById(R.id.signIn_register_txt)
        tvForgotPwd = findViewById(R.id.signIn_forgotPwd_txt)
        etEmail = findViewById(R.id.signIn_email_et)
        etPwd = findViewById(R.id.signIn_pwd_et)
        tvWarning = findViewById(R.id.signIn_warning_tv)
        progress = findViewById(R.id.login_progress)
        layout = findViewById(R.id.login_rootLayout)
    }

    private fun login() {
        progress.visibility = View.VISIBLE

        val email = etEmail.text.toString().trim()
        val pwd = etPwd.text.toString().trim()

        auth.signInWithEmailAndPassword(email, pwd)
            .addOnCompleteListener(this) {
                progress.visibility = View.GONE
                if (it.isSuccessful) {
                    tvWarning.visibility = View.INVISIBLE
                    Log.d(TAG, "signInWithEmail:success")
                    startHomeActivity()

                } else {
                    tvWarning.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener(this) {
                tvWarning.visibility = View.VISIBLE
                progress.visibility = View.GONE
                Toast.makeText(this, "" + it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun recoverPassword() {

    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in failed", e)
                }
            } else {
                Log.w(TAG, task.exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "registerUser:success")
                    val user = auth.currentUser
                    if (user != null) {
                        Log.d(TAG, "User: ${user.uid}")
//                        storeUserInfo(user)
                    }
                } else {
                    Log.d(TAG, "registerUser:failure", it.exception)
                    Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "" + it.message, Toast.LENGTH_SHORT).show()
            }
    }
//
//    private fun storeUserInfo(user: FirebaseUser) {
//        val newUser = arguments?.let {
//            user.email?.let { it1 ->
//                User(
//                    user.uid,
//                    it1, it.getInt("UserType")
//                )
//            }
//        }
//
//        if (newUser != null) {
//            database.collection("Users").document(user.uid).set(newUser)
//                .addOnSuccessListener {
//                    Toast.makeText(activity, "Account created Successfully", Toast.LENGTH_SHORT)
//                        .show()
//                    startHomeActivity()
//                }
//                .addOnFailureListener {
//                    Log.d(SignupFragment.TAG, "Error adding document" + it.message)
//                }
//        }
//    }

    /**
     * Password recovery
     */

    private fun showPasswordRecoverDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Recover Password")

        //set layout
        val linearLayout = LinearLayout(this)

        //views to be set in the dialog
        val emailEt = EditText(this)
        emailEt.hint = "Email"
        emailEt.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        emailEt.minEms = 16
        linearLayout.addView(emailEt)
        linearLayout.setPadding(10, 10, 10, 10)
        builder.setView(linearLayout)

        // recover button
        builder.setPositiveButton(
            "Recover"
        ) { _, i ->
            val inputEmail = emailEt.text.toString().trim { it <= ' ' }
            if (inputEmail != "") {
                beginRecovery(inputEmail)
            } else {
                Snackbar.make(layout, "Please enter email..", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getColor(R.color.color_primary))
                    .show()
            }
        }

        // cancel button
        builder.setNegativeButton(
            "Cancel"
        ) { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.create().show()
    }

    private fun beginRecovery(email: String) {
        progress.visibility = View.VISIBLE
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                progress.visibility = View.GONE
                if (it.isSuccessful)
                    Snackbar.make(layout, "Email Sent", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getColor(R.color.color_primary))
                        .show()
                else
                    Snackbar.make(layout, "Failed!", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getColor(R.color.color_primary))
                        .show()
            }
            .addOnFailureListener {
                progress.visibility = View.GONE
                Snackbar.make(layout, "Failed!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getColor(R.color.color_primary))
                    .show();
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null)
            startHomeActivity()
    }


    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 101
    }
}