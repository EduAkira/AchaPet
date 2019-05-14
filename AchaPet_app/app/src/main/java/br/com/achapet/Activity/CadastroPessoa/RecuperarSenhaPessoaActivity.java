package br.com.achapet.Activity.CadastroPessoa;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.achapet.Modal.PessoaModal;
import br.com.achapet.R;

public class RecuperarSenhaPessoaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edEmail;

    private PessoaModal pessoaModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha_pessoa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pessoaModal = new PessoaModal();

        findViewById(R.id.recuperar_button).setOnClickListener(this);

        edEmail = findViewById(R.id.recuperar_email);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recuperar_button:
                pessoaModal.esqueciSenha(edEmail.getText().toString());
                break;
        }
    }
}
