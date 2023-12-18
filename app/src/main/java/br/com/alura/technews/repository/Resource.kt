package br.com.alura.technews.repository

open class Resource<T>(
    val dado: T?,
    val erro: String? = null
)

class SucessoResource<T>(dado: T) : Resource<T>(dado)

class FalhaResource<T>(erro: String) : Resource<T>(dado = null, erro = erro)
