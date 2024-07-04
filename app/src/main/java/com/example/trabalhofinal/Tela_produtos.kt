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
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Tela_produtos : AppCompatActivity() {

    lateinit var et_tipodograo : EditText
    lateinit var et_pontodatorra : EditText
    lateinit var et_blend : EditText
    lateinit var et_valor : EditText
    lateinit var bt_cadastrarproduto : Button
    lateinit var lv_listarprodutos : ListView
    lateinit var fa_editarprodutos : FloatingActionButton
    private lateinit var crud: CRUD
    lateinit var bottom_nav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_produtos)
        crud = CRUD(this)
        et_blend = findViewById(R.id.et_blend)
        et_pontodatorra = findViewById(R.id.et_pontodatorra)
        et_valor = findViewById(R.id.et_valor)
        et_tipodograo = findViewById(R.id.et_tipodograo)
        bt_cadastrarproduto = findViewById(R.id.bt_cadastrarproduto)
        fa_editarprodutos = findViewById(R.id.fa_editarprodutos)
        lv_listarprodutos = findViewById(R.id.lv_listarprodutos)

        //DEIXAR PRODUTOS JÁ PREVIAMENTE CRIADOS, COMO SE FOSSE ESTOQUE DISPONIVEL
        crud.criarProdutoIfNotExists("Café Bourbon", "Arábica", 27.99, "Escura")
        crud.criarProdutoIfNotExists("Café Kona", "Acaiá", 42.99, "Média")
        crud.criarProdutoIfNotExists("Café Geisha", "Robusta", 22.99, "Clara")
        crud.criarProdutoIfNotExists("Café Robusta", "Mundo Novo", 42.99, "Média")
        crud.criarProdutoIfNotExists("Café Catuaí", "Conilon", 42.99, "Média")

        crud.listarProdutos(this,lv_listarprodutos)

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


        //CADASTRAR NOVO PRODUTO
        bt_cadastrarproduto.setOnClickListener {
            val ponto = et_pontodatorra.text.toString()
            val tipo = et_tipodograo.text.toString()
            val valorText = et_valor.text.toString()
            val blend = et_blend.text.toString()

            //TRANSFORMAR VARIAVEL VALOR PARA DOUBLE
            val valor = valorText.toDouble()
            if (ponto.isNotEmpty() && tipo.isNotEmpty() && blend.isNotEmpty()){
                val produto = Produto(tipo,blend,valor,ponto)
                crud.criarProduto(tipo,blend,valor,ponto)
                crud.listarProdutos(this,lv_listarprodutos)
            }else{
                // Exiba uma mensagem de erro se algum campo estiver vazio
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            et_tipodograo.text.clear()
            et_blend.text.clear()
            et_pontodatorra.text.clear()
            et_valor.text.clear()
        }

        //BOTAO DE IR PARA A TELA DE EDITAR/DELETAR PRODUTOS
        fa_editarprodutos.setOnClickListener{
            irparaTeladeEdicao()
        }

    }

    private fun irparaTeladeEdicao() {
        val editar = Intent(this, Tela_produtos2::class.java)
        startActivity(editar)
    }
}