package br.com.leiaaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CategoriaLivroController {
    private SQLiteDatabase db;
    private DatabaseManager banco;

    public CategoriaLivroController(Context context) {
        banco = new DatabaseManager(context);
    }

    public String insert(String descricao, int diasEmprestimo, double multa) {
        ContentValues values;
        long resultado;

        db = banco.getWritableDatabase();
        values = new ContentValues();

        values.put("descricao", descricao);
        values.put("numero_maximo_emprestimo", diasEmprestimo);
        values.put("taxa_multa", multa);

        resultado = db.insert("categoria_livros", null, values);
        db.close();

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }
}
