package com.njso.manualsapp.Administrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.njso.manualsapp.R

class ListaPdfAdmin : AppCompatActivity() {

    private var idCategoria=""
    private var tituloCategoria=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pdf_admin)
        val intent = intent
        idCategoria=intent.getStringExtra("idCategoria")!!
        tituloCategoria=intent.getStringExtra("tituloCategoria")!!

    }
}