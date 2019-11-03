package br.com.leiaaqui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class LivroAtualiza extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    LivroController crud;
    EditText isbn;
    EditText tituloLivro;
    EditText autorLivro;
    EditText palavrasChaveLivro;
    EditText dtPublic;
    EditText nrEdicao;
    EditText editoraLivro;
    EditText nrPags;
    String codigo;
    Spinner codCategoria;
    int codCatLivro = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_atualiza);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        codCategoria = (Spinner) findViewById((R.id.spinnerCatLivrosAt));
        codCategoria.setOnItemSelectedListener(this);
        loadCategoriasLivros();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        codigo = this.getIntent().getStringExtra("codigo");
        crud = new LivroController(getBaseContext());

        isbn = (EditText) findViewById(R.id.ISBNat);
        tituloLivro= (EditText) findViewById((R.id.tituloLivroAt));
        autorLivro = (EditText) findViewById(R.id.autorLivroAt);
        palavrasChaveLivro = (EditText) findViewById((R.id.palavrasChaveAt));
        dtPublic = (EditText) findViewById((R.id.dtPublicacaoAt));
        nrEdicao= (EditText) findViewById(R.id.nrEdicaoAt);
        editoraLivro= (EditText) findViewById((R.id.editoraAt));
        nrPags= (EditText) findViewById((R.id.nrPagsAt));

        Button alterar = (Button) findViewById(R.id.buttonAltLivro);

        Cursor cursor = crud.findOne(Integer.parseInt(codigo));
        Cursor cursor2 = crud.findOneCatId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseManager.getCodCategoriaLivros()))));

        isbn.setText(cursor.getString(cursor.getColumnIndex(DatabaseManager.getIsbnLivros())));
        tituloLivro.setText(cursor.getString(cursor.getColumnIndex(DatabaseManager.getTituloLivros())));
        codCategoria.setSelection(((ArrayAdapter)codCategoria.getAdapter()).getPosition(cursor2.getString(cursor2.getColumnIndex(DatabaseManager.getDescricaoCategoriaLivros()))));
        autorLivro.setText(cursor.getString(cursor.getColumnIndex(DatabaseManager.getAutorLivros())));
        palavrasChaveLivro.setText(cursor.getString(cursor.getColumnIndex(DatabaseManager.getPalavrasChaveLivros())));
        dtPublic.setText(cursor.getString(cursor.getColumnIndex(DatabaseManager.getDtPublicacaoLivros())));
        nrEdicao.setText(cursor.getString(cursor.getColumnIndex(DatabaseManager.getNrEdicaoLivros())));
        editoraLivro.setText(cursor.getString(cursor.getColumnIndex(DatabaseManager.getEditoraLivros())));
        nrPags.setText(cursor.getString(cursor.getColumnIndex(DatabaseManager.getNrPaginasLivros())));

        codCategoria.setOnItemSelectedListener(this);

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crud.update(Integer.parseInt(codigo), Integer.parseInt(isbn.getText().toString()), tituloLivro.getText().toString(),
                        codCatLivro, autorLivro.getText().toString(), palavrasChaveLivro.getText().toString(),
                        dtPublic.getText().toString(), Integer.parseInt(nrEdicao.getText().toString()),
                        editoraLivro.getText().toString(), Integer.parseInt(nrPags.getText().toString()));

                Intent intent = new Intent(LivroAtualiza.this, LivroConsulta.class);
                startActivity(intent);
                finish();
            }
        });

        Button deletar = (Button) findViewById(R.id.buttonDelLivro);

        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crud.delete(Integer.parseInt(codigo));
                Intent intent = new Intent(LivroAtualiza.this, LivroConsulta.class);
                startActivity(intent);
                finish();
            }
        });

        Button cancelar = (Button) findViewById(R.id.buttonCancelLivroAt);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LivroAtualiza.this, LivroConsulta.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.livro_consulta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == 'a') {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String id = item.toString();

        Intent intent = new Intent(LivroAtualiza.this, MainActivity.class);
        startActivity(intent);
        finish();

        return true;
    }

    private void loadCategoriasLivros(){
        ArrayAdapter<String> spinnerAdapter;
        LivroController db = new LivroController(getApplicationContext());
        List<String> catLivros = db.getCategoriasLivros();
        spinnerAdapter = new ArrayAdapter<String>(LivroAtualiza.this,
                android.R.layout.simple_spinner_item, catLivros);
        codCategoria.setAdapter(spinnerAdapter);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        String label = parentView.getItemAtPosition(position).toString();

        LivroController db = new LivroController(getApplicationContext());
        Cursor idCatLivros = db.findOneCat(label);

        codCatLivro = Integer.parseInt(idCatLivros.getString(0));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
