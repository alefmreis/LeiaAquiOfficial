package br.com.leiaaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LivroController {
    private SQLiteDatabase db;
    private DatabaseManager banco;
    private HashMap<String, String> LivrosProjection;

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

        values.put(DatabaseManager.getIsbnLivros(), ISBN);
        values.put(DatabaseManager.getTituloLivros(), titulo);
        values.put(DatabaseManager.getCodCategoriaLivros(), codigoCategoria);
        values.put(DatabaseManager.getAutorLivros(), autor);
        values.put(DatabaseManager.getPalavrasChaveLivros(), palavrasChave);
        values.put(DatabaseManager.getDtPublicacaoLivros(), dataPublicacao);
        values.put(DatabaseManager.getNrEdicaoLivros(), numeroEdicao);
        values.put(DatabaseManager.getEditoraLivros(), editora);
        values.put(DatabaseManager.getNrPaginasLivros(), numeroPaginas);

        resultado = db.insert(DatabaseManager.getTabelaLivros(), null, values);
        db.close();

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }

    }


    public Cursor list(String tipo, String texto) {
        Cursor cursor;

        /*String selectQuery = "SELECT  l."+ DatabaseManager.getIdLivros() +"," +
                "l."+ DatabaseManager.getIsbnLivros() +"," +
                "l."+DatabaseManager.getTituloLivros() +"," +
                "cl."+DatabaseManager.getDescricaoCategoriaLivros() +"," +
                "l."+DatabaseManager.getAutorLivros() +"," +
                "l."+DatabaseManager.getPalavrasChaveLivros() +"," +
                "l."+DatabaseManager.getDtPublicacaoLivros() +"," +
                "l."+DatabaseManager.getNrEdicaoLivros() +"," +
                "l."+DatabaseManager.getEditoraLivros() +"," +
                "l."+DatabaseManager.getNrPaginasLivros() +
                " FROM " + DatabaseManager.getTabelaLivros() + " l" +
                " INNER JOIN " + DatabaseManager.getTabelaCategoriaLivros() + " cl ON l."+ DatabaseManager.getCodCategoriaLivros() +" = cl."+DatabaseManager.getIdCategoriaLivros();*/

        LivrosProjection = new HashMap<String, String>();
        LivrosProjection.put(DatabaseManager.getIdLivros(), DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getIdLivros());
        LivrosProjection.put(DatabaseManager.getIsbnLivros(), DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getIsbnLivros());
        LivrosProjection.put(DatabaseManager.getTituloLivros(), DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getTituloLivros());
        LivrosProjection.put(DatabaseManager.getDescricaoCategoriaLivros(), DatabaseManager.getTabelaCategoriaLivros()+"."+DatabaseManager.getDescricaoCategoriaLivros());
        LivrosProjection.put(DatabaseManager.getAutorLivros(), DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getAutorLivros());
        LivrosProjection.put(DatabaseManager.getPalavrasChaveLivros(), DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getPalavrasChaveLivros());
        LivrosProjection.put(DatabaseManager.getDtPublicacaoLivros(), DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getDtPublicacaoLivros());
        LivrosProjection.put(DatabaseManager.getNrEdicaoLivros(), DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getNrEdicaoLivros());
        LivrosProjection.put(DatabaseManager.getEditoraLivros(), DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getEditoraLivros());
        LivrosProjection.put(DatabaseManager.getNrPaginasLivros(), DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getNrPaginasLivros());

        String groupBy = DatabaseManager.getTabelaCategoriaLivros()+"."+DatabaseManager.getDescricaoCategoriaLivros();

        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(DatabaseManager.getTabelaLivros() +
                " LEFT JOIN " +DatabaseManager.getTabelaCategoriaLivros() +
                " ON "+DatabaseManager.getTabelaLivros()+"." + DatabaseManager.getCodCategoriaLivros() +
                " = "+DatabaseManager.getTabelaCategoriaLivros()+"."+ DatabaseManager.getIdCategoriaLivros());

        _QB.setProjectionMap(LivrosProjection);
        Log.d("CREATE",tipo);
        SQLiteDatabase _DB = banco.getReadableDatabase();
        Cursor _Result = _QB.query(_DB, null, null, null, groupBy, null, null);
        if(tipo.equals("Titulo")) {
            Log.d("CREATE", tipo);
            _QB.appendWhere(DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getTituloLivros()
                    + " like '%" + texto + "%'");
            _Result = _QB.query(_DB, null, DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getTituloLivros()
                    + " like '%" + texto + "%'", null, groupBy, null, null);
        } else if(tipo.equals("Autor")) {
            _QB.appendWhere(DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getAutorLivros()
                    + " like '%" + texto + "%'");
            _Result = _QB.query(_DB, null, DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getAutorLivros()
                    + " like '%" + texto + "%'", null, groupBy, null, null);
        } else if(tipo.equals("Editora")) {
            _QB.appendWhere(DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getEditoraLivros()
                    + " like '%" + texto + "%'");
            _Result = _QB.query(_DB, null, DatabaseManager.getTabelaLivros()+"."+DatabaseManager.getEditoraLivros()
                    + " like '%" + texto + "%'", null, groupBy, null, null);
        } else {
            _Result = _QB.query(_DB, null, null, null, groupBy, null, null);
        }



        /*String[] campos = {banco.getIdLivros(),banco.getIsbnLivros(),banco.getTituloLivros(),banco.getDescricaoCategoriaLivros(),
                banco.getAutorLivros(), banco.getPalavrasChaveLivros(),banco.getDtPublicacaoLivros(),
                banco.getNrEdicaoLivros(),banco.getEditoraLivros(),banco.getNrPaginasLivros()};*/


        if (_Result != null) {
            _Result.moveToFirst();
        }
        _DB.close();
        return _Result;
    }

    public Cursor findOne(int id) {
        Cursor cursor;

        String[] campos = {banco.getIdLivros(),banco.getIsbnLivros(),banco.getTituloLivros(),
                                           banco.getCodCategoriaLivros(),banco.getAutorLivros(),
                                           banco.getPalavrasChaveLivros(),banco.getDtPublicacaoLivros(),
                                           banco.getNrEdicaoLivros(),banco.getEditoraLivros(),banco.getNrPaginasLivros()};

        db = banco.getReadableDatabase();

        String condition = DatabaseManager.getIdLivros() + "=" + id;

        cursor = db.query(DatabaseManager.getTabelaLivros(), campos, condition,
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findOneCat(String cat) {
        Cursor cursor;

        String[] campos = {banco.getIdCategoriaLivros()};

        db = banco.getReadableDatabase();

        String condition = DatabaseManager.getDescricaoCategoriaLivros() + "= '" + cat + "'";

        cursor = db.query(DatabaseManager.getTabelaCategoriaLivros(), campos, condition,
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findOneCatId(int cat) {
        Cursor cursor;

        String[] campos = {banco.getDescricaoCategoriaLivros()};

        db = banco.getReadableDatabase();

        String condition = DatabaseManager.getIdCategoriaLivros() + "= '" + cat + "'";

        cursor = db.query(DatabaseManager.getTabelaCategoriaLivros(), campos, condition,
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void update(int id,int ISBN, String titulo, int codigoCategoria, String autor,
                       String palavrasChave, String dataPublicacao,
                       int numeroEdicao, String editora, int numeroPaginas) {
        ContentValues valores;
        db = banco.getWritableDatabase();

        String condition = DatabaseManager.getIdLivros() + "=" + id;

        valores = new ContentValues();
        valores.put(DatabaseManager.getIsbnLivros(), ISBN);
        valores.put(DatabaseManager.getTituloLivros(), titulo);
        valores.put(DatabaseManager.getCodCategoriaLivros(), codigoCategoria);
        valores.put(DatabaseManager.getAutorLivros(), autor);
        valores.put(DatabaseManager.getPalavrasChaveLivros(), palavrasChave);
        valores.put(DatabaseManager.getDtPublicacaoLivros(), dataPublicacao);
        valores.put(DatabaseManager.getNrEdicaoLivros(), numeroEdicao);
        valores.put(DatabaseManager.getEditoraLivros(), editora);
        valores.put(DatabaseManager.getNrPaginasLivros(), numeroPaginas);

        db.update(DatabaseManager.getTabelaLivros(), valores, condition, null);
        db.close();
    }

    public void delete(int id){
        String condition = DatabaseManager.getIdLivros() + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(DatabaseManager.getTabelaLivros(), condition, null);
        db.close();
    }

    public List<String> getCategoriasLivros() {
        db = banco.getReadableDatabase();
        String selectQuery = "SELECT  " +
                DatabaseManager.getIdCategoriaLivros() + "," +
                DatabaseManager.getDescricaoCategoriaLivros() +
                " FROM " + DatabaseManager.getTabelaCategoriaLivros();

        List<String> catLivros = new ArrayList<String>() ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Integer i=0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                catLivros.add(cursor.getColumnIndex(DatabaseManager.getIdCategoriaLivros()),cursor.getString(cursor.getColumnIndex(DatabaseManager.getDescricaoCategoriaLivros())));
                i+=1;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return catLivros;

    }


}
