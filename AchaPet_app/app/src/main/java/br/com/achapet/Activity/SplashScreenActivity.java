package br.com.achapet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.achapet.Activity.Pet.ListaPetActivity;
import br.com.achapet.Activity.Pessoa.LoginPessoaActivity;
import br.com.achapet.Modal.PessoaModal;
import br.com.achapet.R;

public class SplashScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private PessoaModal pessoaModal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        findViewById(R.id.splash_conectar).setOnClickListener(this);
        findViewById(R.id.splash_pet_perdido).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pessoaModal = new PessoaModal();
        if(pessoaModal.temUsuarioConectado()){
            findViewById(R.id.splash_conectar).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.splash_conectar:
                intent = new Intent(this, LoginPessoaActivity.class);
                startActivity(intent);
                break;
            case R.id.splash_pet_perdido:
                intent = new Intent(this, ListaPetActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
