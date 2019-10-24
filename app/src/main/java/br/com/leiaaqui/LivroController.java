package br.com.leiaaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LivroController {
    private SQLiteDatabase db;
    private DatabaseManager banco;

    public LivroController(Context context) {
        banco = new DatabaseManager(context);
    }

    public String insert(int ISBN, String titulo, int codigoCategoria, String autor,
                         String palavrasChave, String dataPublicacao,
                         int numeroEdicao, String editora, int numeroPaginas) {

        ContentValues values;
        long resultado;

        db = banco.getWritableDatabase();
        values = new ContentValues();

        values.put("ISBN", ISBN);
        values.put("titulo", titulo);
        values.put("codigo_categoria", codigoCategoria);
        values.put("autor", autor);
        values.put("palavras_chave", palavrasChave);
        values.put("data_publicacao", dataPublicacao);
        values.put("numero_edicao", numeroEdicao);
        values.put("editora", editora);
        values.put("numero_paginas", numeroPaginas);

        resultado = db.insert("livros", null, values);
        db.close();

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }

    }


    public Cursor carregaDadosLivros(){
        Cursor cursor;
        String[] campos = {DatabaseManager.getIdLivros(),DatabaseManager.getTituloLivros()};
        db = banco.getReadableDatabase();
        cursor = db.query(DatabaseManager.getTabelaLivros(), campos, null, null, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


}
