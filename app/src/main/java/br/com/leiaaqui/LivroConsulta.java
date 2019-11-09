package br.com.leiaaqui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LivroConsulta extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lista;
    private String tipo;
    private String txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_consulta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obter dados da Activity que chamou esta:
        // Recuperar Intent passado:
        Intent intentRecebida = getIntent();
        // Recuperar Bundle vinculado ao Intent:
        Bundle bundle = intentRecebida.getExtras();
        // Recuperar texto do Bundle:
        tipo = bundle.getString(BuscaLivro.KEY_INFO_TIPO).toString();
        txt = bundle.getString(BuscaLivro.KEY_INFO_TEXTO).toString();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LivroConsulta.this, LivroInserir.class);
                startActivity(intent);
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LivroController banco = new LivroController(getBaseContext());
        final Cursor cursor = banco.list(tipo,txt);

        String[] campos = new String[]{DatabaseManager.getIsbnLivros(),DatabaseManager.getTituloLivros(),
                DatabaseManager.getDescricaoCategoriaLivros(),DatabaseManager.getAutorLivros(),
                DatabaseManager.getPalavrasChaveLivros(),DatabaseManager.getDtPublicacaoLivros(),
                DatabaseManager.getNrEdicaoLivros(),DatabaseManager.getEditoraLivros(),DatabaseManager.getNrPaginasLivros()};

        int[] idViews = new int[]{R.id.ISBNValue, R.id.TituloValue, R.id.CatLivroValue,
                R.id.AutorValue, R.id.PCValue, R.id.DtPublicacaoValue,
                R.id.NrEdicaoValue, R.id.EditoraValue, R.id.NrPagsValue};
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.content_livro_consulta, cursor, campos, idViews, 0
        );

        lista = (ListView) findViewById(R.id.listView5);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManager.getIdCategoriaLivros()));
                Intent intent = new Intent(LivroConsulta.this, LivroAtualiza.class);
                intent.putExtra("codigo", codigo);
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

        Intent intent = new Intent(LivroConsulta.this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;

    }
}
