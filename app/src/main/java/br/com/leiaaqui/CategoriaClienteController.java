package br.com.leiaaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

        values.put("descricao", descricao);
        values.put("numero_maximo_emprestimo", diasEmprestimo);

        resultado = db.insert("categoria_leitores", null, values);
        db.close();

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }
}
