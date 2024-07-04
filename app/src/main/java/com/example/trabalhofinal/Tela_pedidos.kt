package com.example.trabalhofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class Tela_pedidos : AppCompatActivity() {

    lateinit var et_idcliente : EditText
    lateinit var et_idproduto : EditText
    lateinit var et_quantidade : EditText
    lateinit var bt_pedido : Button
    lateinit var bt_adicionarproduto : Button
    lateinit var listarprodutos : ListView
    private lateinit var crud: CRUD
    lateinit var bottom_nav : BottomNavigationView
    private var currentPedidoId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_pedidos)
        crud = CRUD(this)

        et_idproduto = findViewById(R.id.et_idproduto)
        et_idcliente = findViewById(R.id.et_idcliente)
        et_quantidade = findViewById(R.id.et_quantidade)
        bt_pedido = findViewById(R.id.bt_pedido)
        bt_adicionarproduto = findViewById(R.id.bt_adicionarproduto)
        listarprodutos = findViewById(R.id.listarprodutos)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav)

        //NAVBAR PARA NAVEGAR ENTRE TELAS MAIS FACIL
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.users -> {
                    val intent = Intent(this, Tela_clientes::class.java)
                    startActivity(intent)
                    true
                }
                R.id.products -> {
                    val intent = Intent(this, Tela_produtos::class.java)
                    startActivity(intent)
                    true
                }
                R.id.pedidos -> {
                    val intent = Intent(this, Tela_pedidos::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        crud.listarProdutos(this, listarprodutos)

        bt_pedido.setOnClickListener {
            if (currentPedidoId != 0L) {
                crud.finalizarPedido(this,currentPedidoId)
                Toast.makeText(this, "Pedido realizado com sucesso", Toast.LENGTH_SHORT).show()
                currentPedidoId = 0L // Resetar o ID do pedido atual
                irParaTelaListarPedidos()
            } else {
                Toast.makeText(this, "Nenhum pedido em andamento", Toast.LENGTH_SHORT).show()
            }
        }

        bt_adicionarproduto.setOnClickListener {
            val clienteIdStr = et_idcliente.text.toString()
            val produtoIdStr = et_idproduto.text.toString()
            val quantidadeStr = et_quantidade.text.toString()

            if (clienteIdStr.isNotEmpty() && produtoIdStr.isNotEmpty() && quantidadeStr.isNotEmpty()) {
                val clienteId = clienteIdStr.toInt()
                val produtoId = produtoIdStr.toInt()
                val quantidade = quantidadeStr.toInt()

                // Verificação para checar se existe o cliente
                if (!crud.idClienteExiste(clienteIdStr)) {
                    Toast.makeText(this, "ID do Cliente não encontrado", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Verificação para checar se existe o produto
                if (!crud.idProdutoExiste(produtoIdStr)) {
                    Toast.makeText(this, "ID do Produto não encontrado", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Criar um novo pedido se não houver um atual
                if (currentPedidoId == 0L) {
                    currentPedidoId = crud.criarPedido(clienteId)
                }

                crud.adicionarProdutoAoCarrinho(this,currentPedidoId, produtoId, quantidade)
                Toast.makeText(this, "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            et_idproduto.text.clear()
            et_quantidade.text.clear()
        }

    }

    fun irParaTelaListarPedidos() {
        val TeladeListarPedidos = Intent(this, Tela_pedidos2::class.java)
        startActivity(TeladeListarPedidos)
    }
}
