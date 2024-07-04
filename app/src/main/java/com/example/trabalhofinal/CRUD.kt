package com.example.trabalhofinal

import android.R
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class CRUD(private val context: Context) {

    fun criarCliente(cpf: String, nome: String, telefone: String, endereco:String, instagram:String) {
        val meuBanco = BancoIFTM(context)
        val db = meuBanco.writableDatabase

        val values = ContentValues().apply {
            put("cpf", cpf)
            put("nome", nome)
            put("telefone", telefone)
            put("endereco", endereco)
            put("instagram", instagram)
        }

        val newRowId = db.insert("Cliente", null, values)

        if (newRowId == -1L) {
            Log.e("CriarCliente", "Falha ao inserir cliente no banco de dados.")
        } else {
            Log.i("CriarCliente", "Cliente inserido com sucesso. ID: $newRowId")
        }

        db.close()
    }

    //MOSTRAR CLIENTES
    fun listarDados(context: Context, listView: ListView) {
        try {
            val linhas = ArrayList<String>()
            val meuAdapter = ArrayAdapter<String>(context, R.layout.simple_list_item_1, linhas)
            listView.adapter = meuAdapter

            val meuBanco = BancoIFTM(context)
            val db = meuBanco.readableDatabase

            val cursor = db.rawQuery("SELECT * FROM Cliente", null)

            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                val cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                val telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))
                val endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco"))
                val instagram = cursor.getString(cursor.getColumnIndexOrThrow("instagram"))

                val cliente = "ID : $id\nCPF: $cpf\nNome: $nome\nTelefone: $telefone\nEndereço: $endereco\nInstagram: $instagram"
                linhas.add(cliente)
            }

            cursor.close()
            db.close()
            meuAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    //DELETAR CLIENTES
    fun deletarCliente(id: String) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.writableDatabase

            val resultado = db.delete("Cliente", "id = ?", arrayOf(id))

            if (resultado != -1) {
                Log.i("DeletarCliente", "Cliente deletado com sucesso")
            } else {
                Log.e("DeletarCliente", "Falha ao deletar cliente")
            }

            db.close()
        } catch (e: Exception) {
            Log.e("DeletarCliente", "Erro ao deletar cliente: ${e.message}")
        }
    }

    //ATUALIZAR CLIENTES
    fun atualizarCliente(context: Context, id: String, novoNome: String, novoTelefone: String, novoEndereco:String, novoInstagram : String) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.writableDatabase

            val values = ContentValues()

            // adicionar valores se eles não forem nulos
            if (!novoNome.isNullOrEmpty()) values.put("nome", novoNome)
            if (!novoTelefone.isNullOrEmpty()) values.put("telefone", novoTelefone)
            if (!novoEndereco.isNullOrEmpty()) values.put("endereco", novoEndereco)
            if (!novoInstagram.isNullOrEmpty()) values.put("instagram", novoInstagram)

            if (values.size() > 0) {
                val resultado = db.update(
                    "Cliente",
                    values,
                    "id = ?",
                    arrayOf(id)
                )

                if (resultado == 0) {
                    Log.e("CRUD", "Erro ao atualizar cliente no banco de dados")
                } else {
                    Log.d("CRUD", "Cliente atualizado com sucesso")
                }
            } else {
                Log.e("CRUD", "Nenhum campo para atualizar")
            }

            db.close()
        } catch (e: Exception) {
            Log.e("CRUD", "Erro ao atualizar cliente no banco de dados: ${e.message}")
        }
    }

    //verificaçao para ver se possui cliente antes de editar/deletar
    fun idClienteExiste(clienteId: String): Boolean {
        val banco = BancoIFTM(context)
        val db = banco.readableDatabase

        val query = "SELECT id FROM Cliente WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(clienteId))
        val clienteExiste = cursor.count > 0

        cursor.close()
        db.close()
        return clienteExiste
    }


    // CRUD DOS PRODUTOS


    //verificaçao para ver se possui propduto antes de editar/deletar
    fun idProdutoExiste(idProduto: String): Boolean {
        val banco = BancoIFTM(context)
        val db = banco.readableDatabase

        val query = "SELECT idproduto FROM Produto WHERE idproduto = ?"
        val cursor = db.rawQuery(query, arrayOf(idProduto.toString()))

        val existe = cursor.count > 0
        cursor.close()
        db.close()

        return existe
    }

    fun criarProduto(tipoDoGrao:String,blend:String, valor: Double, pontoDaTorra:String) {
        val meuBanco = BancoIFTM(context)
        val db = meuBanco.writableDatabase

        val values = ContentValues().apply {
            put("tipodograo", tipoDoGrao)
            put("blend", blend)
            put("valor", valor)
            put("pontodatorra", pontoDaTorra)
        }

        val newRowId = db.insert("Produto", null, values)

        if (newRowId == -1L) {
            Log.e("CriarProduto", "Falha ao inserir produto no banco de dados.")
        } else {
            Log.i("CriarProduto", "Produto inserido com sucesso. ID: $newRowId")
        }

        db.close()
    }

    fun atualizarProduto(context: Context,id:String, tipoDoGrao:String,blend:String, valor: String, pontoDaTorra:String) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.writableDatabase

            val valores = ContentValues()

            if (!tipoDoGrao.isNullOrEmpty()) {
                valores.put("tipodograo", tipoDoGrao)
            }
            if (!blend.isNullOrEmpty()) {
                valores.put("blend", blend)
            }
            if (!valor.isNullOrEmpty()) {
                valores.put("valor", valor)
            }
            if (!pontoDaTorra.isNullOrEmpty()) {
                valores.put("pontodatorra", pontoDaTorra)
            }

            if (valores.size() > 0) {
                val rowsUpdated = db.update("Produto", valores, "idproduto = ?", arrayOf(id))
                if (rowsUpdated > 0) {
                    Toast.makeText(context, "Produto atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Produto não encontrado.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Nenhum campo para atualizar.", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(context, "Erro ao atualizar o produto: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun listarProdutos(context: Context, listView: ListView) {
        try {
            val linhas = ArrayList<String>()
            val meuAdapter = ArrayAdapter<String>(context, R.layout.simple_list_item_1, linhas)
            listView.adapter = meuAdapter

            val meuBanco = BancoIFTM(context)
            val db = meuBanco.readableDatabase

            val cursor = db.rawQuery("SELECT * FROM Produto", null)

            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndexOrThrow("idproduto"))
                val tipodograo = cursor.getString(cursor.getColumnIndexOrThrow("tipodograo"))
                val tipodatorra = cursor.getString(cursor.getColumnIndexOrThrow("pontodatorra"))
                val valor = cursor.getString(cursor.getColumnIndexOrThrow("valor"))
                val blend = cursor.getString(cursor.getColumnIndexOrThrow("blend"))

                val produto = "ID : $id\nTipo do Grão: $tipodograo\nTipo da Torra: $tipodatorra\nValor: $valor\nBlend: $blend\n"
                linhas.add(produto)
            }

            cursor.close()
            db.close()
            meuAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deletarProduto(idProduto: Int) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.writableDatabase

            val resultado = db.delete("Produto", "idproduto = ?", arrayOf(idProduto.toString()))

            if (resultado != -1) {
                Log.i("DeletarProduto", "Produto deletado com sucesso")
            } else {
                Log.e("DeletarProduto", "Falha ao deletar produto")
            }

            db.close()
        } catch (e: Exception) {
            Log.e("DeletarProduto", "Erro ao deletar produto: ${e.message}")
        }
    }

    // Função para criar produto se ele não existir
    fun criarProdutoIfNotExists(tipoDoGrao: String, blend: String, valor: Double, pontoDaTorra: String) {
        val banco = BancoIFTM(context)
        val db = banco.writableDatabase

        val values = ContentValues().apply {
            put("tipodograo", tipoDoGrao)
            put("blend", blend)
            put("valor", valor)
            put("pontodatorra", pontoDaTorra)
        }

        // Verifica se o produto já existe considerando todos os campos
        if (!produtoExiste(tipoDoGrao, blend, pontoDaTorra, valor, db)) {
            db.insert("Produto", null, values)
        }
        db.close()
    }

    // Função para verificar se o produto já existe no banco de dados
    private fun produtoExiste(tipoDoGrao: String, blend: String, pontoDaTorra: String, valor: Double, db: SQLiteDatabase): Boolean {
        val query = "SELECT idproduto FROM Produto WHERE tipodograo = ? AND blend = ? AND pontodatorra = ? AND valor = ?"
        val selectionArgs = arrayOf(tipoDoGrao, blend, pontoDaTorra, valor.toString())
        val cursor = db.rawQuery(query, selectionArgs)
        val produtoExiste = cursor.count > 0
        cursor.close()
        return produtoExiste
    }

    //AQUI
    //PEDIDOS

    fun criarPedido(clienteId: Int): Long {
        val banco = BancoIFTM(context)
        val db = banco.writableDatabase

        // Configurando o fuso horário para o Brasil (-03:00)
        val timeZone = TimeZone.getTimeZone("America/Sao_Paulo")

        // Criando objetos SimpleDateFormat para data e hora com o fuso horário configurado
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        dateFormat.timeZone = timeZone
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        timeFormat.timeZone = timeZone

        // Obter a data e hora atuais formatadas
        val currentDate = Date()
        val formattedDate = dateFormat.format(currentDate)
        val formattedTime = timeFormat.format(currentDate)

        val values = ContentValues().apply {
            put("cliente_id", clienteId)
            put("valor_total", 0.0) // Inicialmente, o valor total é 0
            put("data", formattedDate)
            put("horario", formattedTime)
        }

        val pedidoId = db.insert("Pedido", null, values)
        db.close()

        return pedidoId
    }

    fun criarProdutoPedido(context: Context, pedidoId: Long, produtoId: Int, quantidade: Int): Long {
        val banco = BancoIFTM(context)
        val db = banco.writableDatabase

        val values = ContentValues().apply {
            put("fk_pedidoid", pedidoId)
            put("fk_produtoid", produtoId)
            put("qtd", quantidade)
        }

        val idProdutoPedido = db.insert("ProdutoPedido", null, values)

        db.close()

        return idProdutoPedido
    }


    //ADICIONAR MAIS DE UM PRODUTO A UM PEDIDO
    fun adicionarProdutoAoCarrinho(context: Context, pedidoId: Long, produtoId: Int, quantidade: Int) {
        val banco = BancoIFTM(context)
        val db = banco.writableDatabase

        // Insira o produto no ProdutoPedido
        val values = ContentValues().apply {
            put("fk_pedidoid", pedidoId)
            put("fk_produtoid", produtoId)
            put("qtd", quantidade)
        }

        Log.d("AdicionarProdutoAoCarrinho", "Valores para inserção: Pedido ID: $pedidoId, Produto ID: $produtoId, Quantidade: $quantidade")

        val idProdutoPedido = db.insert("ProdutoPedido", null, values)
        if (idProdutoPedido != -1L) {
            Log.d("AdicionarProdutoAoCarrinho", "ProdutoPedido inserido com sucesso com ID: $idProdutoPedido")
        } else {
            Log.e("AdicionarProdutoAoCarrinho", "Erro ao inserir ProdutoPedido")
        }

        // Atualize o valor total do pedido
        val cursor = db.rawQuery("SELECT valor FROM Produto WHERE idproduto = ?", arrayOf(produtoId.toString()))
        var valorProduto = 0.0
        if (cursor.moveToFirst()) {
            valorProduto = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
        }
        cursor.close()

        val valorTotalProduto = valorProduto * quantidade

        db.execSQL("UPDATE Pedido SET valor_total = valor_total + ? WHERE idpedido = ?", arrayOf(valorTotalProduto.toString(), pedidoId.toString()))

        db.close()
    }


    fun finalizarPedido(context: Context, pedidoId: Long) {
        val banco = BancoIFTM(context)
        val db = banco.writableDatabase

        // Mover itens do ProdutoPedido para o PedidoProduto
        val cursor = db.rawQuery("SELECT * FROM ProdutoPedido WHERE fk_pedidoid = ?", arrayOf(pedidoId.toString()))
        if (cursor.moveToFirst()) {
            do {
                val produtoId = cursor.getInt(cursor.getColumnIndexOrThrow("fk_produtoid"))
                val quantidade = cursor.getInt(cursor.getColumnIndexOrThrow("qtd"))

                val valoresItem = ContentValues().apply {
                    put("fk_pedidoid", pedidoId)
                    put("fk_produtoid", produtoId)
                    put("qtd", quantidade)
                }
                db.insert("ProdutoPedido", null, valoresItem)

                // Opcional: Atualizar estoque ou realizar outras operações necessárias
            } while (cursor.moveToNext())
        }
        cursor.close()

        // Limpar o ProdutoPedido para o pedido atual
        db.delete("ProdutoPedido", "fk_pedidoid = ?", arrayOf(pedidoId.toString()))
        db.close()
    }

    fun listarPedidos(context: Context, listView: ListView) {
        try {
            val linhas = ArrayList<String>()
            val meuAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, linhas)
            listView.adapter = meuAdapter

            val meuBanco = BancoIFTM(context)
            val db = meuBanco.readableDatabase

            val query = """
            SELECT p.idpedido, c.nome AS nome_cliente, pp.fk_produtoid, pp.qtd AS quantidade, p.valor_total, p.data, p.horario
            FROM Pedido p 
            INNER JOIN Cliente c ON p.cliente_id = c.id
            LEFT JOIN ProdutoPedido pp ON p.idpedido = pp.fk_pedidoid
        """


            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val idPedido = cursor.getInt(cursor.getColumnIndexOrThrow("idpedido"))
                val nomeCliente = cursor.getString(cursor.getColumnIndexOrThrow("nome_cliente"))
                val produtoId = cursor.getInt(cursor.getColumnIndexOrThrow("fk_produtoid"))
                val quantidade = cursor.getInt(cursor.getColumnIndexOrThrow("quantidade"))
                val valorTotal = cursor.getDouble(cursor.getColumnIndexOrThrow("valor_total"))
                val data = cursor.getString(cursor.getColumnIndexOrThrow("data"))
                val horario = cursor.getString(cursor.getColumnIndexOrThrow("horario"))

                Log.d("listarPedidos", "Pedido ID: $idPedido, Cliente: $nomeCliente, Produto ID: $produtoId, Quantidade: $quantidade")

                val pedido = """
                ID do Pedido: $idPedido
                Nome do Cliente: $nomeCliente
                ID do Produto: $produtoId
                Quantidade: $quantidade
                Valor Total: R$$valorTotal
                Data: $data
                Horário: $horario
            """.trimIndent()

                linhas.add(pedido)
            }

            cursor.close()
            db.close()
            meuAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    fun deletarPedido(idProduto: Int) {
        try {
            val banco = BancoIFTM(context)
            val db = banco.writableDatabase

            val resultado = db.delete("Pedido", "idpedido = ?", arrayOf(idProduto.toString()))

            if (resultado != -1) {
                Log.i("DeletarPedido", "Pedido deletado com sucesso")
            } else {
                Log.e("DeletarPedido", "Falha ao deletar pedido")
            }

            db.close()
        } catch (e: Exception) {
            Log.e("DeletarPedido", "Erro ao deletar produto: ${e.message}")
        }
    }

    fun buscarPedidoPorId(id: Int): Pedido? {
        val banco = BancoIFTM(context)
        val db = banco.writableDatabase

        val cursor = db.rawQuery("SELECT * FROM Pedido WHERE idpedido = ?", arrayOf(id.toString()))
        return if (cursor.moveToFirst()) {
            val clienteId = cursor.getInt(cursor.getColumnIndexOrThrow("cliente_id"))
            val produtoId = cursor.getInt(cursor.getColumnIndexOrThrow("produto_id"))
            val quantidade = cursor.getInt(cursor.getColumnIndexOrThrow("quantidade"))
            val valorTotal = cursor.getDouble(cursor.getColumnIndexOrThrow("valor_total"))
            val data = cursor.getString(cursor.getColumnIndexOrThrow("data"))
            val horario = cursor.getString(cursor.getColumnIndexOrThrow("horario"))
            cursor.close()
            Pedido(id, clienteId, produtoId, quantidade, valorTotal, data, horario)
        } else {
            cursor.close()
            null
        }
    }

}
