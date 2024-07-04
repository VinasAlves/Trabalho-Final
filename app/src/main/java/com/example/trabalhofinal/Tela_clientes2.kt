package com.example.trabalhofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Tela_clientes2 : AppCompatActivity() {

    lateinit var lv_listarclientes: ListView
    lateinit var bt_voltar2: Button
    private lateinit var crud: CRUD
    lateinit var bt_editar: FloatingActionButton
    lateinit var bt_deletar: FloatingActionButton
    lateinit var et_id: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_clientes2)

        crud = CRUD(this)
        lv_listarclientes = findViewById(R.id.lv_listarclientes)
        bt_voltar2 = findViewById(R.id.bt_voltar2)
        bt_deletar = findViewById(R.id.bt_deletar)
        bt_editar = findViewById(R.id.bt_editar)
        et_id = findViewById(R.id.et_id)

        crud.listarDados(this, lv_listarclientes)

        bt_deletar.setOnClickListener {
            val id = et_id.text.toString()
            if (id.isNotEmpty()) {
                if(crud.idClienteExiste(id)){
                    crud.deletarCliente(id)
                    crud.listarDados(this, lv_listarclientes)
                } else {
                    Toast.makeText(this, "ID do Cliente não encontrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, insira o ID do cliente a ser deletado", Toast.LENGTH_SHORT).show()
            }
            et_id.text.clear()
        }

        bt_editar.setOnClickListener {
            val id = et_id.text.toString()
            if (id.isNotEmpty()) {
                if(crud.idClienteExiste(id)){
                    irparaTeladeAtualizar(id)
                }else {
                    Toast.makeText(this, "ID do Cliente não encontrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, insira o ID do cliente a ser editado", Toast.LENGTH_SHORT).show()
            }
            et_id.text.clear()
        }

        bt_voltar2.setOnClickListener {
            voltarparaTelaClientes2()
        }
    }

    private fun irparaTeladeAtualizar(id_att: String) {
        val atualizar = Intent(this, Tela_clientes_atualizar::class.java).apply {
            putExtra("id", id_att)
        }
        startActivity(atualizar)
    }

    private fun voltarparaTelaClientes2() {
        val voltar2 = Intent(this, Tela_clientes::class.java)
        startActivity(voltar2)
    }
}
