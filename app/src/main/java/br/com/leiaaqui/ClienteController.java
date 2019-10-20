package br.com.leiaaqui;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;

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

        values.put("nome", nome);
        values.put("endereco", endereco);
        values.put("celular", celular);
        values.put("email", email);
        values.put("cpf", cpf);
        values.put("data_nascimento", dataNascimento);
        values.put("codigo_categoria", categoriaCliente);

        resultado = db.insert("clientes", null, values);
        db.close();

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }
}
