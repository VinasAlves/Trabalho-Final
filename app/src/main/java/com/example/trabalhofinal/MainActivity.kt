package com.example.trabalhofinal

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var crud: CRUD
    lateinit var bottom_nav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        crud = CRUD(this)

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


    }
}