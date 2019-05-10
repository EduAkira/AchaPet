package br.com.achapet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        // Button listeners
        findViewById(R.id.singup_in_button).setOnClickListener(this);

        editTextEmail = findViewById(R.id.cadastro_pessoa_email);
        editTextNomeCompleto = findViewById(R.id.cadastro_pessoa_nome);
        editTextPassword = findViewById(R.id.cadastro_pessoa_senha);
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
            pessoaModal.getmAuth().createUserWithEmailAndPassword(valEmail, valPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        pessoaModal.atualizarPerfil(valName, valFoto);
                        pessoaModal = new PessoaModal();
                        Log.e("Login Registro", pessoaModal.toString());
                    }else{
                        Log.e("Login Registro",task.getException().toString());
                    }
                }
            });
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