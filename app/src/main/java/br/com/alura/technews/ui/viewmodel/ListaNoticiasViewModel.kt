package br.com.alura.technews.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "ListaNoticiasViewModel"

class ListaNoticiasViewModel() : ViewModel() {

    init {
        Log.i(TAG, "Criando ListaNoticiasViewModel...")
    }

    override fun onCleared() {
        super.onCleared()
        Log.e(TAG, "onCleared: ViewModel destruida!")
    }


}