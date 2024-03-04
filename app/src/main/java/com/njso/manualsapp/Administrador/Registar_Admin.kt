package com.njso.manualsapp.Administrador

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.njso.manualsapp.MainActivity
import com.njso.manualsapp.R
import com.njso.manualsapp.databinding.ActivityRegistarAdminBinding



class Registar_Admin : AppCompatActivity() {

    private lateinit var binding : ActivityRegistarAdminBinding

    private lateinit var firebaseAuth :FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistarAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.IbRegresar.setOnClickListener {//para regresar a activity anterior
            onBackPressedDispatcher.onBackPressed()
        }

        binding.BtnRegistrarAdmin.setOnClickListener {
            ValidarInformacion()
        }
        binding.TxtTengoCuenta.setOnClickListener{
            startActivity(Intent(this@Registar_Admin, Login_Admin::class.java))
        }



    }


    var nombres = ""
    var email =""
    var password=""
    var r_password=""


    private fun ValidarInformacion() {
        nombres=binding.EtNombresAdmin.text.toString().trim()
        email=binding.EtEmailAdmin.text.toString().trim()
        password=binding.EtPasswordAdmin.text.toString().trim()
        r_password=binding.EtRPasswordAdmin.text.toString().trim()

        if (nombres.isEmpty()){
            binding.EtNombresAdmin.error="Introduzca nombres"
            binding.EtNombresAdmin.requestFocus()
        }
        else if (email.isEmpty()){
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
        else if (r_password.isEmpty()){
            binding.EtRPasswordAdmin.error="Repita la contraseña"
            binding.EtRPasswordAdmin.requestFocus()
        }
        else if (r_password != password){
            binding.EtRPasswordAdmin.error="Las contraseñas no coinciden"
            binding.EtRPasswordAdmin.requestFocus()
        }
        else {
            CrearCuentaAdmin(email,password)
        }

    }

    private fun CrearCuentaAdmin(email: String, password: String) {
        progressDialog.setMessage("Creando Cuenta")
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                AgregarInfoBD()

            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Ha fallado la creacion de cuenta debido a ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    private fun AgregarInfoBD() {
        progressDialog.setMessage("Guardando información...")
        val tiempo = System.currentTimeMillis()
        val uid = firebaseAuth.uid
        val datos_admin : HashMap<String, Any?> = HashMap()
        datos_admin["uid"]=uid
        datos_admin["nombres"]=nombres
        datos_admin["email"]=email
        datos_admin["rol"]="admin"
        datos_admin["tiempo_registro"]=tiempo
        datos_admin["imagen"]=""

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uid!!)
            .setValue(datos_admin)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Cuenta creada", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(this, MainActivity::class.java) )
                finishAffinity()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "No se puede guardar la información  debido a ${e.message}", Toast.LENGTH_SHORT)
                    .show()

            }





    }
}