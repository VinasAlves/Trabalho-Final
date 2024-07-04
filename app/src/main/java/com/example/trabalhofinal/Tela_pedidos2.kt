package com.example.trabalhofinal

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Tela_pedidos2 : AppCompatActivity() {
    lateinit var lv_listarpedidos: ListView
    private lateinit var crud: CRUD
    lateinit var bt_voltar: Button
    lateinit var fa_deletar: FloatingActionButton
    lateinit var fa_buscar: FloatingActionButton
    lateinit var et_deletarporid: EditText
    lateinit var et_buscarporid: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_pedidos2)

        crud = CRUD(this)

        lv_listarpedidos = findViewById(R.id.lv_listarpedidos)
        fa_buscar = findViewById(R.id.fa_buscar)
        fa_deletar = findViewById(R.id.fa_deletar)
        bt_voltar = findViewById(R.id.bt_voltar)
        et_deletarporid = findViewById(R.id.et_deletarporid)
        et_buscarporid = findViewById(R.id.et_buscarporId)

        // Chama a função listarPedidos para exibir os pedidos na ListView
        listarPedidos()

        fa_deletar.setOnClickListener {
            val deleteStr = et_deletarporid.text.toString()
            if (deleteStr.isNotEmpty()) {
                try {
                    val delete = deleteStr.toInt()
                    crud.deletarPedido(delete)
                    Toast.makeText(this, "Pedido deletado com sucesso", Toast.LENGTH_SHORT).show()
                    listarPedidos() // Atualiza a lista após deletar um pedido
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Por favor, insira um ID válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, insira um ID", Toast.LENGTH_SHORT).show()
            }
            et_deletarporid.text.clear()
        }

        fa_buscar.setOnClickListener {
            val idStr = et_buscarporid.text.toString()
            if (idStr.isNotEmpty()) {
                try {
                    val id = idStr.toInt()
                    val pedido = crud.buscarPedidoPorId(id)
                    if (pedido != null) {
                        val pedidosList = listOf(pedido)
                        val pedidosStringList = pedidosList.map { formatarPedido(it) }
                        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pedidosStringList)
                        lv_listarpedidos.adapter = adapter
                    } else {
                        Toast.makeText(this, "Pedido não encontrado", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Por favor, insira um ID válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, insira um ID", Toast.LENGTH_SHORT).show()
            }
        }
        bt_voltar.setOnClickListener {
            voltarparaTelaPedidos()
        }
    }

    private fun listarPedidos() {
        crud.listarPedidos(this, lv_listarpedidos)
    }

    private fun formatarPedido(pedido: Pedido): String {
        return "ID: ${pedido.id}\nCliente ID: ${pedido.clienteId}\nProduto ID: ${pedido.produtoId}\nQuantidade: ${pedido.quantidade}\nValor Total: ${pedido.valorTotal}\nData: ${pedido.data}\nHorário: ${pedido.horario}"
    }

    private fun voltarparaTelaPedidos() {
        val voltar = Intent(this, Tela_pedidos::class.java)
        startActivity(voltar)
    }
}
