package com.njso.manualsapp.Administrador

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.njso.manualsapp.MainActivity
import com.njso.manualsapp.R
import com.njso.manualsapp.databinding.ActivityLoginAdminBinding

class Login_Admin : AppCompatActivity() {

    private lateinit var binding : ActivityLoginAdminBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.IbRegresar.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        binding.BtnLoginAdmin.setOnClickListener{
            ValidarInformacion()
        }
    }

    private var email =""
    private var password=""
    private fun ValidarInformacion() {
        email=binding.EtEmailAdmin.text.toString().trim()
        password=binding.EtPasswordAdmin.text.toString().trim()

        if (email.isEmpty()){
            binding.EtEmailAdmin.error="Introduzca email"
            binding.EtEmailAdmin.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtEmailAdmin.error="email no es válido"
            binding.EtEmailAdmin.requestFocus()
        }
        else if (password.isEmpty()){
            binding.EtPasswordAdmin.error="Introduzca la contraseña"
            binding.EtPasswordAdmin.requestFocus()
        }
        else if (password.length < 6){
            binding.EtPasswordAdmin.error= "Contraseña debe ser mínimo 6 caracteres"
            binding.EtPasswordAdmin.requestFocus()
        }
        else{
            loginAdmin()
        }

    }

    private fun loginAdmin() {
        progressDialog.setMessage("Iniciando Sesión")
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@Login_Admin, MainActivity::class.java))
                finishAffinity()

            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"No se pudo iniciar sesión debido a ${e.message}",
                Toast.LENGTH_SHORT).show()

            }

    }


}