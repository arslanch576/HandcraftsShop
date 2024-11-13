package com.coderobust.handcraftsshop

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.coderobust.handcraftsshop.databinding.ActivityLoginBinding
import com.coderobust.handcraftsshop.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(FirebaseAuth.getInstance().currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            return
        }

        binding.loginbtn.setOnClickListener {
            val email=binding.email.editText?.text.toString()
            val password=binding.password.editText?.text.toString()

            if(!email.contains("@")){
                Toast.makeText(this,"Invalid Email",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.length<6){
                Toast.makeText(this,"Password must be atleast 6 characters",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val progressDialog=ProgressDialog(this)
            progressDialog.setMessage("Please wait while we check your credentials...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener {
                progressDialog.dismiss()
                if (it.isSuccessful) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Login Failed ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}