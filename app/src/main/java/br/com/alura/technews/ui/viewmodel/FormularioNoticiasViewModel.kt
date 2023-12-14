package br.com.alura.technews.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

class FormularioNoticiasViewModel(private val repository: NoticiaRepository) : ViewModel() {


    fun salva(noticia: Noticia): LiveData<Resource<Void?>> {
        return repository.salva(noticia)
    }


}