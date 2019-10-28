package br.com.leiaaqui;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "LeiaAquiDatabase";

    private static final String TABELA_LIVROS = "livros";
    private static final String ID_LIVROS = "_id";
    private static final String ISBN_LIVROS = "isbn";
    private static final String TITULO_LIVROS = "titulo";
    private static final String COD_CATEGORIA_LIVROS = "cod_categoria";
    private static final String AUTOR_LIVROS = "autor";
    private static final String PALAVRAS_CHAVE_LIVROS = "palavras_chave";
    private static final String DT_PUBLICACAO_LIVROS = "dt_publicacao";
    private static final String NR_EDICAO_LIVROS = "nr_edicao";
    private static final String EDITORA_LIVROS = "editora";
    private static final String NR_PAGINAS_LIVROS = "nr_paginas";

    private static final String TABELA_CLIENTES = "clientes";
    private static final String ID_CLIENTES = "_id";
    private static final String NOME_CLIENTES = "nome";
    private static final String ENDERECO_CLIENTES = "endereco";
    private static final String CELULAR_CLIENTES = "celular";
    private static final String EMAIL_CLIENTES = "email";
    private static final String CPF_CLIENTES = "cpf";
    private static final String DT_NASCIMENTO_CLIENTES = "dt_nascimento";

    private static final String TABELA_CATEGORIA_LIVROS = "categoria_livros";
    private static final String ID_CATEGORIA_LIVROS = "_id";
    private static final String DESCRICAO_CATEGORIA_LIVROS = "descricao";
    private static final String NR_EMPRESTIMO_CATEGORIA_LIVROS = "nr_maximo_emprestimo";
    private static final String TAXA_MULTA_CATEGORIA_LIVROS = "taxa_multa";

    private static final String TABELA_CATEGORIA_LEITORES = "categoria_leitores";
    private static final String ID_CATEGORIA_LEITORES = "_id";
    private static final String DESCRICAO_CATEGORIA_LEITORES = "descricao";
    private static final String NR_EMPRESTIMO_CATEGORIA_LEITORES = "nr_maximo_emprestimo";

    private static final int VERSAO = 3;

    public DatabaseManager(Context context) {
        super(context, getNomeBanco(), null, VERSAO);
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
        db.execSQL("DROP TABLE IF EXISTS " + getTabelaClientes());
        db.execSQL("DROP TABLE IF EXISTS " + getTabelaLivros());
        db.execSQL("DROP TABLE IF EXISTS " + getTabelaCategoriaLivros());
        db.execSQL("DROP TABLE IF EXISTS " + getTabelaCategoriaLeitores());
        this.onCreate(db);
    }

    public void criarTabelaLivros(SQLiteDatabase db) {

        String command = "CREATE TABLE " + getTabelaLivros() + " (" +
                getIdLivros() + " integer primary key autoincrement," +
                getIsbnLivros() + " integer," +
                getTituloLivros() + " text," +
                getCodCategoriaLivros() + " integer," +
                getAutorLivros() + " text," +
                getPalavrasChaveLivros() + " text," +
                getDtPublicacaoLivros() + " DATETIME," +
                getNrEdicaoLivros() + " integer," +
                getEditoraLivros() + " text," +
                getNrPaginasLivros() + " integer" +
                ")";

        db.execSQL(command);

        /*String commandInsert = "INSERT INTO "+getTabelaLivros()+" VALUES (" +
                " 1," +
                " 12345," +
                " 'teste'," +
                " 1," +
                " 'tester'," +
                " 'test,teste'," +
                " '2019-10-23'," +
                " 1," +
                " 'teste livros'," +
                " 1" +
                ")";

        db.execSQL(commandInsert);*/
    }

    public void criarTabelaClientes(SQLiteDatabase db) {
        String command = "CREATE TABLE " + getTabelaClientes() + " (" +
                getIdClientes() + " integer primary key autoincrement," +
                getNomeClientes() + " text," +
                getEnderecoClientes() + " text," +
                getCelularClientes() + " text," +
                getEmailClientes() + " text unique," +
                getCpfClientes() + " text unique," +
                getDtNascimentoClientes() + " DATETIME" +
                ")";
        db.execSQL(command);
    }

    public void criarTabelaCategoriaLivros(SQLiteDatabase db) {
        String command = "CREATE TABLE " + getTabelaCategoriaLivros() + " (" +
                getIdCategoriaLivros() + " integer primary key autoincrement," +
                getDescricaoCategoriaLivros() + " text," +
                getNrEmprestimoCategoriaLivros() + " number," +
                getTaxaMultaCategoriaLivros() + " REAL" +
                ")";
        db.execSQL(command);
    }

    public void criarTabelaCategoriaLeitores(SQLiteDatabase db) {
        String command = "CREATE TABLE " + getTabelaCategoriaLeitores() + " (" +
                getIdCategoriaLeitores() + " integer primary key autoincrement," +
                getDescricaoCategoriaLeitores() + " text," +
                getNrEmprestimoCategoriaLeitores() + " integer" +
                ")";
        db.execSQL(command);
    }


    public static String getNomeBanco() {
        return NOME_BANCO;
    }

    public static String getTabelaLivros() {
        return TABELA_LIVROS;
    }

    public static String getIdLivros() {
        return ID_LIVROS;
    }

    public static String getIsbnLivros() {
        return ISBN_LIVROS;
    }

    public static String getTituloLivros() {
        return TITULO_LIVROS;
    }

    public static String getCodCategoriaLivros() {
        return COD_CATEGORIA_LIVROS;
    }

    public static String getAutorLivros() {
        return AUTOR_LIVROS;
    }

    public static String getPalavrasChaveLivros() {
        return PALAVRAS_CHAVE_LIVROS;
    }

    public static String getDtPublicacaoLivros() {
        return DT_PUBLICACAO_LIVROS;
    }

    public static String getNrEdicaoLivros() {
        return NR_EDICAO_LIVROS;
    }

    public static String getEditoraLivros() {
        return EDITORA_LIVROS;
    }

    public static String getNrPaginasLivros() {
        return NR_PAGINAS_LIVROS;
    }

    public static String getTabelaClientes() {
        return TABELA_CLIENTES;
    }

    public static String getIdClientes() {
        return ID_CLIENTES;
    }

    public static String getNomeClientes() {
        return NOME_CLIENTES;
    }

    public static String getEnderecoClientes() {
        return ENDERECO_CLIENTES;
    }

    public static String getCelularClientes() {
        return CELULAR_CLIENTES;
    }

    public static String getEmailClientes() {
        return EMAIL_CLIENTES;
    }

    public static String getCpfClientes() {
        return CPF_CLIENTES;
    }

    public static String getDtNascimentoClientes() {
        return DT_NASCIMENTO_CLIENTES;
    }

    public static String getTabelaCategoriaLivros() {
        return TABELA_CATEGORIA_LIVROS;
    }

    public static String getIdCategoriaLivros() {
        return ID_CATEGORIA_LIVROS;
    }

    public static String getDescricaoCategoriaLivros() {
        return DESCRICAO_CATEGORIA_LIVROS;
    }

    public static String getNrEmprestimoCategoriaLivros() {
        return NR_EMPRESTIMO_CATEGORIA_LIVROS;
    }

    public static String getTaxaMultaCategoriaLivros() {
        return TAXA_MULTA_CATEGORIA_LIVROS;
    }

    public static String getTabelaCategoriaLeitores() {
        return TABELA_CATEGORIA_LEITORES;
    }

    public static String getIdCategoriaLeitores() {
        return ID_CATEGORIA_LEITORES;
    }

    public static String getDescricaoCategoriaLeitores() {
        return DESCRICAO_CATEGORIA_LEITORES;
    }

    public static String getNrEmprestimoCategoriaLeitores() {
        return NR_EMPRESTIMO_CATEGORIA_LEITORES;
    }

}
