package com.example.trabalhofinal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.ArrayAdapter
import android.widget.ListView

class BancoIFTM(context: Context) : SQLiteOpenHelper(context, "MeubancoIFTM", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val nomeTabela = "Cliente"
        val id = "id"
        val cpf = "cpf"
        val nome = "nome"
        val telefone = "telefone"
        val endereco = "endereco"
        val instagram = "instagram"

        val SQL_clientes =
            "CREATE TABLE ${nomeTabela} (" +
                    "${id} INTEGER PRIMARY KEY," +
                    "${cpf} TEXT," +
                    "${nome} TEXT," +
                    "${telefone} TEXT," +
                    "${endereco} TEXT," +
                    "${instagram} TEXT)"

        db.execSQL(SQL_clientes)

        //CRIAR TABELA PARA OS PRODUTOS
        val nomeTabelaProduto = "Produto"
        val idProduto = "idproduto"
        val tipodograo = "tipodograo"
        val pontodatorra = "pontodatorra"
        val valor = "valor"
        val blend = "blend"

        val SQL_Produto =
            "CREATE TABLE $nomeTabelaProduto (" +
                    "$idProduto INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$tipodograo TEXT," +
                    "$pontodatorra TEXT," +
                    "$valor REAL," +
                    "$blend TEXT)"

        db.execSQL(SQL_Produto)

        //CRIAR TABELA PARA OS PEDIDOS
        val nomeTabelaPedido = "Pedido"
        val idPedido = "idpedido"
        val fk_clienteId = "cliente_id"
        val fk_produtoId = "produto_id"
        val quantidade = "quantidade"
        val valorTotal = "valor_total"
        val data = "data"
        val horario = "horario"

        val SQL_Pedido =
            "CREATE TABLE $nomeTabelaPedido (" +
                    "$idPedido INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$fk_clienteId INTEGER," +
                    "$fk_produtoId INTEGER," +
                    "$quantidade INTEGER," +
                    "$valorTotal REAL," +
                    "$data TEXT," +
                    "$horario TEXT," +
                    "FOREIGN KEY($fk_clienteId) REFERENCES Cliente(id)," +
                    "FOREIGN KEY($fk_produtoId) REFERENCES Produto(idproduto))"

        db.execSQL(SQL_Pedido)

        //CRIAR TABELA PARA OS PEDIDOS/PRODUTOS
        val nomeTabelaProdutoPedido = "ProdutoPedido"
        val idProdutoPedido = "idProdutoPedido"
        val fk_pedidoid = "fk_pedidoid"
        val fk_produtoid = "fk_produtoid"
        val qtd = "qtd"

        val SQL_PedidoProduto =
            "CREATE TABLE $nomeTabelaProdutoPedido (" +
                    "$idProdutoPedido INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$fk_pedidoid INTEGER," +
                    "$fk_produtoid INTEGER," +
                    "$qtd INTEGER," +
                    "FOREIGN KEY($fk_pedidoid) REFERENCES Pedido(idpedido)," +
                    "FOREIGN KEY($fk_produtoid) REFERENCES Produto(idproduto))"

        db.execSQL(SQL_PedidoProduto)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Cliente")
        db.execSQL("DROP TABLE IF EXISTS Produto")
        db.execSQL("DROP TABLE IF EXISTS Pedido")
        db.execSQL("DROP TABLE IF EXISTS ProdutoPedido")
        onCreate(db)
    }
}

