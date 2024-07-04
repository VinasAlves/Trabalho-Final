package com.example.trabalhofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Tela_clientes_atualizar : AppCompatActivity() {

    lateinit var et_atualizarnome: EditText
    lateinit var et_atualizartelefone: EditText
    lateinit var et_atualizarendereco: EditText
    lateinit var et_atualizarinstagram: EditText
    lateinit var bt_atualizar: Button
    lateinit var bt_voltar3: Button
    lateinit var lv_atualizados: ListView
    private lateinit var crud: CRUD
    private var clienteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_clientes_atualizar)
        crud = CRUD(this)
        et_atualizarendereco = findViewById(R.id.et_atualizarendereco)
        et_atualizarnome = findViewById(R.id.et_atualizarnome)
        et_atualizarinstagram = findViewById(R.id.et_atualizarinstagram)
        et_atualizartelefone = findViewById(R.id.et_atualizartelefone)
        bt_atualizar = findViewById(R.id.bt_atualizar)
        bt_voltar3 = findViewById(R.id.bt_voltar3)
        lv_atualizados = findViewById(R.id.lv_atualizados)

        // Recebe o ID do cliente
        clienteId = intent.getStringExtra("id")

        crud.listarDados(this, lv_atualizados)
        bt_atualizar.setOnClickListener {
            val nome = et_atualizarnome.text.toString()
            val telefone = et_atualizartelefone.text.toString()
            val endereco = et_atualizarendereco.text.toString()
            val instagram = et_atualizarinstagram.text.toString()

            if (clienteId != null ) {
                crud.atualizarCliente(this, clienteId!!, nome, telefone, endereco, instagram)
                crud.listarDados(this, lv_atualizados)
                Toast.makeText(this, "Cliente atualizado com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            et_atualizarinstagram.text.clear()
            et_atualizartelefone.text.clear()
            et_atualizarendereco.text.clear()
            et_atualizarinstagram.text.clear()
        }

        bt_voltar3.setOnClickListener {
            voltarparaTela2()
        }

    }
    private fun voltarparaTela2() {
        val voltar3 = Intent(this, Tela_clientes2::class.java)
        startActivity(voltar3)
    }
}
