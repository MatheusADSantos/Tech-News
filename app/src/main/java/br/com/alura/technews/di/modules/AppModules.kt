package br.com.alura.technews.di.modules

import androidx.room.Room
import br.com.alura.technews.database.AppDatabase
import org.koin.dsl.module

private const val NOME_BANCO_DE_DADOS = "news.db"

val appModules = module {
//    factory {  } Create a new one instance all time
//    single {  } Create a new instance all time | Singleton
//    single<AppDatabase> Is recommendation to declare the type

    single<AppDatabase> {
        Room.databaseBuilder(
            get(), // Koin will try inject this dependence
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }

}