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
import android.widget.Button;
import android.widget.EditText;

public class CategoriaClienteAtualiza extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText descricao;
    EditText numeroDias;
    CategoriaClienteController crud;
    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_cliente_atualiza);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        codigo = this.getIntent().getStringExtra("codigo");
        crud = new CategoriaClienteController(getBaseContext());

        descricao = (EditText) findViewById(R.id.des);
        numeroDias = (EditText) findViewById((R.id.atualizaEmprestimoDiasLeitor));

        Button alterar = (Button) findViewById(R.id.buttonAltererCategoriaLeitor);

        Cursor cursor = crud.findOne(Integer.parseInt(codigo));

        descricao.setText(cursor.getString(cursor.getColumnIndex(DatabaseManager.getDescricaoCategoriaLeitores())));
        numeroDias.setText(cursor.getString(cursor.getColumnIndex(DatabaseManager.getNrEmprestimoCategoriaLeitores())));


        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crud.update(Integer.parseInt(codigo), descricao.getText().toString(),
                        Integer.parseInt(numeroDias.getText().toString()));

                Intent intent = new Intent(CategoriaClienteAtualiza.this, CategoriaClienteConsulta.class);
                startActivity(intent);
                finish();
            }
        });

        Button deletar = (Button) findViewById(R.id.button3);

        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crud.delete(Integer.parseInt(codigo));
                Intent intent = new Intent(CategoriaClienteAtualiza.this, CategoriaClienteConsulta.class);
                startActivity(intent);
                finish();
            }
        });

        Button cancelar = (Button) findViewById(R.id.button2);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriaClienteAtualiza.this, CategoriaClienteConsulta.class);
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
        getMenuInflater().inflate(R.menu.categoria_cliente_consulta, menu);
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

        Intent intent = new Intent(CategoriaClienteAtualiza.this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
