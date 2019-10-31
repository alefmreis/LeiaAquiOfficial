package br.com.leiaaqui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract;

public class ClienteController {
    private SQLiteDatabase db;
    private DatabaseManager banco;

    public ClienteController(Context context) {
        banco = new DatabaseManager(context);
    }

    public String insert(String nome, String endereco, String celular, String email,
                         String cpf, String dataNascimento, String categoriaCliente) {

        ContentValues values;
        long resultado;

        db = banco.getWritableDatabase();
        values = new ContentValues();

        values.put(DatabaseManager.getNomeClientes(), nome);
        values.put(DatabaseManager.getEnderecoClientes(), endereco);
        values.put(DatabaseManager.getCelularClientes(), celular);
        values.put(DatabaseManager.getEmailClientes(), email);
        values.put(DatabaseManager.getCpfClientes(), cpf);
        values.put(DatabaseManager.getDtNascimentoClientes(), dataNascimento);
//        values.put(DatabaseManager.getCodCategoriaLeitores(), categoriaCliente);

        resultado = db.insert("clientes", null, values);
        db.close();

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public Cursor list() {
        Cursor cursor;

        String[] campos = {DatabaseManager.getIdClientes(), DatabaseManager.getNomeClientes(),
                DatabaseManager.getEnderecoClientes(), DatabaseManager.getEmailClientes(),
                DatabaseManager.getCelularClientes(), DatabaseManager.getCpfClientes(),
                DatabaseManager.getDtNascimentoClientes()};

        db = banco.getReadableDatabase();
        cursor = db.query(banco.getTabelaClientes(), campos,
                null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findOne(int id) {
        Cursor cursor;

        String[] campos = {DatabaseManager.getIdClientes(), DatabaseManager.getNomeClientes(),
                DatabaseManager.getEnderecoClientes(), DatabaseManager.getEmailClientes(),
                DatabaseManager.getCelularClientes(), DatabaseManager.getCpfClientes(),
                DatabaseManager.getDtNascimentoClientes()};

        db = banco.getReadableDatabase();

        String condition = DatabaseManager.getIdLivros() + "=" + id;

        cursor = db.query(DatabaseManager.getTabelaClientes(), campos, condition,
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void update(int id, String nome, String endereco, String celular, String email,
                       String cpf, String dataNascimento, String categoriaCliente) {

        ContentValues valores;

        db = banco.getReadableDatabase();

        String condition = DatabaseManager.getIdLivros() + "=" + id;

        valores = new ContentValues();

        valores.put(DatabaseManager.getNomeClientes(), nome);
        valores.put(DatabaseManager.getEnderecoClientes(), endereco);
        valores.put(DatabaseManager.getCelularClientes(), celular);
        valores.put(DatabaseManager.getEmailClientes(), email);
        valores.put(DatabaseManager.getCpfClientes(), cpf);
        valores.put(DatabaseManager.getDtNascimentoClientes(), dataNascimento);

        db.update(DatabaseManager.getTabelaClientes(), valores, condition, null);
        db.close();
    }

    public void delete(int id){
        String condition = DatabaseManager.getIdClientes() + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(DatabaseManager.getTabelaClientes(), condition, null);
        db.close();
    }
}
