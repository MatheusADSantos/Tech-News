package br.com.alura.technews.di.modules

import androidx.room.Room
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.retrofit.webclient.NoticiaWebClient
import br.com.alura.technews.ui.viewmodel.FormularioNoticiasViewModel
import br.com.alura.technews.ui.viewmodel.ListaNoticiasViewModel
import br.com.alura.technews.ui.viewmodel.VisualizaNoticiaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val NOME_BANCO_DE_DADOS = "news.db"

val appModules = module {
//    factory {  }          Create a new one instance all time
//    single {  }           Create a new instance all time | Singleton
//    single<AppDatabase>   Is recommendation to declare the type
//    get()                 Koin will try inject this dependence

    single<AppDatabase> {
        Room.databaseBuilder(
            get(), // Koin will try inject this dependence
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }
    single<NoticiaDAO> {
        get<AppDatabase>().noticiaDAO
    }
    single<NoticiaWebClient> {
        NoticiaWebClient()
    }
    single<NoticiaRepository> {
        NoticiaRepository(get(), get())
    }

    viewModel<ListaNoticiasViewModel> {
        ListaNoticiasViewModel(get())
    }
    viewModel<VisualizaNoticiaViewModel> { (id: Long) ->
        VisualizaNoticiaViewModel(id, get())
    }
    viewModel<FormularioNoticiasViewModel> {
        FormularioNoticiasViewModel(get())
    }

}