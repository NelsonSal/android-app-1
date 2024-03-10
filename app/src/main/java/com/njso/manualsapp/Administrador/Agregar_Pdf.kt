package com.njso.manualsapp.Administrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.njso.manualsapp.R
import com.njso.manualsapp.databinding.ActivityAgregarPdfBinding

class Agregar_Pdf : AppCompatActivity() {

    private lateinit var binding : ActivityAgregarPdfBinding
    private lateinit var categoriaArrayList: ArrayList<ModeloCategoria>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CargarCategorias()

        binding.IbRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.TvCategoriaLibro.setOnClickListener{
            SeleccionarCat()
        }
    }

    private fun CargarCategorias() {
        categoriaArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Categorias").orderByChild("categoria")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                categoriaArrayList.clear()
                for (ds in snapshot.children){
                    val modelo = ds.getValue(ModeloCategoria::class.java)
                    categoriaArrayList.add(modelo!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    private var id_categoria=""
    private var titulo_categoria=""
    private fun SeleccionarCat(){
        val categoriasArray = arrayOfNulls<String>(categoriaArrayList.size)
        for (i in categoriaArrayList.indices){
            categoriasArray[i]= categoriaArrayList[i].categoria
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccionar categoría")
            .setItems(categoriasArray){dialog, which->
                id_categoria = categoriaArrayList[which].id
                titulo_categoria = categoriaArrayList[which].categoria
                binding.TvCategoriaLibro.text = titulo_categoria
            }
            .show()
    }
}