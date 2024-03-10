package com.njso.manualsapp.Administrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.njso.manualsapp.databinding.ItemLibroAdminBinding

class AdaptadorPdfAdmin : RecyclerView.Adapter<AdaptadorPdfAdmin.HolderPdfAdmin>{

    private lateinit var binding : ItemLibroAdminBinding
    private var m_context : Context
    private var pdfArrayList : ArrayList<Modelopdf>

    constructor(m_context: Context, pdfArrayList: ArrayList<Modelopdf>) : super() {
        this.m_context = m_context
        this.pdfArrayList = pdfArrayList
    }


    inner class HolderPdfAdmin(itemView : View) : RecyclerView.ViewHolder(itemView){
        val VisualizadorPDF = binding.VisualizadorPDF
        val progressBar = binding.progressBar
        val Txt_titulo_libro_item = binding.TxtTituloLibroItem
        val Txt_descripcion_libro_item = binding.TxtDescripcionLibroItem
        val Txt_categoria_libro_admin = binding.TxtCategoriaLibroAdmin
        val Txt_tamanio_libro_admin = binding.TxtTamanioLibroAdmin
        val Txt_fecha_libro_admin = binding.TxtFechaLibroAdmin
        val Ib_mas_opciones = binding.IbMasOpciones



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPdfAdmin {
        binding = ItemLibroAdminBinding.inflate(LayoutInflater.from(m_context),parent,false)
        return HolderPdfAdmin(binding.root)
    }

    override fun getItemCount(): Int {
        return pdfArrayList.size
    }

    override fun onBindViewHolder(holder: HolderPdfAdmin, position: Int) {
        val modelo  = pdfArrayList[position]
        val pdfId = modelo.id
        val categoriaId = modelo.categoria
        val tirulo = modelo.titulo
        val descripcion = modelo.descripcion
        val pdfUrl = modelo.url
        val tiempo = modelo.tiempo
        return
    }
}