package br.com.alura.technews.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository

const val TAG = "ListaNoticiasViewModel"

class ListaNoticiasViewModel(private val repository: NoticiaRepository) : ViewModel() {

    init {
        Log.i(TAG, "Criando ListaNoticiasViewModel...")
    }

    override fun onCleared() {
        super.onCleared()
        Log.e(TAG, "onCleared: ViewModel destruida!")
    }

    fun buscaTodos(
        quandoSucesso: (List<Noticia>) -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        repository.buscaTodos(quandoSucesso, quandoFalha)
    }


}