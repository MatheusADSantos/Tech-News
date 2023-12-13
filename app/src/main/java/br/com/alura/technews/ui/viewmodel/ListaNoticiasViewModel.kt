package br.com.alura.technews.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

const val TAG = "ListaNoticiasViewModel"

class ListaNoticiasViewModel(private val repository: NoticiaRepository) : ViewModel() {

    // onCreate
    init {
        Log.i(TAG, "Criando ListaNoticiasViewModel...")
    }

    // OnDestroy
    override fun onCleared() {
        super.onCleared()
        Log.e(TAG, "onCleared: ViewModel destruida!")
    }

    fun buscaTodos(): LiveData<Resource<List<Noticia>?>> {
        return repository.buscaTodos()
    }


}