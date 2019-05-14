package br.com.achapet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.achapet.Activity.CadastroPessoa.LoginPessoaActivity;
import br.com.achapet.Modal.PessoaModal;
import br.com.achapet.R;

public class SplashScreenActivity extends AppCompatActivity {

    private PessoaModal pessoaModal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //pessoaModal = new PessoaModal();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        pessoaModal = new PessoaModal();
        if(pessoaModal.temUsuarioConectado()){
            //finish();
            findViewById(R.id.splash_conectar).setVisibility(View.INVISIBLE);
        }
    }

    public void cadastrarPessoa(View v){
        Intent intent = new Intent(this, LoginPessoaActivity.class);
        startActivity(intent);
    }
}
