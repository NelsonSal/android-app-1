package com.njso.manualsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.njso.manualsapp.Administrador.Registar_Admin
import com.njso.manualsapp.databinding.ActivityElegirRolBinding
import com.njso.manualsapp.databinding.ActivityMainBinding

private lateinit var binding: ActivityElegirRolBinding
class Elegir_rol : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElegirRolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnRolAdministrador.setOnClickListener {
            //Toast.makeText(applicationContext,"Rol Administrador", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Elegir_rol, Registar_Admin::class.java))
        }
        binding.BtnRolCliente.setOnClickListener {
            Toast.makeText(applicationContext,"Rol Cliente", Toast.LENGTH_SHORT).show()
        }

    }
}