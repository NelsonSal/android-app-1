package com.njso.manualsapp.Fragmentos_Admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.core.Context
import com.njso.manualsapp.Elegir_rol
import com.njso.manualsapp.R
import com.njso.manualsapp.databinding.FragmentAdminCuentaBinding
import android.content.Context //14 6:15 Esta funcion dara error por que se importo
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
        binding.CerrarSesionAdmin.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(context, Elegir_rol::class.java))
            activity?.finishAffinity()

        }
    }

}