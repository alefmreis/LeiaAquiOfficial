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

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
}
