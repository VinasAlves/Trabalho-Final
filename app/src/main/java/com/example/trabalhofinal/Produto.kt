package com.example.trabalhofinal

class Produto(var tipoDoGrao: String, var blend: String, var valor: Double, var pontoDaTorra: String) {
    var idProduto: Int = -1
    init {
        // Inicialização dos campos
        this.blend = blend
        this.valor = valor
        this.pontoDaTorra = pontoDaTorra
        this.tipoDoGrao = tipoDoGrao
    }
}

