package com.njso.manualsapp.Administrador

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.njso.manualsapp.MainActivity
import com.njso.manualsapp.R
import com.njso.manualsapp.databinding.ActivityAgregarCategoriaBinding
import java.util.HashMap

class Agregar_Categoria : AppCompatActivity() {

    private lateinit var binding : ActivityAgregarCategoriaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarCategoriaBinding.inflate(layoutInflater)

        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.IbRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.AgregarCategoriaBD.setOnClickListener {
            Validardatos()
        }

    }

    private var categoria =""
    private fun Validardatos() {
        categoria=binding.EtCategoria.text.toString().trim()
        if (categoria.isEmpty()){
            Toast.makeText(applicationContext,"Ingrese una Categoria", Toast.LENGTH_SHORT).show()
        }else{
            AgregarCategoriaBD()
        }
    }

    private fun AgregarCategoriaBD() {
        progressDialog.setMessage("Agregando Categoria")
        progressDialog.show()

        val tiempo = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"]= "$tiempo"
        hashMap["categoria"] = categoria
        hashMap["tiempo"] = tiempo
        hashMap["uid"]= "${firebaseAuth.uid}"

        val ref = FirebaseDatabase.getInstance().getReference("Categorias")
        ref.child("$tiempo")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"Se aregó la categoria a la BD", Toast.LENGTH_SHORT).show()
                binding.EtCategoria.setText("")
                startActivity(Intent(this@Agregar_Categoria, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"No se aregó la categoria debido a ${e.message}", Toast.LENGTH_SHORT).show()


            }

    }
}