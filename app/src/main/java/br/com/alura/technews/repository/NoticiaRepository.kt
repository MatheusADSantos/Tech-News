package br.com.alura.technews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.asynctask.BaseAsyncTask
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.retrofit.webclient.NoticiaWebClient

class NoticiaRepository(
    private val dao: NoticiaDAO,
    private val webclient: NoticiaWebClient = NoticiaWebClient()
) {

    private var noticiasEncontradasCacheadas = MutableLiveData<Resource<List<Noticia>?>>()

    fun buscaTodos(): LiveData<Resource<List<Noticia>?>> {
        val atualizaListaNoticias: (List<Noticia>) -> Unit = {
            noticiasEncontradasCacheadas.value = Resource(dado = it)
        }
        buscaInterno(quandoSucesso = atualizaListaNoticias)
        buscaNaApi(quandoSucesso = atualizaListaNoticias, quandoFalha = { erro ->
            val resourceAtual = noticiasEncontradasCacheadas.value
            val resourceDeFalha = criaResourceDeFalha<List<Noticia>?>(
                resourceAtual,
                erro
            )
            noticiasEncontradasCacheadas.value = resourceDeFalha
        })
        return noticiasEncontradasCacheadas
    }

    fun salva(noticia: Noticia): LiveData<Resource<Void?>> {
        val liveData = MutableLiveData<Resource<Void?>>()
        salvaNaApi(noticia, quandoSucesso = {
            liveData.value = Resource(null)
        }, quandoFalha = { erro ->
            liveData.value = Resource(null, erro)
        })

        return liveData
    }

    fun remove(noticia: Noticia): LiveData<Resource<Void?>> {
        Log.i("NoticiaRepositoy", "remove: $noticia")
        val liveData = MutableLiveData<Resource<Void?>>()
        removeNaApi(noticia, quandoSucesso = {
            liveData.value = Resource(null)
        }, quandoFalha = { error ->
            Resource(dado = null, erro = error)
        })
        return liveData
    }

    fun edita(noticia: Noticia): LiveData<Resource<Void?>> {
        var liveData = MutableLiveData<Resource<Void?>>()
        editaNaApi(noticia, quandoSucesso = {
            liveData.value = Resource(dado = null)
        }, quandoFalha = { error ->
            liveData.value = Resource(null, error)
        })
        return liveData
    }

    fun buscaPorId(noticiaId: Long): LiveData<Noticia?> {
        return dao.buscaPorId(noticiaId)
    }

    private fun buscaNaApi(
        quandoSucesso: (List<Noticia>) -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.buscaTodas(
            quandoSucesso = { noticiasNovas ->
                noticiasNovas?.let {
                    salvaInterno(noticiasNovas, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun buscaInterno(quandoSucesso: (List<Noticia>) -> Unit) {
        BaseAsyncTask(quandoExecuta = {
            Log.i("teste", "buscando noticias no banco")
            Thread.sleep(5000)
            dao.buscaTodos()
        }, quandoFinaliza = {
            Log.i("teste", "finalizou busca")
            quandoSucesso(it)
        })
            .execute()
    }


    private fun salvaNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.salva(
            noticia,
            quandoSucesso = {
                it?.let { noticiaSalva ->
                    salvaInterno(noticiaSalva, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun salvaInterno(
        noticias: List<Noticia>,
        quandoSucesso: (noticiasNovas: List<Noticia>) -> Unit
    ) {
        BaseAsyncTask(
            quandoExecuta = {
                dao.salva(noticias)
                dao.buscaTodos()
            }, quandoFinaliza = quandoSucesso
        ).execute()
    }

    private fun salvaInterno(
        noticia: Noticia,
        quandoSucesso: () -> Unit
    ) {
        BaseAsyncTask(quandoExecuta = {
            dao.salva(noticia)
        }, quandoFinaliza = {
            quandoSucesso()
        }).execute()

    }

    private fun removeNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.remove(
            noticia.id,
            quandoSucesso = {
                removeInterno(noticia, quandoSucesso)
            },
            quandoFalha = quandoFalha
        )
    }


    private fun removeInterno(
        noticia: Noticia,
        quandoSucesso: () -> Unit
    ) {
        BaseAsyncTask(quandoExecuta = {
            dao.remove(noticia)
        }, quandoFinaliza = {
            quandoSucesso()
        }).execute()
    }

    private fun editaNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.edita(
            noticia.id, noticia,
            quandoSucesso = { noticiaEditada ->
                noticiaEditada?.let {
                    salvaInterno(noticiaEditada, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

}
