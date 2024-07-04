package com.example.trabalhofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Tela_clientes : AppCompatActivity() {

    lateinit var et_cpf :EditText
    lateinit var et_nome :EditText
    lateinit var et_telefone :EditText
    lateinit var et_endereco :EditText
    lateinit var et_instagram :EditText
    lateinit var bt_inserircliente :Button
    lateinit var bt_listarcliente : Button
    private lateinit var crud: CRUD
    lateinit var bottom_nav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_clientes)
        crud = CRUD(this)

        //declaração de variaveis
        et_cpf = findViewById(R.id.et_cpf)
        et_nome = findViewById(R.id.et_nome)
        et_telefone = findViewById(R.id.et_telefone)
        et_endereco = findViewById(R.id.et_endereco)
        et_instagram = findViewById(R.id.et_instagram)
        bt_inserircliente = findViewById(R.id.bt_inserircliente)
        bt_listarcliente = findViewById(R.id.bt_listarclientes)

        //NAVIGATION BOTTOM BAR
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav)
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

        //INSERIR CLIENTE
        bt_inserircliente.setOnClickListener {
            val cpf = et_cpf.text.toString()
            val nome = et_nome.text.toString()
            val telefone = et_telefone.text.toString()
            val endereco = et_endereco.text.toString()
            val instagram = et_instagram.text.toString()

            if(cpf.isNotEmpty() && nome.isNotEmpty() && telefone.isNotEmpty() && endereco.isNotEmpty() && instagram.isNotEmpty()){
                val cliente = Cliente(cpf, nome, telefone, endereco,instagram)
                crud.criarCliente(cpf, nome, telefone, endereco,instagram)
                Toast.makeText(this, "Cliente adicionado com sucesso", Toast.LENGTH_SHORT).show()
            }else{
                // Exiba uma mensagem de erro se algum campo estiver vazio
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            et_cpf.text.clear()
            et_nome.text.clear()
            et_telefone.text.clear()
            et_endereco.text.clear()
            et_instagram.text.clear()

        }
        //VER TELA DE CADASTRADOS
        bt_listarcliente.setOnClickListener {
            irParaTelaListarClientes()
        }

    }

    //função de ir para telas de clientes
    fun irParaTelaListarClientes(){
        val TeladeListarClientes = Intent(this,Tela_clientes2::class.java)
        startActivity(TeladeListarClientes)
    }

}