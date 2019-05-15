package br.com.achapet.Activity.Pessoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;

import br.com.achapet.Modal.PessoaModal;
import br.com.achapet.R;

public class CadastroPessoaActivity extends AppCompatActivity implements View.OnClickListener{

    private PessoaModal pessoaModal;

    private String valName;
    private String valEmail;
    private String valPassword;
    private Uri valFoto;

    private EditText editTextEmail;
    private EditText editTextNomeCompleto;
    private EditText editTextPassword;

    private Button ButtonCadastro;

    private ProgressBar toolbarprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_cadastro);

        //findViewById(R.id.toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // Button listeners
        ButtonCadastro = findViewById(R.id.singup_in_button);
        ButtonCadastro.setOnClickListener(this);

        editTextEmail = findViewById(R.id.cadastro_pessoa_email);
        editTextNomeCompleto = findViewById(R.id.cadastro_pessoa_nome);
        editTextPassword = findViewById(R.id.cadastro_pessoa_senha);

        toolbarprogress = findViewById(R.id.toolbarprogress);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pessoaModal = new PessoaModal();
    }

    public void singUp(){
        valName = editTextNomeCompleto.getText().toString();
        valEmail = editTextEmail.getText().toString();
        valPassword = editTextPassword.getText().toString();
        if( !valName.isEmpty()  && !valEmail.isEmpty() && !valPassword.isEmpty() ){
            ButtonCadastro.setEnabled(false);
            toolbarprogress.setVisibility(View.VISIBLE);
            pessoaModal.getmAuth().createUserWithEmailAndPassword(valEmail, valPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        pessoaModal.atualizarPerfil(valName, valFoto);
                        pessoaModal = new PessoaModal();
                        //Log.e("Login Registro", pessoaModal.toString());
                        finish();
                    }else{
                        String erro = ((FirebaseAuthException) task.getException()).getErrorCode();
                        pessoaModal.validar(erro, editTextEmail, editTextPassword);
                        //Log.e("Login Registro",task.getException().toString());
                        toolbarprogress.setVisibility(View.INVISIBLE);
                        ButtonCadastro.setEnabled(true);
                    }
                }
            });
        }else{
            editTextNomeCompleto.setError("Campo Obrigatorio.");
            editTextEmail.setError("Campo Obrigatorio.");
            editTextPassword.setError("Campo Obrigatorio.");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singup_in_button:
                singUp();
                break;
        }
    }
}