package br.com.leiaaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

public class CategoriaClienteController {
    private SQLiteDatabase db;
    private DatabaseManager banco;

    public CategoriaClienteController(Context context) {
        banco = new DatabaseManager(context);
    }

    public String insert(String descricao, int diasEmprestimo) {
        ContentValues values;
        long resultado;

        db = banco.getWritableDatabase();
        values = new ContentValues();

        values.put(DatabaseManager.getDescricaoCategoriaLeitores(), descricao);
        values.put(DatabaseManager.getNrEmprestimoCategoriaLeitores(), diasEmprestimo);

        resultado = db.insert(DatabaseManager.getTabelaCategoriaLeitores(), null, values);
        db.close();

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public Cursor list() {
        Cursor cursor;

        String[] campos = {banco.getIdCategoriaLeitores(), banco.getDescricaoCategoriaLeitores(),
                banco.getNrEmprestimoCategoriaLeitores()};

        db = banco.getReadableDatabase();
        cursor = db.query(banco.getTabelaCategoriaLeitores(), campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findOne(int id) {
        Cursor cursor;

        String[] campos = {banco.getIdCategoriaLeitores(), banco.getDescricaoCategoriaLeitores(),
                banco.getNrEmprestimoCategoriaLeitores()};

        db = banco.getReadableDatabase();

        String condition = DatabaseManager.getIdCategoriaLeitores() + "=" + id;

        cursor = db.query(DatabaseManager.getTabelaCategoriaLeitores(), campos, condition,
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void update(int id, String descricao, int diasEmprestimo) {
        ContentValues valores;
        db = banco.getWritableDatabase();

        String condition = DatabaseManager.getIdCategoriaLeitores() + "=" + id;

        valores = new ContentValues();
        valores.put(DatabaseManager.getDescricaoCategoriaLeitores(), descricao);
        valores.put(DatabaseManager.getNrEmprestimoCategoriaLeitores(), diasEmprestimo);

        db.update(DatabaseManager.getTabelaCategoriaLeitores(), valores, condition, null);
        db.close();
    }

    public void delete(int id){
        String condition = DatabaseManager.getIdCategoriaLeitores() + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(DatabaseManager.getTabelaCategoriaLeitores(), condition, null);
        db.close();
    }
}
