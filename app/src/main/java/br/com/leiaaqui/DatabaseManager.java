package br.com.leiaaqui;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(Context context) {
        super(context, "LeiaAquiDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.criarTabelaLivros(db);
        this.criarTabelaClientes(db);
        this.criarTabelaCategoriaLeitores(db);
        this.criarTabelaCategoriaLivros(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP DATABASE IF EXISTS clientes");
        db.execSQL("DROP DATABASE IF EXISTS livros");
        db.execSQL("DROP DATABASE IF EXISTS categoria_leitores");
        db.execSQL("DROP DATABASE IF EXISTS categoria_livros");
        this.onCreate(db);
    }

    public void criarTabelaLivros(SQLiteDatabase db) {
        String command = "CREATE TABLE livros (" +
                "codigo integer primary key autoincrement," +
                "ISBN integer," +
                "titulo text," +
                "codigo_categoria integer," +
                "autor text," +
                "palavras_chave text," +
                "data_publicacao DATETIME," +
                "numero_edicao integer," +
                "editora text," +
                "numero_paginas integer," +
                "FOREIGN KEY(codigo_categoria) REFERENCES categoria_livros(codigo)" +
                ")";

        db.execSQL(command);
    }

    public void criarTabelaClientes(SQLiteDatabase db) {
        String command = "CREATE TABLE clientes (" +
                "nome text," +
                "endereco text," +
                "celular text," +
                "email text unique," +
                "cpf text primary key unique," +
                "data_nascimento DATETIME," +
                "FOREIGN KEY(codigo_categoria) REFERENCES categoria_leitores(codigo)" +
                ")";
        db.execSQL(command);
    }

    public void criarTabelaCategoriaLivros(SQLiteDatabase db) {
        String command = "CREATE TABLE categoria_livros (" +
                "codigo integer primary key autoincrement," +
                "descricao text," +
                "numero_maximo_emprestimo number," +
                "taxa_multa REAL" +
                ")";
        db.execSQL(command);
    }

    public void criarTabelaCategoriaLeitores(SQLiteDatabase db) {
        String command = "CREATE TABLE categoria_leitores (" +
                "codigo integer primary key autoincrement," +
                "descricao text," +
                "numero_maximo_emprestimo integer" +
                ")";
        db.execSQL(command);
    }


}
