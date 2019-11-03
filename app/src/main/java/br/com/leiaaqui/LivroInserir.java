package br.com.leiaaqui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LivroInserir extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    Spinner codCategoria;
    int codCatLivro = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_inserir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        codCategoria = (Spinner) findViewById((R.id.spinnerCatLivros));
        codCategoria.setOnItemSelectedListener(this);
        loadCategoriasLivros();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button criarCategoria = (Button) findViewById(R.id.buttonLivro);
        criarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LivroController crud = new LivroController(getBaseContext());
                EditText isbn = (EditText) findViewById(R.id.ISBN);
                EditText tituloLivro= (EditText) findViewById((R.id.tituloLivro));
                EditText autorLivro = (EditText) findViewById(R.id.autorLivro);
                EditText palavrasChaveLivro = (EditText) findViewById((R.id.palavrasChave));
                EditText dtPublic = (EditText) findViewById((R.id.dtPublicacao));
                EditText nrEdicao= (EditText) findViewById(R.id.nrEdicao);
                EditText editoraLivro= (EditText) findViewById((R.id.editora));
                EditText nrPags= (EditText) findViewById((R.id.nrPags));

                int ISBN = Integer.parseInt(isbn.getText().toString());
                String titulo = tituloLivro.getText().toString();
                int codigoCategoria = codCatLivro;
                String autor = autorLivro.getText().toString();
                String palavrasChave = palavrasChaveLivro.getText().toString();
                String dataPublicacao = dtPublic.getText().toString();
                int numeroEdicao = Integer.parseInt(nrEdicao.getText().toString());
                String editora = editoraLivro.getText().toString();
                int numeroPaginas = Integer.parseInt(nrPags.getText().toString());

                String resultado = crud.insert(ISBN, titulo, codigoCategoria, autor, palavrasChave,
                        dataPublicacao, numeroEdicao, editora, numeroPaginas);

                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LivroInserir.this, LivroConsulta.class);
                startActivity(intent);
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


        Intent intent = new Intent(LivroInserir.this, MainActivity.class);
        startActivity(intent);
        finish();

        return true;
    }

    private void loadCategoriasLivros(){
        ArrayAdapter<String> spinnerAdapter;
        LivroController db = new LivroController(getApplicationContext());
        List<String> catLivros = db.getCategoriasLivros();
        spinnerAdapter = new ArrayAdapter<String>(LivroInserir.this,
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
