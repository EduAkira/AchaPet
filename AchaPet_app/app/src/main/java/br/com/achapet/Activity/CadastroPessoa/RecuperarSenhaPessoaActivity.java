package br.com.achapet.Activity.CadastroPessoa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import br.com.achapet.Modal.PessoaModal;
import br.com.achapet.R;

public class RecuperarSenhaPessoaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edEmail;

    private Button bt_recuperar_button;

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

        bt_recuperar_button = findViewById(R.id.recuperar_button);
        bt_recuperar_button.setOnClickListener(this);
        edEmail = findViewById(R.id.recuperar_email);
    }

    public void esqueciSenha(String emailAddress){
        bt_recuperar_button.setEnabled(false);
        if(!edEmail.getText().toString().isEmpty()){
            pessoaModal.getmAuth().sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //email Enviado
                        bt_recuperar_button.setEnabled(false);
                        new AlertDialog.Builder(RecuperarSenhaPessoaActivity.this).setTitle("Recuperação de Senha")
                                .setMessage("Voce recebera em instantes um email para efetuar a troca da senha.")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i){finish();}}).show();
                    }else{
                        bt_recuperar_button.setEnabled(true);
                    }
                }
            });
        }else{
            edEmail.setError("Campo Obrigatorio.");
            bt_recuperar_button.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recuperar_button:
                esqueciSenha(edEmail.getText().toString());
                break;
        }
    }
}
