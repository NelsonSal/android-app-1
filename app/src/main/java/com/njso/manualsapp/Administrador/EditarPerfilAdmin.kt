package com.njso.manualsapp.Administrador

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.njso.manualsapp.R
import com.njso.manualsapp.databinding.ActivityEditarPerfilAdminBinding

class EditarPerfilAdmin : AppCompatActivity() {

    private lateinit var binding : ActivityEditarPerfilAdminBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()

        cargarInformacion()

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.BtnActualizarInfo.setOnClickListener {
            validarinformacion()
        }
    }

    private var nombres=""
    private fun validarinformacion() {
        nombres=binding.EtANombres.text.toString().trim()
        if (nombres.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese un nuevo nombre",Toast.LENGTH_SHORT).show()
        }else {
            actualizarinformacion()
        }

    }

    private fun actualizarinformacion() {
        progressDialog.setMessage("Actualizando información")
        val hashMap : HashMap<String,Any> = HashMap()
        hashMap["nombres"]="$nombres"
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Se actualizó su información",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "No se pudo actualizar debido a ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun cargarInformacion() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //Obtenemos la informacion en tiempo real
                    val nombre = "${snapshot.child("nombres").value}"
                    val imagen = "${snapshot.child("imagen").value}"

                    //Seteamos
                    binding.EtANombres.setText(nombre)

                    try {
                        Glide.with(applicationContext)
                            .load(imagen)
                            .placeholder(R.drawable.ic_imagen_perfil)
                            .into(binding.imgPerfilAdmin)

                    }catch (e:Exception){
                        Toast.makeText(applicationContext, "${e.message}",Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}