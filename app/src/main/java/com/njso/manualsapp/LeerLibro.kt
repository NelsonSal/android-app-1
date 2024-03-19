package com.njso.manualsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.njso.manualsapp.Administrador.Constantes
import com.njso.manualsapp.databinding.ActivityLeerLibroBinding

class LeerLibro : AppCompatActivity() {

    private lateinit var binding : ActivityLeerLibroBinding
    var idLibro=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityLeerLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idLibro=intent.getStringExtra("idLibro")!!

        binding.IbRegresar.setOnClickListener {
           onBackPressedDispatcher.onBackPressed()

        }
        cargarInformacionLibro()
    }

   private fun cargarInformacionLibro() {
        val ref = FirebaseDatabase.getInstance().getReference("Libros")
        ref.child(idLibro)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pdfUrl =snapshot.child("url").value
                    cargarLibroStorage("$pdfUrl")
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun cargarLibroStorage(pdfUrl: String) {
        val reference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
        reference.getBytes(Constantes.Maximo_bytes_pdf)
                .addOnSuccessListener {bytes->
                    //Cargar el pdf
                    binding.VisualizadorPDF.fromBytes(bytes)
                        .swipeHorizontal(false)
                        .onPageChange{pagina, contadorPaginas->
                            val paginaActual = pagina+1
                            binding.TxtLeerLibro.text="$paginaActual/$contadorPaginas"

                        }
                        .load()
                    binding.progressBar.visibility = android.view.View.GONE //No coincide!!!
                }
                .addOnFailureListener {e->
                    Toast.makeText(this, "No se completo debido a ${e.message}", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = android.view.View.GONE //No coincide!!!


                }

    }

}