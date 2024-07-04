package com.example.trabalhofinal

data class Pedido(
    val id: Int,
    val clienteId: Int,
    val produtoId: Int,
    val quantidade: Int,
    val valorTotal: Double,
    val data: String,
    val horario: String
)



