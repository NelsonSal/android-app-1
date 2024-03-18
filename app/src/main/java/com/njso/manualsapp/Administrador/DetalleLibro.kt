package com.njso.manualsapp.Administrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.njso.manualsapp.R
import com.njso.manualsapp.databinding.ActivityDetalleLibroBinding

class DetalleLibro : AppCompatActivity() {

    private lateinit var binding : ActivityDetalleLibroBinding
    private var idLibro =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idLibro=intent.getStringExtra("idLibro")!!

        binding.IbRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()

        }

        cargarDetalleLibro()
    }

    private fun cargarDetalleLibro() {
        val ref = FirebaseDatabase.getInstance().getReference("Libros")
        ref.child(idLibro)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //Obtenemos la info del libro a traves del id
                    val categoria = "${snapshot.child("categoria").value}"
                    val contadorDescargas = "${snapshot.child("contadorDescargas").value}"
                    val contadorVistas = "${snapshot.child("contadorVistas").value}"
                    val descripcion = "${snapshot.child("descripcion").value}"
                    val tiempo = "${snapshot.child("tiempo").value}"
                    val titulo = "${snapshot.child("titulo").value}"
                    val url = "${snapshot.child("url").value}"

                    //Formato de tiempo
                    val fecha = MisFunciones.formatoTiempo(tiempo.toLong())
                    //Obtenemos nombre de la categoria
                    MisFunciones.CargarCategoria(categoria, binding.categoriaD)
                    //Cargamos la miniatura del libro y contador de páginas
                    MisFunciones.CargarPdfUrl("$url","$titulo",binding.VisualizadorPDF,
                        binding.progressBar, binding.paginasD)
                    //Cargamos el tamaño
                    MisFunciones.CargarTamanioPdf("$url","$titulo",binding.tamanioD)
                    //Setamos la información restante
                    binding.tituloLibroD.text=titulo
                    binding.descripcionD.text=descripcion
                    binding.vistasD.text=contadorVistas
                    binding.descargasD.text=contadorDescargas
                    binding.fechaD.text=fecha

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}