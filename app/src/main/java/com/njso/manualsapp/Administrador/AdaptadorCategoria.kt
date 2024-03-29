package com.njso.manualsapp.Administrador

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.njso.manualsapp.databinding.ItemCategoriaAdminBinding

class AdaptadorCategoria : RecyclerView.Adapter<AdaptadorCategoria.HolderCategoria>, Filterable {

    private lateinit var binding : ItemCategoriaAdminBinding

    private val m_context : Context
    public var categoriaArrayList : ArrayList<ModeloCategoria>
    private var filtroLista : ArrayList<ModeloCategoria>
    private var filtro : FiltroCategoria?=null

    constructor(m_context: Context, categoriaArrayList: ArrayList<ModeloCategoria>) {
        this.m_context = m_context
        this.categoriaArrayList = categoriaArrayList
        this.filtroLista = categoriaArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategoria {
        binding = ItemCategoriaAdminBinding.inflate(LayoutInflater.from(m_context),parent,false)
        return HolderCategoria(binding.root)
    }

    override fun getItemCount(): Int {
        return categoriaArrayList.size
    }

    override fun onBindViewHolder(holder: HolderCategoria, position: Int) {
        val modelo = categoriaArrayList[position]
        val id = modelo.id
        val categoria = modelo.categoria
        val tiempo = modelo.tiempo
        val uid = modelo.uid

        holder.categoriaTv.text = categoria

        holder.eliminarCatIb.setOnClickListener {
            val builder = AlertDialog.Builder(m_context)
            builder.setTitle("Eliminar categoria")
                .setMessage("Quiere eliminar la categoría?")
                .setPositiveButton("Confirmar"){a, d->
                    Toast.makeText(m_context,"Eliminando Categoría", Toast.LENGTH_SHORT).show()
                    EliminarCategoria(modelo, holder)

                }
                .setNegativeButton("Cancelar"){a, d->
                    a.dismiss()

                }
            builder.show()
        }
    }

    private fun EliminarCategoria(modelo: ModeloCategoria, holder: AdaptadorCategoria.HolderCategoria) {
        val id = modelo.id
        val ref = FirebaseDatabase.getInstance().getReference("Categorias")
        ref.child(id).removeValue()
            .addOnSuccessListener {
                Toast.makeText(m_context,"Categoría eliminada", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                Toast.makeText(m_context,"No se pudo eliminar debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    inner class HolderCategoria(itemView : View) : RecyclerView.ViewHolder(itemView){
        var categoriaTv : TextView = binding.ItemNombreCatA
        var eliminarCatIb : ImageButton = binding.EliminarCat



    }

    override fun getFilter(): Filter {
        if (filtro == null){
            filtro = FiltroCategoria(filtroLista, this)
        }
        return filtro as FiltroCategoria
    }


}