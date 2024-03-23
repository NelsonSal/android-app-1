package com.njso.manualsapp.Fragmentos_Admin

//import com.google.firebase.database.core.Context
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.njso.manualsapp.Administrador.MisFunciones
import com.njso.manualsapp.Elegir_rol
import com.njso.manualsapp.R
import com.njso.manualsapp.databinding.FragmentAdminCuentaBinding
                                //la libreria Context errada (la de firebase en lugar de Android


class Fragment_admin_cuenta : Fragment() {

    private lateinit var binding : FragmentAdminCuentaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mContext : Context


    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminCuentaBinding.inflate(layoutInflater,container, false)
        // return inflater.inflate(R.layout.fragment_admin_cuenta, container, false) 14 4:36
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        cargarInformacion()
        binding.CerrarSesionAdmin.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(context, Elegir_rol::class.java))
            activity?.finishAffinity()

        }
    }

    private fun cargarInformacion() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child("${firebaseAuth.uid}")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //Obtener Datos del Usuario Actual -administrador
                    val nombres="${snapshot.child("nombres").value}"
                    val email="${snapshot.child("email").value}"
                    var t_registro="${snapshot.child("tiempo_registro").value}"
                    val rol="${snapshot.child("rol").value}"
                    val imagen="${snapshot.child("imagen").value}"

                    if(t_registro=="null"){
                        t_registro="0"
                    }
                    //Convertimos a formato fecha el t_registro
                    val formato_fecha=MisFunciones.formatoTiempo(t_registro.toLong())
                    //Seteamos la información
                    binding.TxtNombresAdmin.text=nombres
                    binding.TxtEmailAdmin.text=email
                    binding.TxtTiempoRegistroAdmin.text=formato_fecha
                    binding.TxtRolAdmin.text=rol

                    //Seteo de la Imagen
                    try {
                        Glide.with(mContext)
                            .load(imagen)
                            .placeholder(R.drawable.ic_imagen_perfil)
                            .into(binding.imgPerfilAdmin)

                    }catch (e:Exception){
                        Toast.makeText(mContext,"${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

}