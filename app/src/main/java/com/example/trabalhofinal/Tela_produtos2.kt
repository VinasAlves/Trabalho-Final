package com.example.trabalhofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Tela_produtos2 : AppCompatActivity() {

    lateinit var edit_id : EditText
    lateinit var edit_tipo: EditText
    lateinit var edit_torra : EditText
    lateinit var edit_valor : EditText
    lateinit var edit_blend : EditText
    lateinit var bt_voltar5 : Button
    lateinit var fa_edit : FloatingActionButton
    lateinit var fa_delete : FloatingActionButton
    lateinit var lv_produtos : ListView
    private lateinit var crud: CRUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_produtos2)

        crud = CRUD(this)
        edit_blend = findViewById(R.id.edit_blend)
        edit_id = findViewById(R.id.edit_id)
        edit_tipo = findViewById(R.id.edit_tipo)
        edit_torra = findViewById(R.id.edit_torra)
        edit_valor = findViewById(R.id.edit_valor)
        bt_voltar5 = findViewById(R.id.bt_voltar5)
        fa_delete = findViewById(R.id.fa_delete)
        fa_edit = findViewById(R.id.fa_edit)
        lv_produtos = findViewById(R.id.lv_produtos)

        bt_voltar5.setOnClickListener {
            voltarparaTelaProdutos()
        }

        crud.listarProdutos(this,lv_produtos)

        fa_delete.setOnClickListener {
            val id = edit_id.text.toString()
            if (id.isNotEmpty()) {
                //passar Id para o tipo correto
                val idProduto = id.toIntOrNull()
                if (idProduto != null) {
                    if(crud.idProdutoExiste(id)){
                        crud.deletarProduto(idProduto)
                        crud.listarProdutos(this, lv_produtos)
                    }else {
                        Toast.makeText(this, "ID do produto não encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Por favor, insira um ID de produto válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, insira o ID do produto a ser deletado", Toast.LENGTH_SHORT).show()
            }
            edit_id.text.clear()
        }

        fa_edit.setOnClickListener{
            val id = edit_id.text.toString()
            val tipo = edit_tipo.text.toString()
            val torra = edit_torra.text.toString()
            val valor = edit_valor.text.toString()
            val blend = edit_blend.text.toString()
            if (id.isNotEmpty())
            {
                if(crud.idProdutoExiste(id)){
                    crud.atualizarProduto(this,id,tipo,blend,valor,torra)
                    crud.listarProdutos(this,lv_produtos)
                }else {
                    Toast.makeText(this, "ID do produto não encontrado", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "Por favor, insira o ID do produto a ser atualizado", Toast.LENGTH_SHORT).show()
            }
            edit_tipo.text.clear()
            edit_valor.text.clear()
            edit_id.text.clear()
            edit_blend.text.clear()
            edit_torra.text.clear()
        }

    }
    fun voltarparaTelaProdutos() {
        val voltar5 = Intent(this, Tela_produtos::class.java)
        startActivity(voltar5)
    }
}