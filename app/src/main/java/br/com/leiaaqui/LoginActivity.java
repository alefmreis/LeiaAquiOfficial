package br.com.leiaaqui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;

public class LoginActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 700;
    EditText campoUsuario;
    EditText campoSenha;
    Button btnLoginAcesso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoUsuario = (EditText) findViewById(R.id.txtEmail);
        campoSenha = (EditText) findViewById(R.id.txtPassword);
        btnLoginAcesso = (Button) findViewById(R.id.btnLogin);

        btnLoginAcesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(campoUsuario.getText().length() == 0 || campoSenha.getText().length() == 0) {
                    Toast.makeText(getApplication(),
                            "Os campos Login e Senha são obrigatórios!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplication(),
                            "Seja bem-vindo "+ campoUsuario.getText().toString() +"!",
                            Toast.LENGTH_LONG).show();

                    campoUsuario.setText("");
                    campoSenha.setText("");


                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            Intent i = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            startActivity(i);

                            finish();
                        }
                    }, SPLASH_TIME_OUT);


                }

            }
        });
    }
}
