package br.com.leiaaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CategoriaLivroController {
    private SQLiteDatabase db;
    private DatabaseManager banco;

    public CategoriaLivroController(Context context) {
        banco = new DatabaseManager(context);
    }

    public String insert(String descricao, int diasEmprestimo, double txMulta) {
        ContentValues values;
        long resultado;

        db = banco.getWritableDatabase();
        values = new ContentValues();

        values.put(DatabaseManager.getDescricaoCategoriaLivros(), descricao);
        values.put(DatabaseManager.getNrEmprestimoCategoriaLivros(), diasEmprestimo);
        values.put(DatabaseManager.getTaxaMultaCategoriaLivros(), txMulta);

        resultado = db.insert(DatabaseManager.getTabelaCategoriaLivros(), null, values);
        db.close();

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public Cursor list() {
        Cursor cursor;

        String[] campos = {banco.getIdCategoriaLivros(), banco.getDescricaoCategoriaLivros(),
                banco.getNrEmprestimoCategoriaLivros(), banco.getTaxaMultaCategoriaLivros()};

        db = banco.getReadableDatabase();
        cursor = db.query(banco.getTabelaCategoriaLivros(), campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findOne(int id) {
        Cursor cursor;

        String[] campos = {banco.getIdCategoriaLivros(), banco.getDescricaoCategoriaLivros(),
                banco.getNrEmprestimoCategoriaLivros(), banco.getTaxaMultaCategoriaLivros()};

        db = banco.getReadableDatabase();

        String condition = DatabaseManager.getIdCategoriaLivros() + "=" + id;

        cursor = db.query(DatabaseManager.getTabelaCategoriaLivros(), campos, condition,
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void update(int id, String descricao, int diasEmprestimo, double txMulta) {
        ContentValues valores;
        db = banco.getWritableDatabase();

        String condition = DatabaseManager.getIdCategoriaLivros() + "=" + id;

        valores = new ContentValues();
        valores.put(DatabaseManager.getDescricaoCategoriaLivros(), descricao);
        valores.put(DatabaseManager.getNrEmprestimoCategoriaLivros(), diasEmprestimo);
        valores.put(DatabaseManager.getTaxaMultaCategoriaLivros(), txMulta);

        db.update(DatabaseManager.getTabelaCategoriaLivros(), valores, condition, null);
        db.close();
    }

    public void delete(int id){
        String condition = DatabaseManager.getIdCategoriaLivros() + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(DatabaseManager.getTabelaCategoriaLivros(), condition, null);
        db.close();
    }
}
