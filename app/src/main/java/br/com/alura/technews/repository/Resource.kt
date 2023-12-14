package br.com.alura.technews.repository

open class Resource<T>(
    val dado: T?,
    val erro: String? = null
)

fun <T> criaResourceDeFalha(
    resourceAtual: Resource<T?>?,
    erro: String?
): Resource<T?> {
    if (resourceAtual?.dado != null) {
        return Resource(dado = resourceAtual.dado, erro = erro)
    }
    return Resource(dado = null, erro = erro)
}

class SucessoResource<T>(dado: T) : Resource<T>(dado)

class FalhaResource<T>(erro: String) : Resource<T>(dado = null, erro = erro)
